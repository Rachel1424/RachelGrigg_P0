package com.revature.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.exceptions.UsernameAlreadyExistsException;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */


public class UserDaoDB implements UserDao {
	public static Connection conec;
	public static Statement stat;
	public static PreparedStatement prestat;
	public static ResultSet rels;
	
	
	public User addUser(User user) {
		// TODO Auto-generated method stub
		
		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "insert into users (id, first_name, last_name,user_name,password,usertype) values (?,?,?,?,?,?)";
		String query2 = "select user_name from users";
		try {
			
			stat = conec.createStatement();
			rels = stat.executeQuery(query2);
				
				

				prestat = conec.prepareStatement(query);
				prestat.setInt(1, user.getId());
				prestat.setString(2, user.getFirstName());
				prestat.setString(3, user.getLastName());
				prestat.setString(4, user.getUsername());
				prestat.setString(5, user.getPassword());
				prestat.setString(6, user.getUserType().name());
				
						
				
				prestat.executeUpdate();
				System.out.println("New user added");}
				
			
				 
				
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		return user;
		}
	

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		User u = new User();
		
		try {conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
		
		stat = conec.createStatement();
		
	
			rels = stat.executeQuery("select * from users WHERE id ="+userId);
			
			if ( rels.next()) {
			
			u.setId(rels.getInt("id"));
			u.setFirstName(rels.getString("first_name"));
			u.setLastName(rels.getString("last_name"));
			u.setUsername(rels.getString("user_name"));
			u.setPassword(rels.getString("password"));
			
			String gotT =rels.getString("usertype");
			UserType enumVal = UserType.valueOf(gotT);
			u.setUserType(enumVal);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		
		return u;
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		User u = new User();
		try {
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
			stat = conec.createStatement();
			rels = stat.executeQuery("SELECT * FROM Users WHERE user_name='" + username + "' AND password='" + pass + "'");
			
			if ( rels.next()) {
				
					 u.setId(rels.getInt("id"));
						u.setFirstName(rels.getString("first_name"));
						u.setLastName(rels.getString("last_name"));
						u.setUsername(rels.getString("user_name"));
						u.setPassword(rels.getString("password"));
						String gotT =rels.getString("usertype");
						UserType enumVal = UserType.valueOf(gotT);
						u.setUserType(enumVal);}
						else u = null;	 
						
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
				
			
	
	return u;

	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		
		
		List<User> users = new ArrayList<>();
		 String testin = "select * from users";
		try 
			{
			conec = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Okaythen12wow");
			
			stat = conec.createStatement();
			rels = stat.executeQuery(testin);
			
			
			while (rels.next())
				 {
					User nxU = new User();
					nxU.setId(rels.getInt("id"));
					nxU.setFirstName(rels.getString("first_name"));
					nxU.setLastName(rels.getString("last_name"));
					nxU.setUsername(rels.getString("user_name"));
					nxU.setPassword(rels.getString("password"));
					String gotT =rels.getString("usertype");
					UserType enumVal = UserType.valueOf(gotT);
					nxU.setUserType(enumVal);
					
					users.add(nxU);}
				}
					 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
			return users;
		
		
	
	}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		// in driver take in all feilds but id as possible chages and set them to user u
		//take user u get it's id
		//replace existing entry with user u?
		return null;
	}

	public boolean removeUser(User u) {
		List<User> del = getAllUsers();
		int fnId = u.getId();
		User ted = getUser(fnId);
		del.remove(fnId);
		if(ted == u) {
			
			System.out.println("User Sucessfully deleted");
			return true;
		}
		System.out.println("Unable to remove user. Please check that all information is correct.");
		return false;
	}

}
