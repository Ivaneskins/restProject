package com.ivaneskins.spring.rest.dao;



import com.ivaneskins.spring.rest.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);


    User getUser(int id);
}
