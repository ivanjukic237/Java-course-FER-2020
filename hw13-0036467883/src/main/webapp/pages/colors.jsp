<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<title>Change bgcolor</title>
<body style="background-color:${pickedBgCol};">

<a href="/webapp2/pages/index.jsp" >HOME</a><br><br>
<p>Choose the background color:<br>
<a href="/webapp2/setcolor?color=white" >WHITE</a>
<br><a href="/webapp2/setcolor?color=red" >RED</a>
<br><a href="/webapp2/setcolor?color=green" >GREEN</a>
<br><a href="/webapp2/setcolor?color=cyan" >CYAN</a>


</body>
</html>
