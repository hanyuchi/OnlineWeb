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
import databeans.Favorite;
import databeans.User;

/*
 * Removes a photo.  Given an "id" parameter.
 * Checks to see that id is valid number for a photo and that
 * the logged user is the owner.
 * 
 * Sets up the "userList" and "photoList" request attributes
 * and if successful, forwards back to to "manage.jsp".
 */
public class RemoveAction extends Action {
	private FavoriteDAO favoriteDAO;

    public RemoveAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
	}

    public String getName() { return "remove.do"; }

    public String perform(HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("user");
		favoriteDAO.deleteFavorite(request.getParameter("id"));
		
    	List<Favorite> favoriteList = favoriteDAO.getUserFavoriate(user.getUserName());
    	request.setAttribute("favoriteList", favoriteList);

        return "manage.do";
    }
}
