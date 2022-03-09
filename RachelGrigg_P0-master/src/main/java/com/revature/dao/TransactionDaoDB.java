package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.beans.User;
import com.revature.beans.Account.AccountType;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class TransactionDaoDB implements TransactionDao {
	private static Connection conec;
	private static Statement stat;
	private static PreparedStatement prestat;
	private static ResultSet rels;
	static User user = new User();
	static UserDaoDB doa = new UserDaoDB();
	static AccountDaoDB aoa = new AccountDaoDB();
	static UserService use = new UserService(doa, aoa);
	static AccountService acser = new AccountService(aoa);
	static TransactionDaoDB tdb = new TransactionDaoDB();
	
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		List<Transaction> l = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		String testin = "select * from transactions";

		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");

			stat = conec.createStatement();
			rels = stat.executeQuery(testin);
			Account inU = new Account();
			Account outU = new Account();

			while (rels.next()) {
				inU = aoa.getAccount(rels.getInt("inId"));
				outU= aoa.getAccount(rels.getInt("outId"));
				t.setAmount(rels.getDouble("amount"));
				t.setSender(outU);
				t.setRecipient(inU);
				String gotAt = rels.getString("type");
				TransactionType enumVal = TransactionType.valueOf(gotAt);
				t.setType(enumVal);
				l.add(t);
			}
				//for(Transaction y: l)
				System.out.println(l);
				
				

				
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}return null;
	
	}}

