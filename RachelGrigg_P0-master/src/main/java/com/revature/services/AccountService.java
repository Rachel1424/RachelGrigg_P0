package com.revature.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User;
import com.revature.beans.Transaction;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoDB;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.exceptions.OverdraftException;
import com.revature.exceptions.UsernameAlreadyExistsException;

/**
 * This class should contain the business logic for performing operations on
 * Accounts
 */
public class AccountService {

	private static Connection conec;
	private static Statement stat;
	private static PreparedStatement prestat;
	private static ResultSet rels;

	public AccountDao actDao;
	public static final double STARTING_BALANCE = 25d;
	static AccountDaoDB aoa = new AccountDaoDB();
	static TransactionDaoDB toa = new TransactionDaoDB();

	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}

	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		boolean negative = true;
		if (!a.isApproved()) {
			System.out.println("Cannot withdraw from an unapproved account");
			throw new UnsupportedOperationException();}
			else {
				 negative = Double.doubleToLongBits(amount) < 0;
				
			}
			 
			if (negative == true ) {
				
			
				System.out.println("Cannot withdraw a negative amount");
				throw new UnsupportedOperationException(); 
				}
			else {
			double x = a.getBalance()-amount ;
				 if (x >= 0) {
				double	nwb = a.getBalance()-amount;
				a.setBalance(nwb);
				
				aoa.updateAccount(a);
			
				 
				System.out.println("Sucessful withdrawl");
				 }
				 else {
						System.out.println("Cannot complete withdraw as amount give would result in  an overdraft");
						throw new UnsupportedOperationException(); 
				 }
				}

	}

	/**
	 * Deposit funds to an account
	 * 
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		boolean negative = true;
		if (!a.isApproved()) {
			System.out.println("Cannot deposit to an unapproved account");
			throw new UnsupportedOperationException();
		} else {
			negative = Double.doubleToLongBits(amount) < 0;

		}

		if (negative == true) {

			System.out.println("Cannot deposit a negative amount");
			throw new UnsupportedOperationException();
		} else {

			double nwb = a.getBalance() + amount;
			a.setBalance(nwb);

			aoa.updateAccount(a);
			// log transaction
			System.out.println("Sucessful deposit");
		}

	}

	/**
	 * Transfers funds between accounts
	 * 
	 * @throws UnsupportedOperationException if amount is negative or the
	 *                                       transaction would result in a negative
	 *                                       balance for either account or if either
	 *                                       account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct   the account to deposit to
	 * @param amount  the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		// System.out.println(amount);
		withdraw(fromAct, amount);
		// System.out.println(amount);
		deposit(toAct, amount);
		// System.out.println(amount);
		// log transaction
	}

	/**
	 * Creates a new account for a given User
	 * 
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User a, boolean b) {
		int lgId = a.getId();
		Account na = new Account();
		na.setOwnerId(lgId);
		na.setId(0);
		na.setBalance(STARTING_BALANCE);
		na.setApproved(false);
		if (b == false) {

			na.setType(AccountType.SAVINGS);
		} else
			na.setType(AccountType.CHECKING);
		aoa.addAccount(na);

		return na;
	}

	/**
	 * Approve or reject an account.
	 * 
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {

		// switch(approval) {

		// }
		return false;
	}
}
