/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FavoriteDAO;
import model.Model;
import model.UserDAO;
import databeans.Favorite;
import databeans.User;

/*
 * Sets up the request attributes for manage.jsp.
 * This is the way to enter "Manage Your Photos"
 * from someplace else in the site.
 * 
 * Sets the "userList" request attribute in order to display
 * the list of users on the navbar.
 * 
 * Sets the "photoList" request attribute in order to display
 * the list of user's photos for management.
 * 
 * Forwards to manage.jsp.
 */
public class ManageAction extends Action {

	private UserDAO  userDAO;
	private FavoriteDAO favoriteDAO;

	public ManageAction(Model model) {
    	userDAO  = model.getUserDAO();
    	favoriteDAO = model.getFavoriteDAO();
	}

	public String getName() { return "manage.do"; }

	public String perform(HttpServletRequest request) {
        // Set up user list for nav bar
		request.setAttribute("userList",userDAO.getUsers());

		User user = (User) request.getSession(false).getAttribute("user");
		String name = user.getFirstName()+" "+user.getLastName();
		request.setAttribute("title", name);
    	List<Favorite> favoriteList = favoriteDAO.getUserFavoriate(user.getUserName());
    	request.setAttribute("favoriteList", favoriteList);
        
        return "manage.jsp";
    }
}
