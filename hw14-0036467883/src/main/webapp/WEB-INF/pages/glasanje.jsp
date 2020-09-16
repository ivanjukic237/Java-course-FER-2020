<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<style>
h1 {
	margin-left: 40px;
	color: maroon;
}
</style>
</head>
<body>
	<h1>${pollInfo[0] }</h1>
	<p>${pollInfo[1] }</p>

	<ol>
		<c:forEach var="map" items="${bandInfo}">
			<li><a href="glasanje-glasaj?id=${map.key}">${map.value[0]}</a></li>
		</c:forEach>
	</ol>

</body>
</html>