<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body style="background-color:${pickedBgCol};">
	<a href="/webapp2/pages/index.jsp">HOME</a>
	<br>
	<br>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>

	<ol>
		<c:forEach var="map" items="${bandInfo}">
			<li><a href="glasanje-glasaj?id=${map.key}">${map.value[0]}</a></li>
		</c:forEach>
	</ol>

</body>
</html>