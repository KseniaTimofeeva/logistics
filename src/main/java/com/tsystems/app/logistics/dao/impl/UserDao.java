package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.dao.impl.GenericDaoImpl;
import com.tsystems.app.logistics.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ksenia on 08.10.2017.
 */
@Repository
public class UserDao extends GenericDaoImpl<User> {

    public List<User> getUserByLogin(String login) {
        return em.createNamedQuery(User.GET_BY_LOGIN, User.class)
                .setParameter("login", login)
                .getResultList();
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_DRIVERS, User.class)
                .getResultList();
    }

    public List<User> getSuitableDrivers(Long orderId) {
        return em.createNamedQuery(User.GET_SUITABLE_DRIVERS, User.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<User> getProfile(String login) {
        return em.createNamedQuery(User.GET_PROFILE, User.class)
                .setParameter("login", login)
                .getResultList();
    }

    public List<User> newUserValidate(String login, String personalNumber) {
        return em.createNamedQuery(User.NEW_USER_VALIDATE, User.class)
                .setParameter("login", login)
                .setParameter("personalNumber", personalNumber)
                .getResultList();
    }
}
