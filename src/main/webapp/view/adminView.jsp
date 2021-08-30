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
    				<li><a href="<%=request.getContextPath()%>/close"
    					class="nav-link">Close and Log out</a></li>
    			</ul>
    		</nav>
    	</header>
	 <div class="container text-left">

                                        <button onclick="document.getElementById('id04').style.display='block'" class="btn btn-success custom-width" >show employees with salaries less than a value</button>
                          </div>
            <br>
            <div class="container text-left">

                                                    <button onclick="document.getElementById('id03').style.display='block'" class="btn btn-success custom-width" >show employees with salaries greater than a value</button>
                                      </div>
                        <br>
                        <div class="container text-left">

                                                                <button onclick="document.getElementById('id02').style.display='block'" class="btn btn-success custom-width" >show employees with salaries equals a value</button>
                                                  </div>
                                    <br>
                                     <div class="container text-left">

                                                                                                    <button onclick="document.getElementById('id05').style.display='block'" class="btn btn-success custom-width" >show employees with specific names</button>
                                                                                      </div>
                                                                        <br>


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
            						<th>Update employee</th>
                                   <th>Delete employee</th>

            					</tr>
            				</thead>
            				<tbody>

            					<c:forEach items="${employees}" var="employee"  >
            						<tr>
            							<td><c:out value="${employee.id}" /></td>
            							<td><c:out value="${employee.name}" /></td>
            							<td><c:out value="${employee.salary}" /></td>
            							<td><c:out value="${employee.department.id}" /></td>
            							<td><c:out value="${employee.department.name}" /></td>
            							<td><c:out value="${employee.department.location}" /></td>
                               <!-- <td><a href="<c:url value='/updateEmployee-${employee.id}' />" class="btn btn-success custom-width">update</a></td> -->
                          <td>   <button onclick="document.getElementById('id01').style.display='block'" class="btn btn-success custom-width" >update</button>
</td>
                                 <td><a href="<c:url value='/deleteEmployee-${employee.id}' />" class="btn btn-danger custom-width">delete</a></td>

            						</tr>
            					</c:forEach>
            				</tbody>

            			</table>


		</div>
		  <div class="container text-left">

                                     <a href="javascript:toggle('showEmployeeForm')" class="btn btn-success">Add employee</a>
                      </div>
        <br>
	</div>


	<div class="container col-md-5" id="showEmployeeForm" style="display: none;" >
    		<div class="card">
    			<div class="card-body">

    					<form action="/addEmployee" >

    				<caption>
    					<h2>
                			Add new Employee
    					</h2>
    				</caption>


    				<fieldset class="form-group">
    					<label>Employee Id</label> <input type="text"
    						value="<c:out value='${employee.id}' />" class="form-control"
    						name="id" required="required">
    				</fieldset>

    				<fieldset class="form-group">
    					<label>Employee name</label>
    					<input type="text"
    						value="<c:out value='${employee.name}' />" class="form-control"
    						name="name">
    				</fieldset>

    				<fieldset class="form-group">
                    					<label>Employee salary</label> <input type="text"
                    						value="<c:out value='${employee.salary}' />" class="form-control"
                    						name="salary">
                    				</fieldset>

                    			<fieldset class="form-group">
                                                        					<label>Employee department</label> <input type="text"
                                                        						value="<c:out value='${employee.department.id}' />" class="form-control"
                                                        						name="departmentId">
                                                        				</fieldset>

    				<button type="submit" class="btn btn-success">Add</button>
    				</form>
    			</div>
    		</div>
    		</div>


               <!-- <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Sign Up</button>-->

                <div  id="id01" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/updateEmployee">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>ID</b></label>
                       <input type="text"     value="<c:out value='${employee.id}' />" class="form-control" name="id">

                           <!-- <input type="text" placeholder="Enter Email" name="email" required> -->
	</fieldset>
	<fieldset class="form-group">
                            <label><b>Name</b></label>
                            <input type="text" placeholder="Enter Name" name="name" required>
	</fieldset>
<fieldset class="form-group">
                            <label><b>Salary</b></label>
                            <input type="text" placeholder="Enter salary" name="salary" required>
                            	</fieldset>
<fieldset class="form-group">
                            <label><b>Department id</b></label>
                           <input type="text" placeholder="Enter id" name="departmentId" required>
	</fieldset>
                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id01').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">Save</button>
                            </div>
                        </div>
                    </form>
                </div>

 <div  id="id02" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryEQ">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id02').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>

 <div  id="id03" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryGT">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id03').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>


<div  id="id04" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id04').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryLT">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id04').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div  id="id05" class="modal"  role="dialog">
                                  <span onclick="document.getElementById('id05').style.display='none'" class="close" title="Close Modal">×</span>
                                    <form class="modal-content animate" action="/listNames">
                                        <div class="container">
                                        <fieldset class="form-group">
                                            <label><b>name</b></label>
                                       <input type="text"     value="<c:out value='${employee.name}' />" class="form-control" name="name">

                	</fieldset>

                                            <div class="clearfix">
                                                <button type="button" onclick="document.getElementById('id05').style.display='none'" class="btn btn-danger ">Cancel</button>
                                                <button type="submit" class="btn btn-success">get</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>

</body>
</html>

<script type="text/javascript">
function toggle(layer) {
    var d = document.getElementById(layer);
    d.style.display = (d.style.display == 'none') ? '' : 'none';
    }


   var modal = document.getElementById('id01');

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }


 var modal = document.getElementById('id02');

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

         var modal = document.getElementById('id03');

                window.onclick = function(event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
                 var modal = document.getElementById('id04');

                        window.onclick = function(event) {
                            if (event.target == modal) {
                                modal.style.display = "none";
                            }
                        }
                        var modal = document.getElementById('id05');

                                                window.onclick = function(event) {
                                                    if (event.target == modal) {
                                                        modal.style.display = "none";
                                                    }
                                                }
</script>