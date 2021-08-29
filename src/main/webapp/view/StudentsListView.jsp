<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Student Grading System</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: LightGray">

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/login"
					class="nav-link">Log out</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">

		<div class="container">
			<hr>



			<br>

			<table class="table table-bordered">
				<thead>
					<tr>

						<th>Student Id</th>
						<th>Student Name</th>
						<th>Student Major</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${listStudents}" var="s" >
						<tr>
							<td><c:out value="${s.id}" /></td>
							<td><c:out value="${s.name}" /></td>
							<td><c:out value="${s.major}" /></td>

						</tr>
					</c:forEach>
				</tbody>

			</table>

		</div>
	</div>
</body>
</html>