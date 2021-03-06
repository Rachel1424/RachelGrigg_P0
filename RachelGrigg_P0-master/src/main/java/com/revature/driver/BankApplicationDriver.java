package com.revature.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.*;
import com.revature.beans.User.UserType;
import com.revature.dao.UserDaoDB;
import com.revature.exceptions.*;
import com.revature.services.*;
//import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.TransactionDaoDB;
import com.revature.utils.ConnectionUtil;

/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {

	static User user = new User();
	static UserDaoDB doa = new UserDaoDB();
	static AccountDaoDB aoa = new AccountDaoDB();
	static UserService use = new UserService(doa, aoa);
	static AccountService acser = new AccountService(aoa);
	static TransactionDaoDB tdb = new TransactionDaoDB();
	private static Connection conec;
	private static Statement stat;
	private static PreparedStatement prestat;
	private static ResultSet rels;
	static Scanner scan = new Scanner(System.in);
	static Logger log = Logger.getLogger(BankApplicationDriver.class);

	public static void main(String[] args) {
		// your code here...

		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n****************************************************\n");
		System.out.println("\t\tWelcome to RevRae Bank");
		System.out.println("  To log in to an existing account please press 1 \n");
		System.out.println("\tTo creat a new account please press 2 \n");
		// System.out.println("\tTo show a list of all existing users please press 3
		// \n");
		System.out.println(
				"****************************************************\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		int choice = scan.nextInt();
		int cve = 0;

		switch (choice) {

		case 1: {
			// login
			// User tester = new User();

			System.out.println(" To log in Please ");
			System.out.println("Enter Username");
			String usvr = scan.next();
			System.out.println(" Enter Password ");
			String pss = scan.next();
			if (doa.getUser(usvr, pss) == null) {
				System.out.println("Invalid credentials added");
			}

			else
				System.out.println(use.login(usvr, pss));
			User logged = use.login(usvr, pss);
			int lgId = logged.getId();

			if (logged.getUserType() == UserType.CUSTOMER) {

				cve = 1;
			} else {
				cve = 2;
			}

			switch (cve) {
			case 1:

				System.out.println("");
				System.out.println("Welcome returning customer");
				System.out.println("Would you like to:");
				System.out.println("1.Apply for a new account");
				System.out.println("2.View balance");
				System.out.println("3.Make a deposit");
				System.out.println("4.Make a withdrawl");
				System.out.println("5.Make a transfer");
				int ucho = scan.nextInt();

				switch (ucho) {
				case 1:

					System.out.println("Would you like to create:");
					System.out.println("1. A checkings account");
					System.out.println("2. A savings account");
					int actcho = scan.nextInt();
					boolean typeb = false;

					if (actcho == 1) {

						typeb = true;
					}

					acser.createNewAccount(logged, typeb);

					break;

				case 2:
					List<Account> acl = aoa.getAccountsByUser(logged);
					System.out.println("Please enter the id of the account you wish to view");
					for (Account a : acl) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());

					}
					int aic = scan.nextInt();
					Account aB = aoa.getAccount(aic);
					System.out.println("Your balance is: $" + aB.getBalance());

					break;
				case 3:
					System.out.println("Please choose an account to deposit to");
					List<Account> acd = aoa.getAccountsByUser(logged);
					for (Account a : acd) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());}
						int aid = scan.nextInt();
						Account aD = aoa.getAccount(aid);
						System.out.println("Please enter the amount to deposit ");
						double amntd = scan.nextDouble();
						
						try {
							conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
							String query = "insert into transactions (type, inId,outId,amount,prim) values (?,?,?,?,?)";
								
								
								stat = conec.createStatement();
								
					            prestat = conec.prepareStatement(query);
					            
					            prestat.setString(1,"DEPOSIT");
					            
					            prestat.setInt(2, aD.getId());
					        
					            prestat.setInt(3, aD.getId());
					       
					            prestat.setDouble(4, amntd);
					            prestat.setInt(5,0);
								prestat.executeUpdate();
								acser.deposit(aD,amntd);
								break;
								}
							
						 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
					
						}
						

					
				case 4:
					System.out.println("Please choose an account to withdraw from");
					List<Account> acw = aoa.getAccountsByUser(logged);
					for (Account a : acw) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());}
						int aiw = scan.nextInt();
						Account aW = aoa.getAccount(aiw);
						System.out.println("Please enter the amount to withdraw ");
						double amntw = scan.nextDouble();
						try {
							conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
							String query = "insert into transactions (type, inId,outId,amount,prim) values (?,?,?,?,?)";
								
								
								stat = conec.createStatement();
								
					            prestat = conec.prepareStatement(query);
					            
					            prestat.setString(1,"WITHDRAWAL");
					            
					            prestat.setInt(2, aW.getId());
					        
					            prestat.setInt(3, aW.getId());
					       
					            prestat.setDouble(4, amntw);
					            prestat.setInt(5,0);
								prestat.executeUpdate();
								
								acser.withdraw(aW, amntw);
								break;}
							
						 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();}
							
					
					
				case 5:
					System.out.println("Please choose an account to withdraw from");
					List<Account> tacw = aoa.getAccountsByUser(logged);
					for (Account a : tacw) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());
						}
					
						int taiw = scan.nextInt();
						Account taW = aoa.getAccount(taiw);
						
						System.out.println("Please choose an account to deposit to");
						List<Account> tacd = aoa.getAccountsByUser(logged);
						for (Account a : tacd) {
							
							System.out.println("Enter: " + a.getId() + " for Account " + a.getType());
							}
						
							int taid = scan.nextInt();
							Account taD = aoa.getAccount(taid);
							System.out.println("Please enter the amount to  ");
							double amntt = scan.nextDouble();
					
					try {
						conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
						
						String query = "insert into transactions (type, inId,outId,amount,prim) values (?,?,?,?,?)";
							
							
							stat = conec.createStatement();
							
				            prestat = conec.prepareStatement(query);
				            
				            prestat.setString(1,"TRANSFER");
				            
				            prestat.setInt(2, taW.getId());
				        
				            prestat.setInt(3, taD.getId());
				       
				            prestat.setDouble(4, amntt);
				            prestat.setInt(5,0);
							prestat.executeUpdate();
							
							acser.transfer(taW, taD, amntt);
							break;
							}
						
					 catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
					
					
				default:
					break;
				}

				break;

			case 2:
				System.out.println("Welcome back valued team member");
				System.out.println("");
				System.out.println("Would you like to:");
				System.out.println("1. View unapproved accounts?");
				System.out.println("2. View transaction history");
				int emc = scan.nextInt();
				switch (emc) {
				case 1:
					System.out.println("Current unapproved accounts:");
					List<Account> allA = new ArrayList();
					List<Account> allUna = new ArrayList();
					allA = aoa.getAccounts();
					for (Account a : allA) {
						if (a.isApproved() == false) {
							allUna.add(a);
							System.out.println(a);
						}

					}
					System.out.println("Please enter account Id of account you would like to approve/reject:");

					int ap = scan.nextInt();
					Account apa = aoa.getAccount(ap);
					System.out.println("For Account:" + apa + "Would you like to");
					System.out.println("1. Approve");
					System.out.println("2. Reject");
					int dec = scan.nextInt();
					try {
						conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root",
								"Okaythen12wow");
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (dec == 1) {
						
						apa.setApproved(true);
						aoa.updateAccount(apa);
						System.out.println("Account sucessfully approved");
						allUna.remove(apa);
						


					} else if (dec == 2) {
						boolean deb = false;
						aoa.removeAccount(apa);
						System.out.println(allUna);
						System.out.println("This account has been rejected");
						// acser.approveOrRejectAccount(apa, deb);
					}

					// System.out.println("Account application approved");

					// delete account
					// "account application denied"

					System.out.println("");

					break;
				case 2:
					tdb.getAllTransactions();
					break;

				}
				// approve/reject
				// view all transactions
				break;
			}
		}

			break;

		case 2:
			// adds new user
			User u = new User();

			System.out.println("Please enter desired username");
			String username = scan.next();
			System.out.println("Please enter desired password");
			String password = scan.next();

			System.out.println("Please enter your first name");
			String first_name = scan.next();

			System.out.println("Please enter your last name");
			String last_name = scan.next();

			System.out.println("For a customer account pleas press 1");
			System.out.println("");
			System.out.println("For an Employee account pleas press 2");
			int type = scan.nextInt();

			u.setUsername(username);
			u.setPassword(password);
			u.setFirstName(first_name);
			u.setLastName(last_name);
			u.setId(0);

			u.setUserType(UserType.CUSTOMER);

			if (type == 2) {
				u.setUserType(UserType.EMPLOYEE);

			}
			// System.out.println(u);
			use.register(u);
			break;

		default:

			// System.out.println(" Why are you here?");
			break;
		}
	}
}


