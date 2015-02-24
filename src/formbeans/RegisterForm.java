/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package formbeans;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
public class RegisterForm {
	private String userName;
	private String lastName;
	private String firstName;
    private String password;
    private String button;
    private String confirm;
    private List<String> ret;
    
    public RegisterForm(HttpServletRequest request){
    	button = request.getParameter("button");
    	lastName = request.getParameter("lastName");
    	firstName = request.getParameter("firstName");
    	userName = request.getParameter("userName");
    	password = request.getParameter("password");
    	confirm = request.getParameter("confirm");
    	if(userName != null) userName = userName.trim().toLowerCase();
    	if(lastName != null) lastName = lastName.trim().toLowerCase();
    	if(firstName != null) firstName = firstName.trim().toLowerCase();
    	if(isPresent()) error();
    }
    
    public boolean isPresent(){
    	return button != null;
    }
    
    /*getters*/
    public String getFirstName(){
    	return firstName;
    }
    public String getLastName(){
    	return lastName;
    }
    public String getUserName(){
    	return userName;
    }
    public String getPassword(){
    	return password;
    }
    public List<String> getValidationErrors(){
    	return ret;
    }
    
    /*setters*/
    public void addError(String str){
    	ret.add(str);
    }
    public void clearError(){
    	ret.clear();
    }
    
    private void error(){
    	ret = new ArrayList<String>();
    	
    	String passwordFormat = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}";
    	String name = "[a-zA-Z]+";
    	
    	if(firstName == null || firstName.length() == 0) ret.add("First Name required.");
    	else if(!firstName.matches(name)) ret.add("First Name must be letters.");
    	if(lastName == null || lastName.length() == 0) ret.add("Last Name required.");
    	else if(!lastName.matches(name)) ret.add("Last Name must be letters.");
    	if(userName == null || userName.length() == 0) ret.add("User Name required.");
        if(password == null || password.length() == 0 || confirm == null || confirm.length() == 0) ret.add("Password required.");
        else if(!password.matches(passwordFormat) || !confirm.matches(passwordFormat)) {
        	ret.add("Password must: ");
        	ret.add("Have at least one lower case character");
        	ret.add("Have at least one capital letter");
        	ret.add("Have at least one number");
        	ret.add("Be from 4 characters to 20 characters");
        }
        
        if(!confirm.equals(password)) ret.add("Passwords are not the same.");
    }
}
