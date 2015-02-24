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
import java.util.List;

import databeans.Favorite;

public class FavoriteDAO {
	private ArrayDeque<Connection> connectionPool;
	private String tableName;
	
	public FavoriteDAO(){
		tableName = "yuchih_favorite";
		connectionPool = new ArrayDeque<Connection>();
		if (!tableExist()) {
			createTable();
			/*hard code favorite for each user*/
			Favorite favorite = new Favorite(); 
			favorite.setUserName("liuying");
			favorite.setURL("http://www.cmu.edu");
			favorite.setComment("I like it so much!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("hanyuchi");
			favorite.setURL("http://www.google.com");
			favorite.setComment("Hello Google!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("sunjin");
			favorite.setURL("http://www.buaa.edu.cn");
			favorite.setComment("I hate");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("hanyuchi");
			favorite.setURL("http://www.cmu.edu");
			favorite.setComment("machine learning");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("liuying");
			favorite.setURL("http://www.piazza.com");
			favorite.setComment("08600!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("sunjin");
			favorite.setURL("http://www.google.com");
			favorite.setComment("Hello Google!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("hanyuchi");
			favorite.setURL("http://www.facebook.com");
			favorite.setComment("Facebook!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("liuying");
			favorite.setURL("http://www.baidu.com");
			favorite.setComment("my baidu");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("sunjin");
			favorite.setURL("http://www.twitter.com");
			favorite.setComment("twitter!");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("sunjin");
			favorite.setURL("http://www.sohu.com");
			favorite.setComment("sohu");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("hanyuchi");
			favorite.setURL("http://www.cs.cmu.edu");
			favorite.setComment("cs");
			addFavorite(favorite);
			favorite = new Favorite();
			favorite.setUserName("liuying");
			favorite.setURL("http://www.amazon.com");
			favorite.setComment("shopping!");
			addFavorite(favorite);
		}
	}
	
	public String getUserNameByID(int id){
		String ret = null;
    	try {
    		Connection connection = getConnection();

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName + " WHERE favoriteID=" + id);
            set.next();
            ret = set.getString("userName");
            statement.close();
    	} catch (SQLException e) {
    		System.out.println("error!");
		}
    	return ret;
	}
	
	public List<Favorite> getUserFavoriate(String s){
		List<Favorite> list = new ArrayList<Favorite>();
		
    	try {
    		Connection connection = getConnection();

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM " + tableName + " WHERE userName =" + "\""+s+"\"");
            
            while (set.next()) {
            	Favorite bean = new Favorite();
            	bean.setURL(set.getString("url"));
            	bean.setComment(set.getString("comment"));
            	bean.setClickCount(set.getInt("clickCount"));
            	bean.setFavoriteId(set.getInt("favoriteID"));
            	list.add(bean);
            }
            statement.close();
    	} catch (SQLException e) {
    		System.out.println("error!");
		}
		return list;
	}
	
	public void addFavorite(Favorite bean){
		try {
			Connection connection = getConnection();
			
            PreparedStatement statement = connection.prepareStatement(
            		"INSERT INTO " + tableName + " (userName,url,comment,clickCount) VALUES (?,?,?,?)");

            statement.setString(1, bean.getUserName());
            statement.setString(2, bean.getURL());
            statement.setString(3, bean.getComment());
            statement.setInt(4, 0);
            statement.executeUpdate();
            statement.close();
		} catch (SQLException e) {
			System.out.println("error!");
		}
	}
	
	public void deleteFavorite(String s){
		int id = Integer.parseInt(s);
		try {
			Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
            		"DELETE FROM " + tableName + " WHERE favoriteID=" + id);
            statement.executeUpdate();
            statement.close();
		} catch (SQLException e) {
			System.out.println("SQL delete error!");
		}
	}
	
	public void addCount(int favoriteID){
    	try {
    		Connection connection = getConnection();
    		Statement statement = connection.createStatement();
    		statement.executeUpdate("update " + tableName + " set clickCount=clickCount+1 WHERE favoriteID=" + favoriteID);
    		statement.close();
    	} catch (SQLException e) {
    		System.out.println("error!");
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
	    /*if connection error*/
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
            		" (favoriteID INT NOT NULL AUTO_INCREMENT," +
            		" userName VARCHAR(255)," +
            		" url VARCHAR(255)," +
            		" comment VARCHAR(255), " +
            		" clickCount INT," +
            		" PRIMARY KEY(favoriteID))");
            state.close();
        }catch (SQLException e) {
			System.out.println("connection error in createTable()");;
		}
    }
}
