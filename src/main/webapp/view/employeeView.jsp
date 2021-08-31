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
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">logout</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div id="wrap">

    		<div class="container">


    			<hr>

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

                					<c:forEach items="${employee}" var="emp"  >
                						<tr>
                							<td><c:out value="${emp.id}" /></td>
                							<td><c:out value="${emp.name}" /></td>
                							<td><c:out value="${emp.salary}" /></td>
                							<td><c:out value="${emp.department.id}" /></td>
                							<td><c:out value="${emp.department.name}" /></td>
                							<td><c:out value="${emp.department.location}" /></td>

                						</tr>
                					</c:forEach>
                				</tbody>

                			</table>


    		</div>

    	</div>


</body>
</html>