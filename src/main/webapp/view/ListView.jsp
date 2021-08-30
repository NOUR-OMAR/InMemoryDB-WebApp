<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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
                                    <th>Employee id</th>
            						<th>Employee name</th>
                                    <th>Employee salary</th>
            						<th>Employee department id</th>
            						<th>Employee department name</th>
            						<th>Employee department location</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${employees}" var="employee" >
						<tr>
							<td><c:out value="${employee.id}" /></td>
							<td><c:out value="${employee.name}" /></td>
							<td><c:out value="${employee.salary}" /></td>
							<td><c:out value="${employee.department.id}" /></td>
							<td><c:out value="${employee.department.name}" /></td>
							<td><c:out value="${employee.department.location}" /></td>


						</tr>
					</c:forEach>
				</tbody>

			</table>

		</div>
	</div>
</body>
</html>