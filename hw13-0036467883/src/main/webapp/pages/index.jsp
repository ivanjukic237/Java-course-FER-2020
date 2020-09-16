<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<title>Home</title>
<body style="background-color:${pickedBgCol};">

<p><a href="colors.jsp">Background color chooser</a></p>
<p><a href="/webapp2/trigonometric?a=0&b=90">Trigonometric table</a></p>

<form action="/webapp2/trigonometric" method="GET">
 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
<p><a href="/webapp2/stories/funny.jsp">Funny story</a></p>
<p><a href="/webapp2/pages/report.jsp">Report image</a></p>
<p><a href="/webapp2/powers?a=1&b=100&n=3">Download powers Excel file</a></p>
<p><a href="/webapp2/pages/runtime.jsp">Runtime</a></p>
<p><a href="/webapp2/glasanje">Glasanje</a></p>
<p><a href="jsPractice.jsp">Practice</a></p>
</body>


</html>