<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Add your new favorite website:
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="addFavorite.do">
		<table>
			<tr>
				<td>Your favorite website: </td>
				<td colspan="2"><input size="30" type="text" name="url" value="${form.getURL()}"/></td>
			</tr>
			<tr>
				<td>Comments: </td>
				<td><input size="30" type="text" name="comments" value="${form.getComment()}"/></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Add Your Favorite!"/>
				</td>
			</tr>
		</table>
	</form>
</p>
<hr/>
	<script type="text/javascript">
	  function submit() {	  
		  document.getElementById("form").submit();	  
	  }
	</script>

<p>
	<table>
	
<%--	
	<%@ page import="databeans.Favorite"%>
	<%@ page import="java.util.List"%>
	<%
        List<Favorite> list = (List<Favorite>) request.getAttribute("favoriteList");
        for (int i = 0; i < list.size(); i++) { 
%>
        <tr>
            <td valign="top">
                <form method="POST" action="remove.do">
                    <input type="hidden" name="id" value="<%=list.get(i).getFavoriteId()%>"/>
                    <input type="submit" value="delete"/>
                </form>
            </td>
            <td valign="top">
                <form id="click" method="POST" action="addClick.do">
                    <input type="hidden" name="favoriteID" value="<%=list.get(i).getFavoriteId()%>"/>
                    <a href="<%=list.get(i).getURL()%>" onclick="submit();" target="_blank"><%=list.get(i).getURL()%></a>
                </form>
            </td>
            <td valign="top"><%=list.get(i).getClickCount()%> Clicks</a></td>
        </tr>
        <tr>
        	<td colspan="5" valign="top"><%=list.get(i).getComment()%></td>
        </tr>
<%
		}
%>
--%>

	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:forEach var="item" items="${favoriteList}">
	    <tr>
	        <td valign="top">
                <form id="form" method="POST" action="remove.do">
                    <input type="hidden" name="id" value="${item.getFavoriteId()}"/>
                    <input type="submit" value="delete"/>
                </form>
            </td>
        	<td colspan="3" valign="top">
                <form method="POST" action="addClick.do">
                    <input type="hidden" name="favoriteID" value="${item.getFavoriteId()}"/>
                    <a href="${item.getURL()}" onclick="submit()" target="_blank">${item.getURL()}</a>
                </form>
            </td>
            <td valign="top">${item.getClickCount()} Clicks</a></td>
        </tr>
        <tr>
        	<td colspan="5" valign="top">${item.getComment()}</td>
        </tr>
	</c:forEach>
	 
	
	</table>
</p>

<jsp:include page="template-bottom.jsp" />
