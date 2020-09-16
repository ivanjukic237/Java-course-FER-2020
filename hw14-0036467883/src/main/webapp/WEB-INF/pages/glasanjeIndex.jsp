<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<style>
h1 {
margin-left:40px;
color: maroon;
}
</style>
</head>
<body>
<h1>Odaberite anketu kojoj želite pristupiti</h1>
	<ol>
		<c:forEach var="map" items="${listOfPolls}">
			<li><a href="glasanje?pollID=${map.key}">${map.value}</a></li>
		</c:forEach>
	</ol>

</body>
</html>