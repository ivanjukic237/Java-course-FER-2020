<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
h1 {
	margin-left: 40px;
	color: maroon;
}
.topcorner {
	position: absolute;
	top: 0;
	right: 40;
}
</style>
</head>

<body>
<p><a href="/blog/servleti/index">HOME</a></p>
	<c:choose>
		<c:when test="${titles.isEmpty()}">
      Nema unosa!
    </c:when>
		<c:otherwise>
			<h1>Popis naslova korisnika: ${korisnik}</h1>
			<ul>
				<c:forEach var="map" items="${titles}">
					<li><a href="${korisnik}/${map.key}">${map.value}</a></li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${user==korisnik}">
			<p>
				<a href="${korisnik}/new">Dodajte zapis!</a>
			</p>
		</c:when>
	</c:choose>
</body>
</html>
