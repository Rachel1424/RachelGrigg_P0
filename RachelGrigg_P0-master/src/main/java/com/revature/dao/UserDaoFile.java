package com.revature.dao;

import java.beans.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {

	public static String fileLocation = "";

	public User addUser(User newB) {
		// TODO Auto-generated method stub
		List<User> exis = getAllUsers();
		exis.add(newB);
		try {
			ObjectOutputStream op = new ObjectOutputStream(new FileOutputStream(fileLocation));
		} /*catch (FileNotFoundException e) {
		
			System.out.println("File exception in addUser");
		} */catch (IOException e) {
			
			System.out.println("IO exception in addUser");
		}{
			
		}
		
		
		return newB;
	}
		/*hopefully this all 
		 * 1. attempts to add the new user
		 * 2. attempts to write/overwrite the user list in the file
		 * - don't think this currently accounts for duplicate accounts
		 * might have to do this in Services or Applications*/
		

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		User u = null;
		List<User> runner = getAllUsers();
		for (User temp : runner) {

			if (userId == temp.getId()) {

				u = temp;
				break;
			}

		}
		// addUserToStream(temp); - not sure if its worth it currently to make a new method to handle this or to just write it out
		return u;
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		User u = null;
		List<User> runner = getAllUsers();
		for (User temp : runner) {

			if (username == temp.getUsername() && pass == temp.getPassword()) {

				u = temp;
				break;
			}

		}
		//addUsersToStream(runner);- not sure if its worth it currentlyy to make a new method to handle this or to just write it out
		return u;
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		try (ObjectInputStream ufr = new ObjectInputStream(new FileInputStream(fileLocation))) {
			users = (List<User>) ufr.readObject();
		} catch (FileNotFoundException e) {
			return users;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Exception thrown in getAllUsers()");
		}
		return users;
	}

	public User updateUser(User udated) {
		// TODO Auto-generated method stub
		// possibly need to check for permissions in another spot
		//ask them what they would like to change, store it in a user separate from the user to change
		//User u = null;
		//List<User> upD = getAllUsers();
		//userDao.updateUser();
		/*int uID = udated.getId();
		User u = getUser(uID);
		setId(Integer id) =
		//get then set
		 * idk what im doing with any of this tbh*/
		return null; 
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		// possibly need to check for permissions in another spot
		//User u = null;
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
