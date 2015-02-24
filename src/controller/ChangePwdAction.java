/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.Model;
import model.UserDAO;
import databeans.User;
import formbeans.ChangePwdForm;

public class ChangePwdAction extends Action {
	private UserDAO userDAO;

	public ChangePwdAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "change-pwd.do"; }
    
    public String perform(HttpServletRequest request) {
    	// Set up error list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        // Set up user list for nav bar
		request.setAttribute("userList",userDAO.getUsers());
        
        // Load the form parameters into a form bean
		ChangePwdForm form = new ChangePwdForm(request);
		User user = (User) request.getSession().getAttribute("user");
		String name = user.getFirstName()+" "+user.getLastName();
		request.setAttribute("title", name);
        
        // If no params were passed, return with no errors so that the form will be
        // presented (we assume for the first time).
        if (!form.isPresent()) {
            return "change-pwd.jsp";
        }

        // Check for any validation errors
        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            return "change-pwd.jsp";
        }

		/*if current password is wrong*/
		if(!user.checkPassword(form.getCurrentPassword())){
			errors.add("Incorrect Current Password");
			return "change-pwd.jsp";
		}
		
		// Change the password
    	userDAO.setPassword(user.getUserName(),form.getNewPassword());

		request.setAttribute("message","Password changed for "+user.getFirstName()+" "+user.getLastName());
        return "success.jsp";
    }
}
