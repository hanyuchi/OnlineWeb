/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import javax.servlet.http.HttpServletRequest;

import model.FavoriteDAO;
import model.Model;

public class AddClickAction extends Action{

	private FavoriteDAO favoriteDAO;

	public AddClickAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
	}
	
	public String getName() {
		return "addClick.do";
	}

	public String perform(HttpServletRequest request) {
		int favoriteID = Integer.parseInt(request.getParameter("favoriteID"));
		favoriteDAO.addCount(favoriteID);
		return "manage.do";
	}
}
