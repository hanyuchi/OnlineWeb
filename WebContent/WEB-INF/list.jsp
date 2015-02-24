<jsp:include page="template-top.jsp" />
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
        	<td colspan="3" valign="top">
                <form id="click" method="POST" action="addClickByVisitor.do">
                    <input type="hidden" name="favoriteID" value="<%=list.get(i).getFavoriteId()%>"/>
                    <a href="<%=list.get(i).getURL()%>" onclick="submit();" target="_blank"><%=list.get(i).getURL()%></a>
                </form>
            </td>
            <td valign="top"><%=list.get(i).getClickCount()%> Clicks</a></td>
        </tr>
        <tr>
        	<td colspan="4" valign="top"><%=list.get(i).getComment()%></td>
        </tr>
<%
		}
%>
	--%>
	
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:forEach var="item" items="${favoriteList}">
	    <tr>
        	<td colspan="3" valign="top">
                <form id="form" method="POST" action="addClickByVisitor.do">
                    <input type="hidden" name="favoriteID" value="${item.getFavoriteId()}"/>
                	<a href="${item.getURL()}" onclick="submit()" target="_blank">${item.getURL()}</a>
                </form>
            </td>
            <td valign="top">${item.getClickCount()} Clicks</a></td>
        </tr>
        <tr>
        	<td colspan="4" valign="top">${item.getComment()}</td>
        </tr>
	</c:forEach>

	</table>
</p>

<jsp:include page="template-bottom.jsp" />
