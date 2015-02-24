/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package formbeans;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ChangePwdForm{
	private String confirmPassword;
	private String newPassword;
	private String currentPassword;
	private String change;
	
	public ChangePwdForm(HttpServletRequest request){
		newPassword = request.getParameter("newPassword");
		confirmPassword = request.getParameter("confirmPassword");
		currentPassword = request.getParameter("current");
		change = request.getParameter("change");
	}
	
	public boolean isPresent(){ return change != null; }
	public String getConfirmPassword() { return confirmPassword; }
	public String getNewPassword()     { return newPassword;     }
	public String getCurrentPassword() { return currentPassword; }
	
	public void setConfirmPassword(String s) { confirmPassword = s.trim(); }
	public void setNewPassword(String s)     { newPassword     = s.trim(); }
	public void setCurrentPassword(String s) { currentPassword = s.trim(); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if(currentPassword == null || currentPassword.length() == 0){
			errors.add("Current Password is required");
		}

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		}
		
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Confirm Password is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!newPassword.equals(confirmPassword)) {
			errors.add("Passwords do not match");
			return errors;
		}
		
		String passwordFormat = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}";
		String password = newPassword; String confirm = confirmPassword;
        if(!password.matches(passwordFormat) || !confirm.matches(passwordFormat)) {
        	errors.add("New Password must: ");
        	errors.add("Have at least one lower case character");
        	errors.add("Have at least one capital letter");
        	errors.add("Have at least one number");
        	errors.add("Be from 4 characters to 20 characters");
        	return errors;
        }
        
		return errors;
	}
}
