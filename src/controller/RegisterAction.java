/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Model;
import model.UserDAO;
import databeans.User;
import formbeans.RegisterForm;

/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new User bean
 *   (2) sets the "user" session attribute to the new User bean
 *   (3) redirects to view the originally requested photo.
 * If there was no photo originally requested to be viewed
 * (as specified by the "redirect" hidden form value),
 * just redirect to manage.do to allow the user to add some
 * photos.
 */
public class RegisterAction extends Action {
	private UserDAO userDAO;
	
	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "register.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        //RegisterForm form = formBeanFactory.create(request);//error
        RegisterForm form = new RegisterForm(request);
        
        request.setAttribute("form",form);
        request.setAttribute("userList",userDAO.getUsers());
        
        // If no params were passed, return with no errors so that the form will be
        // presented (we assume for the first time).
        if (!form.isPresent()) {
            return "register.jsp";
        }
        
        // Any validation errors?
        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            return "register.jsp";
        }
        
        if(userDAO.read(form.getUserName()) != null){
        	errors.add("Username already exists! Please try a new one!");
        	return "register.jsp";
        }
        
        // Create the user bean
        User user = new User();
        user.setUserName(form.getUserName());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setPassword(form.getPassword());
    	userDAO.create(user);/*if usename duplicated, then return*/
    	
		// Attach (this copy of) the user bean to the session
        HttpSession session = request.getSession(false);
        session.setAttribute("user",user);
		return "manage.do";
    }
}
