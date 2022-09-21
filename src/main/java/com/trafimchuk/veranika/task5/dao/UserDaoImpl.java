package com.trafimchuk.veranika.task5.dao;


import com.trafimchuk.veranika.task5.model.Currency;
import com.trafimchuk.veranika.task5.model.UserAccount;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserDaoImpl implements UserDao {

    @Override
    public void saveUser(UserAccount userAccount) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(userAccount.getLogin());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        userAccount.getCurrencyMap().forEach((key, value) -> printWriter.print(key + " " + value + " "));
        printWriter.close();
    }

    @Override
    public UserAccount getUser(String login) {
        Map<Currency, BigDecimal> currencyMap = new HashMap<>();
        try {
            FileReader file = new FileReader(login);
            Scanner sc = new Scanner(file);
            sc.useDelimiter(" ");

            while (sc.hasNext()) {
                currencyMap.put(Currency.valueOf(sc.next()), new BigDecimal(sc.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new UserAccount(login, currencyMap);
    }
}