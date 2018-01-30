package com.capgemini.service.impl;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repo.AccountRepo;
import com.capgemini.service.AccountService;

public class AccountServiceImpl  implements AccountService{
	AccountRepo accountRepo;
	public AccountServiceImpl(AccountRepo accountRepo) {
		super();
		this.accountRepo = accountRepo;
	}
	@Override
	public Account createAccount(int accountNumber, int amount)
			throws InsufficientInitialAmountException {
		if(amount < 500) {
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		if(accountRepo.save(account))
			return account;
		return null;
	}
	@Override
	public int depositAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		if(accountRepo.searchAccount(accountNumber) == null)
			throw new InvalidAccountNumberException();
		
		return amount;
	}
	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficientBalanceException {
		// TODO Auto-generated method stub
		if(accountRepo.searchAccount(accountNumber) == null)
			throw new InvalidAccountNumberException();
		
		if(amount <= 0)
			throw new  InsufficientBalanceException();
		
		return amount;
	}
}
