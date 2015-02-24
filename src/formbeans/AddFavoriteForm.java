/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package formbeans;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class AddFavoriteForm {
	 private String url;
	 private String comment;
	 private List<String> ret;
	 
	 public AddFavoriteForm(HttpServletRequest request){
		 url = request.getParameter("url");
		 comment = request.getParameter("comments");
		 if(url != null) url = url.trim();
		 if(comment != null) comment = comment.trim();
		 show();
		 if(ret.size()==0){
			 if(!url.startsWith("http")){
				 url = "http://"+url;
			 }
		 }
	 }
	 
	 /*getters*/
	 public String getURL(){
		 return url;
	 }
	 public String getComment(){
		 return comment;
	 }
	 public List<String> getError(){
		 return ret;
	 }
	 
	 /*setters*/
	 public void clearURLandComment(){
		 url = null;
		 comment = null;
	 }
	 public void clear(){
		 ret.clear();
	 }
	 public void add(String str){
		 ret.add(str);
	 }
	 
	 private void show(){
		 ret = new ArrayList<String>();
		 if(comment == null || comment.length() == 0) ret.add("Comments required.");
		 if(url == null || url.length() == 0) ret.add("URL required.");
	 }
}
