<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Registracija</title>

<style type="text/css">
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
<p><a href="/blog/servleti/index">HOME</a></p>

	<h1>Registracija</h1>

	<form action="save" method="post">

		<div>
			<div>
				<span class="formLabel">Nadimak</span><input type="text" name="nick"
					value='<c:out value="${zapis.nick}"/>' size="20">
			</div>
			<c:if test="${zapis.imaPogresku('nick')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('nick')}" />
				</div>
			</c:if>
			<c:if test="${zapis.imaPogresku('nick_unique')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('nick_unique')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Ime</span><input type="text" name="ime"
					value='<c:out value="${zapis.ime}"/>' size="20">
			</div>
			<c:if test="${zapis.imaPogresku('ime')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('ime')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Prezime</span><input type="text"
					name="prezime" value='<c:out value="${zapis.prezime}"/>' size="20">
			</div>
			<c:if test="${zapis.imaPogresku('prezime')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('prezime')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">EMail</span><input type="text" name="email"
					value='<c:out value="${zapis.email}"/>' size="20">
			</div>
			<c:if test="${zapis.imaPogresku('email')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('email')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Lozinka</span><input type="text"
					name="password" value='<c:out value="${zapis.passwordHash}"/>'
					size="20">
			</div>
			<c:if test="${zapis.imaPogresku('password')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('password')}" />
				</div>
			</c:if>
		</div>




		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="Pohrani"> <input type="submit"
				name="metoda" value="Odustani">
		</div>

	</form>

</body>
</html>
