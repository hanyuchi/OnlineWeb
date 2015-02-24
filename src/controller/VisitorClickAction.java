/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import databeans.Favorite;
import databeans.User;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class VisitorClickAction extends Action{
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;
	
	public VisitorClickAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO  = model.getUserDAO();
	}
	
	public String getName() {
		return "addClickByVisitor.do";
	}

	public String perform(HttpServletRequest request) {
		int favoriteID = Integer.parseInt(request.getParameter("favoriteID"));
		System.out.println(favoriteID);
		favoriteDAO.addCount(favoriteID);
		String userName = favoriteDAO.getUserNameByID(favoriteID);
		
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        // Set up user list for nav bar
		request.setAttribute("userList",userDAO.getUsers());

        // Set up photo list
    	User user = userDAO.read(userName);
    	if (user == null) {
			errors.add("Invalid User: "+userName);
			return "error.jsp";
		}

    	List<Favorite> favoriteList = favoriteDAO.getUserFavoriate(userName);
    	request.setAttribute("favoriteList", favoriteList);
    	String name = user.getFirstName() + " " + user.getLastName();
    	request.setAttribute("title", name);
        return "list.jsp";
	}
}
