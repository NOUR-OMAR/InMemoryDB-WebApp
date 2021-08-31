<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Admin View</title>
<meta http-equiv="content-type" content="text/html;charset=iso-8859-1">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">


	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">

</head>
<body>
	<header>
    		<nav class="navbar navbar-expand-md navbar-dark"
    			style="background-color: LightGray">

    			<ul class="navbar-nav">
    				<li><a href="<%=request.getContextPath()%>/logout"
    					class="nav-link">Close and Log out</a></li>

    		<li> <button onclick="document.getElementById('id04').style.display='block'" class="btn btn-success custom-width" >show employees with salaries less than a value</button>
                   </li>
<li>
              <button onclick="document.getElementById('id03').style.display='block'" class="btn btn-success custom-width" >show employees with salaries greater than a value</button>

</li>

<li><button onclick="document.getElementById('id02').style.display='block'" class="btn btn-success custom-width" >show employees with salaries equals a value</button></li>

    <li> <button onclick="document.getElementById('id05').style.display='block'" class="btn btn-success custom-width" >show employees with specific names</button></li>
    			</ul>
    		</nav>
    	</header>
<br>


  <div class="container text-left">

                                     <a href="<%=request.getContextPath()%>/departmentView" class="btn btn-success">Show Departments</a>
                      </div>
        <br>

         <div class="container text-left">

                                             <a href="javascript:toggle('showEmployeeForm')" class="btn btn-success">Add Employee</a>
                              </div>
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


<script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.11.0/js/jquery.dataTables.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
    			$('#wrap').dataTable({
    				"sPaginationType": "bs_full"
    			});
    			$('.datatable').each(function(){
    				var datatable = $(this);
    				// SEARCH - Add the placeholder for Search and Turn this into in-line form control
    				var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
    				search_input.attr('placeholder', 'Search');
    				search_input.addClass('form-control input-sm');
    				// LENGTH - Inline-Form control
    				var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
    				length_sel.addClass('form-control input-sm');
    			});
    		});
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

</body>
</html>