package com.trafimchuk.veranika.task5.dao;

import com.trafimchuk.veranika.task5.model.UserAccount;

public interface UserDao {

    void saveUser(UserAccount userAccount);
    UserAccount getUser(String login);
}