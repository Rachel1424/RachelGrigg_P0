package com.revature.dao;

import java.util.*;
import java.io.*;

import com.revature.beans.Account;
import com.revature.beans.User;

/**
 * Implementation of AccountDAO which reads/writes to files
 */
public class AccountDaoFile implements AccountDao {
	// use this file location to persist the data to
	public static String fileLocation = "";

	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
		
		// remeber Users has a list of Account named accounts
		return null;
	}

	public Account getAccount(Integer actId) {
		// TODO Auto-generated method stub
		
		
		return null;
	}


	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		
		return null;
		
	}

	public List<Account> getAccountsByUser(User u) {
		// TODO Auto-generated method stub
		
	
		return null;}
	

	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		List<Account> accs = getAccounts();
		try {
			if (accs.removeIf(acc -> acc.getId().equals(a.getId()))) {
				accs.add(a);
				return a;
			}
			
		} finally {
			//addto thing
			
		}
		return null;
	}

	public boolean removeAccount(Account a) {
		// TODO Auto-generated method stub
		List<Account> getA = getAccounts();
		boolean getB = getA.removeIf(accon -> accon.equals(a));
		//addto thing;
		return getB;
	}

}
