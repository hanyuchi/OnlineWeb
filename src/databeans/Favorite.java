/*
 * Name: Yuchi Han
 * Date: 12/01/2014
 * Course: 08-600
 */
package databeans;

public class Favorite {
    private int favorite_id;
	private String url;
	private String comment;
	private int count;
	private String username;
	
	/*getters*/
	public int getFavoriteId(){
    	return favorite_id;
    }
	public String getURL(){
		return url;
	}
	public String getComment(){
		return comment;
	}
	public int getClickCount(){
		return count;
	}
	public String getUserName(){
		return username;
	}
    
    /*setters*/
	public void setFavoriteId(int id){
		favorite_id = id;
	}
    public void setURL(String str){
    	url = str;
    }
	public void setComment(String str){
		comment = str;
	}
	public void setClickCount(int count){
		this.count = count;
	}
	public void setUserName(String u){
		this.username = u;
	}
}
