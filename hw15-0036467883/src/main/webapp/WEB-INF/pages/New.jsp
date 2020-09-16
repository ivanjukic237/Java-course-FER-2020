<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
h1 {
	margin-left: 40px;
	color: maroon;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>

<body>
	<p>
		<a href="/blog/servleti/index">HOME</a>
	</p>
	<c:choose>
		<c:when test="${nick==user}">
			<h1>Dodajte novi zapis!</h1>
			<form action="/blog/servleti/new?nick=${nick}" method="post">

				<div>
					<span class="formLabel">Naslov</span><input type="text"
						name="naslov" value='' size="50">
				</div>
				<br> <br>

				<div>
					<textarea id="noviZapis" name="noviZapis" rows="4" cols="50">Ovdje ide tekst.</textarea>
				</div>
				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="metoda" value="Pohrani"> <input type="submit"
						name="metoda" value="Odustani">
				</div>
			</form>
		</c:when>
		<c:otherwise>
			<div>Error: Unauthorized Access</div>
		</c:otherwise>
	</c:choose>





	<br>
	<br>

</body>
</html>
