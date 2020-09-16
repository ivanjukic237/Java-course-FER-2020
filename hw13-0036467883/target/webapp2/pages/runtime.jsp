<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
long timeElapsed = System.currentTimeMillis() - (Long)session.getServletContext().getAttribute("startTime");
long seconds = timeElapsed / 1000;
long minutes = seconds / 60;
long hours = minutes / 60;
long days = hours / 24;
hours = hours % 24;
minutes = minutes % 60;
seconds = seconds % 60;
%>

<html>
<title>Runtime</title>
<body style="background-color:${pickedBgCol};">
<a href="/webapp2/pages/index.jsp" >HOME</a><br><br>

<p>
  Runtime is: <%=days%> days, <%=hours%> hours, <%=minutes %> minutes and <%=seconds %> seconds.
</p>

</body>
</html>
