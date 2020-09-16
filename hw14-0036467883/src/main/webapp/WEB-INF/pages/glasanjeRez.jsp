<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<style>
h1 {
margin-left:40px;
color:maroon;
}
</style>
</head>
<body>

<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja:</p>
<table border="1" style="width:80%">
<tr>
<th>Bend</th>
<th>Broj glasova</th>
</tr>
<c:forEach var="map" items="${scores}">
<tr>
<th>${bandInfo[map.key][0]}</th>
<th>${map.value}</th>
</tr>
</c:forEach>
</table>
<h1>Grafički prikaz rezultata</h1>
<img src=glasanje-chart> 
<h1>Rezultati u XLS formatu</h1>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-excel">ovdje</a><p>
<br>
<h1>Razno</h1>
<p>Pogledajte video link pobjednika!</p>
<ul>
<c:forEach var="map" items="${winners}">
<li><a href="${bandInfo[map][1]}">${bandInfo[map][0]}</a></li>
</c:forEach>
</ul>
</html>

