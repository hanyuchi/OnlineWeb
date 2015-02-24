/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import databeans.User;

public class UserDAO{
	private ArrayDeque<Connection> connectionPool;
	private String tableName;
	
	public UserDAO(){
		tableName = "yuchih_user";
		connectionPool = new ArrayDeque<Connection>();
		
		if (!tableExist()) {
			createTable();
			/*hard code three new users*/
			User user = new User();
	        user.setUserName("liuying");
	        user.setFirstName("Ying");
	        user.setLastName("Liu");
	        user.setPassword("liuying");
        	create(user);
        	user = new User();
	        user.setUserName("hanyuchi");
	        user.setFirstName("Yuchi");
	        user.setLastName("Han");
	        user.setPassword("hanyuchi");
        	create(user);
        	user = new User();
	        user.setUserName("sunjin");
	        user.setFirstName("Jin");
	        user.setLastName("Sun");
	        user.setPassword("sunjin");
        	create(user);
		}
	}
	
	public String getNameByUserName(String s){
		String ret = null;
    	try {
    		Connection connection = getConnection();

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName + " WHERE userName =" + "\""+s+"\"");
            set.next();
            ret = set.getString("firstName") + " " + set.getString("lastName");
            statement.close();
    	} catch (SQLException e) {
    		System.out.println("get name error!");
		}
    	return ret;
	}
	
	public User read(String s){
		User user = new User();
    	try {
    		Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName + " WHERE userName =" + "\""+s+"\"");
            set.next();
        	user.setUserName(set.getString("userName"));
        	user.setFirstName(set.getString("firstName"));
        	user.setLastName(set.getString("lastName"));
        	user.setSalt(set.getInt("salt"));
        	user.setHashedPassword(set.getString("password"));
            statement.close();
    	} catch (SQLException e) {
    		user = null;
		}
		return user;
	}

	public User[] getUsers(){
		List<User> list = new ArrayList<User>();
		
    	try {
    		Connection connection = getConnection();

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName);
            
            while (set.next()) {
            	User bean = new User();
            	bean.setUserName(set.getString("userName"));
            	bean.setFirstName(set.getString("firstName"));
            	bean.setLastName(set.getString("lastName"));
            	list.add(bean);
            }
            statement.close();
    	} catch (SQLException e) {
    		System.out.println("return user list error!");
		}
    	
    	User[] users = new User[list.size()];
    	for(int i = 0; i < list.size(); i++){
    		users[i] = list.get(i);
    	}
		Arrays.sort(users);  // We want them sorted by last and first names (as per User.compareTo());
		return users;
	}
	
	public void setPassword(String userName, String password){
		User user = new User();
		System.out.println(userName);
		System.out.println(password);
		user.setPassword(password);
		
		try {
    		Connection connection = getConnection();
    		Statement statement = connection.createStatement();
    		System.out.println("!!!!");
    		statement.executeUpdate("update " + tableName + " set password="+"\""+user.getHashedPassword()+"\""+" WHERE userName=" + "\""+userName+"\"");
    		statement.close();
    	} catch (SQLException e) {
    		System.out.println("set password error!");
		}
	}
	
	public void create(User user){
		Connection connection = null;
        try {
        	connection = getConnection();
        	PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName 
        			+ " (userName,firstName,lastName,password,salt) VALUES (?,?,?,?,?)");
        	
        	statement.setString(1,user.getUserName());
        	statement.setString(2,user.getFirstName());
        	statement.setString(3,user.getLastName());
        	statement.setString(4,user.getHashedPassword());
        	statement.setInt(5,user.getSalt());
        	statement.executeUpdate();
        	
        	statement.close();
        } catch (Exception e) {
            System.out.println("create() error!");
        }
	}
	
	/*multi-threading*/
	private synchronized Connection getConnection(){
		/*record this connection*/
		if (connectionPool.size() > 0) {
			return connectionPool.poll();
		}
		
		Connection connection = null;
	    try{   
	    	Class.forName("com.mysql.jdbc.Driver"); 
	    }
	    catch(ClassNotFoundException e){   
	        System.out.println("cannot find the driver!");   
	    }  
	    
	    /*Define the connection URL*/
	    String host = "localhost";
		String dbName = "test";
		int port = 3306;
		String mysqlURL = "jdbc:mysql://"+ host + ":" + port +"/" + dbName;
		
		/*Establish the connection*/
	    try {
			connection = DriverManager.getConnection(mysqlURL);
		} catch (SQLException e) {
			System.out.println("getConnection() errors in FavoriteDAO");
		}
	    connectionPool.offer(connection);
	    return connection;
	}
	
	private boolean tableExist(){
    	Connection connection = getConnection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet set = metaData.getTables(null, null, tableName, null);
	    	
	    	boolean ret = set.next();
	    	set.close();
	    	return ret;
		} catch (SQLException e) {
			System.out.println("connection error in tableExist()");;
		}
    	return false;
	}
	
	private void createTable(){
		Connection connection = null;
        try {
        	connection = getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate("CREATE TABLE " + tableName + 
            		" (userID INT NOT NULL AUTO_INCREMENT, "
            		+ "userName VARCHAR(255), firstName VARCHAR(255), "
            		+ "lastName VARCHAR(255), password VARCHAR(255), "
            		+ "salt INT, PRIMARY KEY(userID))");
            state.close();
        }catch (SQLException e) {
			System.out.println("connection error in createTable()");;
		}
    }
}
