package com.ivaneskins.spring.rest.dao;


import com.ivaneskins.spring.rest.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void addUser(User user) {
        if (user.getId() != 0) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {
        Query query = entityManager.createQuery("DELETE from User WHERE id =:userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }
}
