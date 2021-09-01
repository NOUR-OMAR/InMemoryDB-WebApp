<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<security:authorize access="hasRole('ROLE_USER')">

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: LightGray">

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">logout</a></li>
			</ul>
		</nav>
	</header>
	<br>

<div id="wrap">

		<div class="container">


			<hr>
<h1>${employee.name} information</h1>
			<br>

			<table class="datatable table table-striped table-bordered">
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

            						<tr>
            							<td><c:out value="${employee.id}" /></td>
            							<td><c:out value="${employee.name}" /></td>
            							<td><c:out value="${employee.salary}" /></td>
            							<td><c:out value="${employee.department.id}" /></td>
            							<td><c:out value="${employee.department.name}" /></td>
            							<td><c:out value="${employee.department.location}" /></td>

            						</tr>
            				</tbody>

            			</table>


		</div>

	</div>

	</security:authorize>
</body>
</html>