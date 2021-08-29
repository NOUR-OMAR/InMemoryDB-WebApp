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


		</nav>
	</header>
	<br>

	<div class="row">

		<div class="container">
			<h3 class="text-center">List of Grades</h3>
			<hr>

			<br>
			<table class="table table-bordered">
				<thead>
					<tr>

						<th>Course Id</th>
						<th>Course Title</th>
						<th>Student Name</th>
						<th>Grade</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${listGrades}" var="g" >
						<tr>
							<td><c:out value="${g.course.id}" /></td>
							<td><c:out value="${g.course.title}" /></td>
							<td><c:out value="${g.student.name}" /></td>
							<td><c:out value="${g.grade}" /></td>

						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>
</body>
</html>