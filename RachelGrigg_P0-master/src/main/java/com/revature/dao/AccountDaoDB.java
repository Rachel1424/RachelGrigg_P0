package com.revature.dao;

import java.util.*;
import java.sql.*;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {

	private static Connection conec;
	private static Statement stat;
	private static PreparedStatement prestat;
	private static ResultSet rels;

	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
		System.out.println(a);
		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String query = "insert into accounts (acc_ID, own_Id, balance,acc_type,approved) values (?,?,?,?,?)";

		// try catch? check user dao

		try {
			stat = conec.createStatement();

			prestat = conec.prepareStatement(query);
			prestat.setInt(1, a.getId());
			prestat.setInt(2, a.getOwnerId());
			prestat.setDouble(3, a.getBalance());
			AccountType enumVal = a.getType();
			prestat.setString(4, (enumVal.name()));
			prestat.setBoolean(5, a.isApproved());

			prestat.executeUpdate();
			System.out.println("New Account added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	public Account getAccount(Integer actId) {
		// TODO Auto-generated method stub
		Account acc = new Account();
		String testin = "select * from accounts";

		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");

			stat = conec.createStatement();
			rels = stat.executeQuery(testin);

			while (rels.next()) {
				Account nxS = new Account();
				nxS.setId(rels.getInt("acc_Id"));
				nxS.setOwnerId(rels.getInt("own_Id"));
				nxS.setBalance(rels.getDouble("balance"));
				nxS.setApproved(rels.getBoolean("approved"));
				String gotAt = rels.getString("acc_type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				if (nxS.getId() == actId) {

					acc = nxS;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acc;

	}

	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		List<Account> acnts = new ArrayList<>();
		String testin = "select * from accounts";

		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");

			stat = conec.createStatement();
			rels = stat.executeQuery(testin);

			while (rels.next()) {
				Account nxS = new Account();
				nxS.setId(rels.getInt("acc_Id"));
				nxS.setOwnerId(rels.getInt("own_Id"));
				nxS.setBalance(rels.getDouble("balance"));
				nxS.setApproved(rels.getBoolean("approved"));
				String gotAt = rels.getString("acc_type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				acnts.add(nxS);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acnts;

	}

	public List<Account> getAccountsByUser(User u) {

		// TODO Auto-generated method stub
		List<Account> accs = new ArrayList<>();
		int idGot = u.getId();
		String testin = "select * from accounts";

		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");

			stat = conec.createStatement();
			rels = stat.executeQuery(testin);

			while (rels.next()) {
				Account nxS = new Account();
				nxS.setId(rels.getInt("acc_Id"));
				nxS.setOwnerId(rels.getInt("own_Id"));
				nxS.setBalance(rels.getDouble("balance"));
				nxS.setApproved(rels.getBoolean("approved"));
				String gotAt = rels.getString("acc_type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				if (nxS.getOwnerId() == u.getId()) {

					accs.add(nxS);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accs;

	}

	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		//System.out.println("made it to update account");
		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
		
			
			String query = "Update accounts set own_id=?, balance=?, approved=?, acc_type=? where acc_id=" + a.getId();
		
			stat = conec.createStatement();
			
			
	            prestat = conec.prepareStatement(query);
	            prestat.setInt(1, a.getOwnerId());
	            prestat.setDouble(2, a.getBalance());
	            prestat.setBoolean(3, a.isApproved());
	            prestat.setString(4, a.getType().name());
	            int result = prestat.executeUpdate();
	            
	            if (result == 0);
	                return null;
	        
	        
	     
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); }
		
		

		
		return a;
	}

	public boolean removeAccount(Account a) {
		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
		
			
		      String query = "DELETE FROM accounts WHERE acc_id=" + a.getId();
		        boolean status = false;

		        try {

		            stat = conec.createStatement();
		            if (stat.executeUpdate(query) != 0) {
		                status = true;
		            }

		        } catch (SQLException e) {

		            e.printStackTrace();

		        }
		        return status;
		    
	        
	        
	     
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); }
		
		

		
		return false;
		}
			
			
			
}


