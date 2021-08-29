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
				<li><a href="<%=request.getContextPath()%>/login"
					class="nav-link">Log out</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">

		<div class="container">
			<h3 class="text-center">Welcome ${name} </h3>
			<hr>

			<div class="container text-left">

            				<a href="<%=request.getContextPath()%>/listCourses" class="btn btn-success">See Courses</a>
            			</div>
			<br>

            <div class="container text-left">

                             <a href="<%=request.getContextPath()%>/listStudents" class="btn btn-success">See Students</a>
                         </div>

			<br>


            <div class="container text-left">
                           <a href="javascript:toggle('showGradesForm')" class="btn btn-success">Add Grade</a>
                        </div>



			<br>
<div class="container col-md-5"  id="showGradesForm" style="display: none;" >
		<div class="card">
			<div class="card-body">

					<form action="/addGrade" method="POSTt">

				<caption>
					<h2>

            			Assign new grade
					</h2>
				</caption>


				<fieldset class="form-group">
					<label>grade</label> <input type="text"
						value="<c:out value='${grade.grade}' />" class="form-control"
						name="grade" required="required">
				</fieldset>
					<fieldset class="form-group">
                					<label>Course Name</label>

                					<select name="courseID">
                                                                                        <c:forEach items="${listCourses}" var="courseID">
                                                                                            <option value="${courseID.id}">${courseID.title}</option>
                                                                                        </c:forEach>
                                                                                    </select>



                				</fieldset>

				<fieldset class="form-group">
					<label>Student Name</label>




						<select name="studentId">
                                                    <c:forEach items="${listStudents}" var="studentId">
                                                        <option value="${studentId.id}">${studentId.name}</option>
                                                    </c:forEach>
                                                </select>
				</fieldset>



				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
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