<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<title>Tablica</title>
<body style="background-color:${pickedBgCol};">
<a href="/webapp2/pages/index.jsp" >HOME</a><br><br>

<table style="width:80%">
<tr>
<th>x</th>
<th>sin(x)</th>
<th>cos(x)</th>
</tr>
<c:forEach var="map" items="${trigonometryMap}">
<tr>
<th>${map.key}</th>
<th>${map.value[0]}</th>
<th>${map.value[1]}</th>
</tr>
</c:forEach>


</table>

</html>