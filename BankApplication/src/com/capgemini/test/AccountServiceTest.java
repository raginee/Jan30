package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repo.AccountRepo;
import com.capgemini.service.AccountService;
import com.capgemini.service.impl.AccountServiceImpl;

public class AccountServiceTest {

	@Mock
	AccountRepo accountRepo;
	AccountService accountService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepo);
	}

	
	
	/**
	 * 1. When Amount is less than 500 then throw InsufficientInitialAmountException
	 * @throws InsufficientInitialAmountException
	 */
	@Test(expected = InsufficientInitialAmountException.class)
	public void whenAmountIsLessThan500ThenThrowInsufficientInitialAmountException() throws InsufficientInitialAmountException{
		
		accountService.createAccount(101, 300);
	}
	
	
	/**
	 * 2. When amount is valid then account must be created successfully.
	 * @throws InsufficientInitialAmountException
	 */
	@Test
	public void whenAmountIsValidAccountMustBeCreatedSuccessfully() throws InsufficientInitialAmountException {
		
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(10000);
		
		when(accountRepo.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 10000));
	}
	
	
	/**
	 * 3. Deposit - When Account number is invalid then throw InvalidAccountNumberException
	 * @throws InvalidAccountNumberException
	 */
	@Test(expected=InvalidAccountNumberException.class)
	public void deposit_whenAccountNumberIsInvalidThenThrowInvalidAccountNumberException() throws InvalidAccountNumberException {
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(10000);
		
		when(accountRepo.searchAccount(account.getAccountNumber())).thenReturn(null);
		accountService.depositAmount(account.getAccountNumber(),account.getAmount());
		
	}
	
	
	/**
	 * 4. When account number is valid then amount must be deposited successfully.
	 * @throws InvalidAccountNumberException
	 */
	@Test
	public void whenAccountIsValidDepositTheAmountSuccessfully() throws InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(1500);
		int depositAmount = 500;
		
		when(accountRepo.searchAccount(account.getAccountNumber())).thenReturn(account);
		assertEquals(account.getAmount()+depositAmount, accountService.depositAmount(account.getAccountNumber(), account.getAmount()+depositAmount));
	}
	
	
	/**
	 * 5. Withdraw -  When Account number is invalid then throw InvalidAccountNumberException
	 * @throws InvalidAccountNumberException
	 * @throws InsufficientBalanceException
	 */
	@Test(expected=InvalidAccountNumberException.class)
	public void withdraw_whenAccountNumberIsInvalidThenThrowInvalidAccountNumberException() throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(10000);
		
		when(accountRepo.searchAccount(account.getAccountNumber())).thenReturn(null);
		
		accountService.withdrawAmount(account.getAccountNumber(),account.getAmount());
	}
	
	
	/**
	 * 6. When account number is valid but no balance available then throw InsufficientBalanceException
	 * @throws InvalidAccountNumberException
	 * @throws InsufficientBalanceException
	 */
	@Test(expected=InsufficientBalanceException.class)
	public void whenAccountIsValidButNoBalanceAvailableThenThrowInsufficientBalanceException() throws InvalidAccountNumberException, InsufficientBalanceException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(1000);
		int withdrawAmount = 1100;
		when(accountRepo.searchAccount(account.getAccountNumber())).thenReturn(account);
		assertEquals(account.getAmount()-withdrawAmount, accountService.withdrawAmount(account.getAccountNumber(), account.getAmount()-withdrawAmount));
	}
	
	/**
	 * 7. When account number is valid then withdraw the amount successfully.
	 * @throws InvalidAccountNumberException
	 * @throws InsufficientBalanceException
	 */
	@Test
	public void whenAccountNumberIsValidThenWithdrawAmountSuccessfully() throws InvalidAccountNumberException, InsufficientBalanceException{
	
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(20000);
		int withdrawAmount = 10000;
		when(accountRepo.searchAccount(account.getAccountNumber())).thenReturn(account);
		assertEquals(account.getAmount()-withdrawAmount, accountService.withdrawAmount(account.getAccountNumber(), account.getAmount()-withdrawAmount));
	}
}
