<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p style="font-size:medium; color:red">
<c:forEach var="error" items="${errors}">
	${error}</br>
</c:forEach>
</p>