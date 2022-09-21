package com.trafimchuk.veranika.task5;

import com.trafimchuk.veranika.task5.dao.UserDao;
import com.trafimchuk.veranika.task5.dao.UserDaoImpl;
import com.trafimchuk.veranika.task5.model.Currency;
import com.trafimchuk.veranika.task5.model.UserAccount;
import com.trafimchuk.veranika.task5.service.ExchangeService;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.exchange();
    }

    UserDao userDao;
    ExchangeService exchangeService;

    public App() {
        this.userDao = new UserDaoImpl();
        this.exchangeService = new ExchangeService();
    }

    public synchronized void exchange() throws InterruptedException {
        Map<Currency, BigDecimal> currencyMap = new EnumMap<>(Currency.class);
        currencyMap.put(Currency.RUB, BigDecimal.valueOf(100.00));
        currencyMap.put(Currency.USD, BigDecimal.valueOf(0.00));
        currencyMap.put(Currency.EUR, BigDecimal.valueOf(0.00));
        userDao.saveUser(new UserAccount("User1", currencyMap));

        currencyMap.put(Currency.RUB, BigDecimal.valueOf(100.00));
        currencyMap.put(Currency.USD, BigDecimal.valueOf(0.00));
        currencyMap.put(Currency.EUR, BigDecimal.valueOf(0.00));
        userDao.saveUser(new UserAccount("User2", currencyMap));

        Runnable runnable1 = () -> exchangeService.exchangeCurrency(
                "User1", BigDecimal.valueOf(100), Currency.RUB, Currency.EUR);

        Runnable runnable2 = () -> exchangeService.exchangeCurrency(
                "User2", BigDecimal.valueOf(100), Currency.RUB, Currency.USD);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(runnable1);
        executorService.submit(runnable2);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserAccount user1 = userDao.getUser("User1");
        System.out.println(user1.getCurrencyMap());

        UserAccount user2 = userDao.getUser("User2");
        System.out.println(user2.getCurrencyMap());
    }
}
