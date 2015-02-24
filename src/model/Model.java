/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package model;

public class Model {
	private UserDAO  userDAO;
	private FavoriteDAO favoriteDAO;

	public Model(){
		userDAO  = new UserDAO();
		favoriteDAO = new FavoriteDAO();
	}
	
	public UserDAO  getUserDAO()  { return userDAO;  }
	public FavoriteDAO getFavoriteDAO() { return favoriteDAO; }
}
