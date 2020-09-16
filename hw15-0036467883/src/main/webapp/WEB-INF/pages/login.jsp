<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Prijava</title>

<style type="text/css">
h1 {
	margin-left: 40px;
	color: maroon;
}

.topcorner {
	position: absolute;
	top: 0;
	right: 10;
}

.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
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
		<c:when test="${not empty user}">
			<div>
				<div class="topcorner">${first_name}
					${last_name} <a href="/blog/servleti/logout">Odlogirajte se</a>
				</div>
			</div>

		</c:when>
		<c:otherwise>
			<span class="topcorner">Niste prijavljeni</span>
			<h1>Prijava</h1>

			<form action="main" method="post">

				<div>
					<div>
						<span class="formLabel">Nadimak</span><input type="text"
							name="nick" value='<c:out value="${zapis.nick}"/>' size="50">
					</div>
					<c:if test="${zapis.imaPogresku('nick')}">
						<div class="greska">
							<c:out value="${zapis.dohvatiPogresku('nick')}" />
						</div>
					</c:if>
					<c:if test="${zapis.imaPogresku('no_nick')}">
						<div class="greska">
							<c:out value="${zapis.dohvatiPogresku('no_nick')}" />
						</div>
					</c:if>
				</div>


				<div>
					<div>
						<span class="formLabel">Lozinka</span><input type="text"
							name="password" value='<c:out value=""/>' size="50">
					</div>
					<c:if test="${zapis.imaPogresku('password')}">
						<div class="greska">
							<c:out value="${zapis.dohvatiPogresku('password')}" />
						</div>
					</c:if>
					<c:if test="${zapis.imaPogresku('wrong_pass')}">
						<div class="greska">
							<c:out value="${zapis.dohvatiPogresku('wrong_pass')}" />
						</div>
					</c:if>
				</div>
				<div class="formControls">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="metoda" value="Ulogiraj se">
				</div>
			</form>


			<br>
		</c:otherwise>
	</c:choose>

	<p>
		<a href="registration">Registracija</a>
	</p>
	<p>Registrirani autori</p>
	<ul>
		<c:forEach var="author" items="${authors}">
			<li><a href="author/${author}">${author}</a></li>
		</c:forEach>
	</ul>
</body>
</html>
