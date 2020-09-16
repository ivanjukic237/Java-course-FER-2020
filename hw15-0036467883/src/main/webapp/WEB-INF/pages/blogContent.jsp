<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
h1 {
	margin-left: 40px;
	color: maroon;
}

.date {
	color: grey;
	font-size: 60%;
}

.user {
	color: maroon;
	font_size: 120%;
}
</style>
</head>

<body>
	<p>
		<a href="/blog/servleti/index">HOME</a>
	</p>
	<c:choose>
		<c:when test="${nick==user}">
			<form action="/blog/servleti/edit?nick=${nick}&id=${id}"
				method="post">

				<div>
					<span class="formLabel">Naslov<br> <br></span><input
						type="text" name="naslov" value='${title}' size="50">
				</div>
				<br>
				<p>Tekst</p>
				<div>
					<textarea id="noviZapis" name="noviZapis" rows="4" cols="50">${text}</textarea>
				</div>
				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="metoda" value="Pohrani"> <input type="submit"
						name="metoda" value="Odustani">
				</div>
			</form>
		</c:when>
		<c:otherwise>
			<h1>${title}</h1>
			<p>${text}</p>
		</c:otherwise>
	</c:choose>
	<ul>
		<c:forEach var="comment" items="${comments}">
			<div>
				<p class="user">${comment.usersEMail}</p>
				<p>${comment.message}</p>
				<p class="date">-${comment.postedOn}</p>
			</div>
			<br>
		</c:forEach>
	</ul>
	<p class="user">Dodaj Komentar:</p>
<form action="/blog/servleti/comment?nick=${nick}&id=${id}"
				method="post">

				<div>
					<span class="formLabel">Nadimak<br> <br></span><input
						type="text" name="email" value='' size="50">
				</div>
				<br>
				<p>Tekst</p>
				<div>
					<textarea id="noviZapis" name="noviZapis" rows="4" cols="50"></textarea>
				</div>
				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="metoda" value="Dodaj">
				</div>
			</form>
</body>
</html>
