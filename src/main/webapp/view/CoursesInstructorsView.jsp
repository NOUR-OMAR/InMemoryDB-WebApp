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

						<th>Course Id</th>
						<th>Course Title</th>
						<th>Course Instructor</th>
						<th>Instructor Name</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list_Courses}" var="c" >
						<tr>
							<td><c:out value="${c.id}" /></td>
							<td><c:out value="${c.title}" /></td>
							<td><c:out value="${c.instructor.id}" /></td>
							<td><c:out value="${c.instructor.name}" /></td>

						</tr>
					</c:forEach>
				</tbody>

			</table>

		</div>
	</div>
</body>
</html>