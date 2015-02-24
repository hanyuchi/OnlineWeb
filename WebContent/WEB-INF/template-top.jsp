<html>
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title>Favorite Sharing Website</title>
	<style>
		.menu-head {font-size: 10pt; font-weight: bold; color: black; }
		.menu-item {font-size: 10pt;  color: black }
    </style>
</head>

<body>
<table cellpadding="4" cellspacing="0">
    <tr>
	    <!-- Banner row across the top -->
        <td width="130" bgcolor="#F5F4BE">
        </td>
        
        <td bgcolor="#F5F4BE">&nbsp;
        </td>
        
        <td width="500" bgcolor="#F5F4BE">
            <p align="center">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:choose>
		<c:when test="${not empty title}">
			<font size="5">${title}'s Favorite Websites</font>
		</c:when>
		<c:otherwise>
			<font size="5">Favorite Sharing Website</font>
		</c:otherwise>
	</c:choose>

			</p>
		</td>
    </tr>
	
	<!-- Spacer row -->
	<tr>
		<td bgcolor="#F5F4BE" style="font-size:5px">&nbsp;</td>
		<td colspan="2" style="font-size:5px">&nbsp;</td>
	</tr>
	
	<tr>
		<td bgcolor="#F5F4BE" valign="top" height="500">
			<!-- Navigation bar is one table cell down the left side -->
            <p align="left">
            
	<c:choose>
		<c:when test="${not empty user}">
			<span class="menu-head">${user.getFirstName()} ${user.getLastName()}</span><br/>
			<span class="menu-item"><a href="manage.do">Manage Your Favorites</a></span><br/>
			<span class="menu-item"><a href="change-pwd.do">Change Password</a></span><br/>
			<span class="menu-item"><a href="logout.do">Logout</a></span><br/>
		</c:when>
		<c:otherwise>
			<span class="menu-item"><a href="login.do">Login</a></span><br/>
			<span class="menu-item"><a href="register.do">Register</a></span><br/>
		</c:otherwise>
	</c:choose>
	
	<span class="menu-item">&nbsp;</span><br/>
	<span class="menu-head">Favorites From:</span><br/>
	
	<c:forEach var="u" items="${userList}">
		<span class="menu-item">
			<a href="list.do?userName=${u.getUserName()}">
				${u.getFirstName()} ${u.getLastName()}
			</a>
		</span>
		<br/>
	</c:forEach>

			</p>
		</td>
		
		<td>
			<!-- Padding (blank space) between navbar and content -->
		</td>
		<td  valign="top">
