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
    				<li><a href="<%=request.getContextPath()%>/back"
    					class="nav-link">Back</a></li>


<li>
              <button onclick="document.getElementById('id03').style.display='block'" class="btn btn-success custom-width" >show departments in specific location</button>

</li>

<li><button onclick="document.getElementById('id02').style.display='block'" class="btn btn-success custom-width" >show departments with specific name</button></li>

    			</ul>
    		</nav>
    	</header>

 <br>
 <div class="container text-left">

                                     <a href="javascript:toggle('showDepartmentForm')" class="btn btn-success">Add department</a>
                      </div>


	<br>

	<div id="wrap">

		<div class="container">


			<hr>

			<br>

			<table class="datatable table table-striped table-bordered">
            				<thead>
            					<tr>

            						<th>Department id</th>
            						<th>Department name</th>
                                    <th>Department location</th>

            						<th>Update department</th>
                                   <th>Delete department</th>

            					</tr>
            				</thead>
            				<tbody>

            					<c:forEach items="${departments}" var="department"  >
            						<tr>

            							<td><c:out value="${department.id}" /></td>
            							<td><c:out value="${department.name}" /></td>
            							<td><c:out value="${department.location}" /></td>
                          <td>   <button onclick="document.getElementById('id01').style.display='block'" class="btn btn-success custom-width" >update</button>
</td>
                                 <td><a href="<c:url value='/deleteDepartment-${department.id}' />" class="btn btn-danger custom-width">delete</a></td>

            						</tr>
            					</c:forEach>
            				</tbody>

            			</table>


		</div>

	</div>


	<div class="container col-md-5" id="showDepartmentForm" style="display: none;" >
    		<div class="card">
    			<div class="card-body">

    					<form action="/addDepartment" >

    				<caption>
    					<h2>
                			Add new Department
    					</h2>
    				</caption>


    				<fieldset class="form-group">
    					<label>Department Id</label> <input type="text"
    						value="<c:out value='${department.id}' />" class="form-control"
    						name="id" required="required">
    				</fieldset>

    				<fieldset class="form-group">
    					<label>Department name</label>
    					<input type="text"
    						value="<c:out value='${department.name}' />" class="form-control"
    						name="name">
    				</fieldset>

    				<fieldset class="form-group">
                    					<label>Department location</label> <input type="text"
                    						value="<c:out value='${department.location}' />" class="form-control"
                    						name="location">
                    				</fieldset>


    				<button type="submit" class="btn btn-success">Add</button>
    				</form>
    			</div>
    		</div>
    		</div>



                <div  id="id01" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/updateDepartment">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>ID</b></label>
                       <input type="text"    placeholder="Enter id" name="id">

	</fieldset>
	<fieldset class="form-group">
                            <label><b>Name</b></label>
                            <input type="text" placeholder="Enter Name" name="name" required>
	</fieldset>
<fieldset class="form-group">
                            <label><b>Location</b></label>
                            <input type="text" placeholder="Enter Location" name="location" required>
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
                    <form class="modal-content animate" action="/filterByName">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>name</b></label>
                       <input type="text"     value="<c:out value='${department.name}' />" class="form-control" name="name">

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
                    <form class="modal-content animate" action="/filterByLocation">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>location</b></label>
                       <input type="text"     value="<c:out value='${department.location}' />" class="form-control" name="location">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id03').style.display='none'" class="btn btn-danger ">Cancel</button>
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