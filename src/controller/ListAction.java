/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Favorite;
import databeans.User;
import formbeans.UserForm;

/*
 * Looks up the photos for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "photoList" request attribute in order to display
 *       the list of given user's photos for selection.
 *   (3) Forwards to list.jsp.
 */
public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);
	private UserDAO  userDAO;
	private FavoriteDAO favoriteDAO;
    public ListAction(Model model) {
    	userDAO  = model.getUserDAO();
    	favoriteDAO = model.getFavoriteDAO();
	}

    public String getName() { return "list.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the request attributes (the errors list and the form bean so
        // we can just return to the jsp with the form if the request isn't correct)
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());

			UserForm form = formBeanFactory.create(request);
			String userName = form.getUserName();
			if (userName == null || userName.length() == 0) {
				errors.add("User must be specified");
				return "error.jsp";
			}
	
        	User user = userDAO.read(userName);
        	if (user == null) {
    			errors.add("Invalid User: "+userName);
    			return "error.jsp";
    		}
        	
        	List<Favorite> favoriteList = favoriteDAO.getUserFavoriate(userName);
        	request.setAttribute("favoriteList", favoriteList);
        	
        	User currentUser = (User) request.getSession(false).getAttribute("user");
        	
	    	request.setAttribute("title", userDAO.getNameByUserName(userName));
        	/*if current user*/
        	if(currentUser != null && currentUser.getUserName().equals(userName)){
    	        return "manage.jsp";
        	}
	        return "list.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
