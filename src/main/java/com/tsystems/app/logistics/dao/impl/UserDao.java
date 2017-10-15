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

}
