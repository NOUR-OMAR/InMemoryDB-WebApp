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


			<hr>

			<br>

			<table class="table table-bordered">
            				<thead>
            					<tr>

            						<th>Admin Id</th>
            						<th>Admin Name</th>

            					</tr>
            				</thead>
            				<tbody>

            					<c:forEach items="${employees}" var="a" >
            						<tr>
            							<td><c:out value="${a.id}" /></td>
            							<td><c:out value="${a.name}" /></td>

            						</tr>
            					</c:forEach>
            				</tbody>

            			</table>

		</div>
	</div>
</body>
</html>