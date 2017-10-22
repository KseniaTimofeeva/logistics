package com.tsystems.app.logistics.service.impl;

import com.google.maps.model.LatLng;
import com.tsystems.app.logistics.converter.DriverConverter;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.PathPointDao;
import com.tsystems.app.logistics.dao.impl.TimeTrackDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.dto.DriverProfileDto;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.dto.SuitableDriverDto;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Crew;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.entity.TimeTrack;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.SecurityRole;
import com.tsystems.app.logistics.service.api.DriverService;
import com.tsystems.app.logistics.utils.GeoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 08.10.2017.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {
    private static final Logger LOG = LogManager.getLogger(DriverServiceImpl.class);

    private UserDao userDao;
    private CityDao cityDao;
    private PathPointDao pointDao;
    private OrderDao orderDao;
    private TimeTrackDao trackDao;

    @Autowired
    private GeoUtils geoUtils;
    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
    }

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }

    @Autowired
    public void setTrackDao(TimeTrackDao trackDao) {
        this.trackDao = trackDao;
        trackDao.setEntityClass(TimeTrack.class);
    }

    @Autowired
    public void setPointDao(PathPointDao pointDao) {
        this.pointDao = pointDao;
        pointDao.setEntityClass(PathPoint.class);
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }

    @Override
    public void processDriver(DriverDto driverDto) {
        if (driverDto.getId() != null) {
            updateDriver(driverDto);
        } else {
            addNewDriver(driverDto);
        }
    }

    @Override
    public void addNewDriver(DriverDto driverDto) {
        userDao.create(fromDtoToUser(driverDto));
    }

    @Override
    public void updateDriver(DriverDto driverDto) {
        userDao.update(fromDtoToUser(driverDto));
    }

    @Override
    public SuitableDriverDto getSuitableDriversForOrder(Long orderId) {
        LOG.debug("Start find suitable drivers for order №: {}", orderId);

        Order order = orderDao.findOneById(orderId);
        List<User> drivers = userDao.getSuitableDrivers(orderId);
        LOG.debug("Number of suitable drivers before time limit checking: {} pcs", drivers.size());

        List<User> suitableDrivers = drivers;
        List<User> notSuitableDriversFromCurrentCrew = new ArrayList<>();

        if (order.getPathPoints() != null && order.getPathPoints().size() >= 2) {
            Long dis = getDistanceForOrder(order.getPathPoints());
            LOG.debug("Distance through all way points: {} mts", dis);

            Truck truck = order.getCrew().getTruck();
            int crewSize = order.getCrew().getUsers().size();


            //check which of the left drivers is suitable
            LOG.trace("Check which of the left drivers is suitable");
            suitableDrivers = checkDrivers(dis, truck, crewSize + 1, drivers, false);
            LOG.debug("Number of suitable drivers after time limit checking: {} pcs", suitableDrivers.size());

            //check which of the drivers from current crew isn't suitable
            LOG.trace("Check which of the drivers from current crew isn't suitable");
            notSuitableDriversFromCurrentCrew = checkDrivers(dis, truck, crewSize, order.getCrew().getUsers(), true);
            LOG.debug("Number of notSuitable drivers from current crew: {} pcs", notSuitableDriversFromCurrentCrew.size());

        }

        SuitableDriverDto suitableDriverDto = new SuitableDriverDto();
        suitableDriverDto.setDrivers(driverConverter.toDriverDtoList(suitableDrivers));
        suitableDriverDto.setNotSuitableDrivers(driverConverter.toDriverDtoList(notSuitableDriversFromCurrentCrew));

        return suitableDriverDto;
    }

    private List<User> checkDrivers(Long distance, Truck truck, int crewSize, List<User> drivers, boolean checkCurrentCrew) {

        //average speed 80km/h
        double totalWorkingHoursForOneDriver = distance / 1000.0 / 80.0 / crewSize;
        double totalDaysForOneDriver = totalWorkingHoursForOneDriver / truck.getWorkingShift();

        List<User> resultDriverList = new ArrayList<>();
        Timestamp firstDayOfMonth = Timestamp.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay());
        for (User driver : drivers) {
            LOG.trace("Driver id: {}", driver.getId());

            List<TimeTrack> timeTracks = trackDao.getTracksInCurrentMonth(driver.getId(), firstDayOfMonth);
            LOG.trace("Quantity of time tracks in current month: {}", timeTracks.size());

            double alreadyWorkedTimeInMillis = 0;
            for (TimeTrack timeTrack : timeTracks) {
                if (timeTrack.getDuration() == null) {
                    alreadyWorkedTimeInMillis += (Duration.between(timeTrack.getDate().toLocalDateTime(), LocalDateTime.now())).toMillis();
                } else {
                    alreadyWorkedTimeInMillis += timeTrack.getDuration();
                }
            }
            LOG.trace("Already worked time in current month in millis {}", alreadyWorkedTimeInMillis);

            double alreadyWorkedHrs = alreadyWorkedTimeInMillis / 1000.0 / 3600.0;
            LOG.trace("Already worked hours in current month {} hrs", alreadyWorkedHrs);

            boolean isSuitable = checkDriverTimeLimitPerMonth(totalDaysForOneDriver, alreadyWorkedHrs, truck.getWorkingShift());
            LOG.trace("Is suitable {}", isSuitable);

            if (!checkCurrentCrew && isSuitable) {
                resultDriverList.add(driver);
            }
            if (checkCurrentCrew && !isSuitable) {
                resultDriverList.add(driver);
            }
        }
        return resultDriverList;
    }

    private boolean checkDriverTimeLimitPerMonth(double totalDays, double alreadyWorkedHrs, double workingShift) {
        int leftDaysInMonth = LocalDate.now().lengthOfMonth() - LocalDate.now().getDayOfMonth();
        if (leftDaysInMonth >= totalDays) {
            LOG.trace("Left days in month more than total days per order");
            return (totalDays * workingShift + alreadyWorkedHrs) <= 176;
        } else {
            LOG.trace("Left days in month is less than total days per order");

            if ((leftDaysInMonth * workingShift + alreadyWorkedHrs) > 176) {
                return false;
            }
            double totalLeftDays = totalDays - leftDaysInMonth;
            int i = 0;
            while (totalLeftDays > LocalDate.now().plusMonths(i + 1).lengthOfMonth()) {
                if (LocalDate.now().plusMonths(i).lengthOfMonth() * workingShift > 176) {
                    return false;
                }
                totalLeftDays -= LocalDate.now().plusMonths(i + 1).lengthOfMonth();
                i++;
            }
            return totalLeftDays * workingShift <= 176;
        }
    }

    private Long getDistanceForOrder(List<PathPoint> points) {
        LOG.trace("Distance calculation");

        LatLng[] pointsLatLng;
        if (points.size() > 2) {
            pointsLatLng = points.stream()
                    .skip(1)
                    .limit(points.size() - 2)
                    .map(p -> new LatLng(p.getCity().getLatitude(), p.getCity().getLongitude()))
                    .toArray(LatLng[]::new);
        } else {
            pointsLatLng = new LatLng[0];
        }

        LatLng start = new LatLng(points.get(0).getCity().getLatitude(), points.get(0).getCity().getLongitude());
        LatLng finish = new LatLng(points.get(points.size() - 1).getCity().getLatitude(), points.get(points.size() - 1).getCity().getLongitude());

        return geoUtils.getDistanceBetweenWithGoogleApi(start, finish, pointsLatLng);
    }

    private User fromDtoToUser(DriverDto driverDto) {
        User user = new User();
        if (driverDto.getId() != null) {
            user.setId(driverDto.getId());
        }
        user.setFirstName(driverDto.getFirstName());
        user.setLastName(driverDto.getLastName());
        user.setPersonalNumber(driverDto.getPersonalNumber());
        user.setLogin(driverDto.getLogin());
        user.setPassword(driverDto.getPassword());
        user.setRole(SecurityRole.ROLE_DRIVER);
        user.setOnOrder(driverDto.getOnOrder());
        City city = cityDao.findOneById(driverDto.getCurrentCity().getId());
        user.setCurrentCity(city);
        return user;
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        List<User> allUsers = userDao.getAllUsers();
        return driverConverter.toDriverDtoList(allUsers);
    }

    @Override
    public void deleteDriver(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public DriverDto getDriverById(Long id) {
        User user = userDao.findOneById(id);
        return driverConverter.toDriverDto(user);
    }

    @Override
    public DriverProfileDto getDriverProfileByLogin(String login) {
        List<User> drivers = userDao.getProfile(login);
        if (drivers.isEmpty()) {
            drivers = userDao.getUserByLogin(login);
        }
        return driverConverter.toDriverProfileDto(drivers.get(0));
    }
}
