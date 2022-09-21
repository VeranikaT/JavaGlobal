package com.trafimchuk.veranika.task5.service;

import com.trafimchuk.veranika.task5.dao.UserDaoImpl;
import com.trafimchuk.veranika.task5.model.Currency;
import com.trafimchuk.veranika.task5.model.UserAccount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ExchangeService {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final UserDaoImpl userDao = new UserDaoImpl();

    public void saveUser(UserAccount userAccount) {
        userDao.saveUser(userAccount);
    }

    public Optional<UserAccount> getUserAccount(String login) {
        lock.readLock().lock();
        Optional<UserAccount> user = Optional.ofNullable(userDao.getUser(login));
        lock.readLock().unlock();

        return user;
    }

    public void exchangeCurrency(String login, BigDecimal amount, Currency from, Currency to) {
        lock.writeLock().lock();
        Optional<UserAccount> userAccount = getUserAccount(login);
        exchangeOperation(userAccount.get(), from, to, amount);

        lock.writeLock().unlock();
    }

    private void exchangeOperation(UserAccount userAccount, Currency from, Currency to, BigDecimal amount) {
        BigDecimal result = amount
                .multiply(BigDecimal.valueOf(from.getRate()))
                .divide(BigDecimal.valueOf(to.getRate()), 2, RoundingMode.HALF_UP);
        userAccount.getCurrencyMap().put(to, result);

        BigDecimal newValueAfterExchange = new BigDecimal(
                String.valueOf(userAccount.getCurrencyMap().get(from).subtract(amount)));
        userAccount.getCurrencyMap().put(from, newValueAfterExchange);

        saveUser(userAccount);

    }

    private boolean checkIfEnoughMoneyForExchange(Map<Currency, BigDecimal> currencyMap, BigDecimal amount, Currency from) {
        return currencyMap.get(from).compareTo(amount) < 0;
    }
}