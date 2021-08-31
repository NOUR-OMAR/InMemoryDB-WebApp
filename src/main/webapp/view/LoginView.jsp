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
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Log out</a></li>
			</ul>
		</nav>
	</header>
	<br>
<div class="container col-md-5" >
		<div class="card">
			<div class="card-body">
			  <form action="${pageContext.request.contextPath}/login" >
			      <p><font color="red">${errorMessage}</font></p>

		<fieldset class="form-group">
    					<label>Name </label> <input type="text"
    						name="username" required="required">
    				</fieldset>


        <fieldset class="form-group">
            					<label>Password </label> <input type="password"
            						name="password" required="required">
            				</fieldset>


        	<button type="submit" class="btn btn-success">Login</button>
        				</form>

</body>
</html>