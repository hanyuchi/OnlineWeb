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
import formbeans.AddFavoriteForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class AddFavoriteAction extends Action{
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public AddFavoriteAction(Model model) {
		userDAO  = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}
	
	public String getName() {
		return "addFavorite.do";
	}

	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        AddFavoriteForm form = new AddFavoriteForm(request);
        request.setAttribute("form",form);
        errors.addAll(form.getError());
        
        // Set up user list for nav bar
		request.setAttribute("userList",userDAO.getUsers());

		User user = (User) request.getSession(false).getAttribute("user");
    	List<Favorite> favoriteList = favoriteDAO.getUserFavoriate(user.getUserName());
    	request.setAttribute("favoriteList", favoriteList);
    	
    	String name = user.getFirstName() + " " + user.getLastName();
    	request.setAttribute("title", name);
        if (errors.size() != 0) {
            return "manage.jsp";
        }
        
		Favorite bean = new Favorite();
		bean.setURL(form.getURL());
		bean.setComment(form.getComment());
		bean.setUserName(user.getUserName());
		favoriteDAO.addFavorite(bean);
		
		return "manage.do";
	}
}
