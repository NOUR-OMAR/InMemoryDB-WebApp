<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml"
          xmlns:th="http://www.thymeleaf.org"
          xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
          xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
<title>In memory db</title>
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
<div class="container col-md-5" >
		<div class="card">
			<div class="card-body">
			  <form action="${pageContext.request.contextPath}/login" method='POST'>
			      <p><font color="red">${errorMessage}</font></p>
			        <p><font color="green">${message}</font></p>

		<fieldset class="form-group">
    					<label>Username </label> <input type="text"
    						name="username" required="required">
    				</fieldset>


        <fieldset class="form-group">
            					<label>Password </label> <input type="password"
            						name="password" required="required">
            				</fieldset>


        	<button type="submit" class="btn btn-success">Login</button>
           <a href="javascript:toggle('showRegistrationForm')" class="btn btn-success">Register</a>


        				</form>


        				<div class="container col-md-5" id="showRegistrationForm" style="display: none;" >
                            		<div class="card">
                            			<div class="card-body">

                            					<form action="/register" >

                            				<caption>
                            					<h2>
                                        			Register
                            					</h2>
                            				</caption>


                            				<fieldset class="form-group">
                            					<label>username</label> <input type="text"
                            						value="<c:out value='${username}' />" class="form-control"
                            						name="username" required="required">
                            				</fieldset>

                            				<fieldset class="form-group">
                            					<label>password</label>
                            					<input type="text"
                            						value="<c:out value='${password}' />" class="form-control"
                            						name="password">
                            				</fieldset>

                            				<button type="submit" class="btn btn-success">Register</button>
                            				</form>
                            			</div>
                            		</div>
                            		</div>

</body>
</html>


<script type="text/javascript">
function toggle(layer) {
    var d = document.getElementById(layer);
    d.style.display = (d.style.display == 'none') ? '' : 'none';
    }
</script>