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

		<div class="container">
			<h3 class="text-center">Welcome ${name} </h3>
			<hr>

			<div class="container text-left">
              <a href="javascript:toggle('showCoursesForm')" class="btn btn-success">Create course</a>
            </div>
            <br>

            <div class="container text-left">

                             <a href="javascript:toggle('showStudentForm')" class="btn btn-success">Add student</a>
              </div>
<br>
  <div class="container text-left">

                             <a href="javascript:toggle('showInstructorsForm')" class="btn btn-success">Add Instructor</a>
              </div>
<br>
 <div class="container text-left">

                             <a href="javascript:toggle('showAssignInstructorForm')" class="btn btn-success">Assign Instructor to course</a>
              </div>
<br>
              <div class="container text-left">

                                           <a href="javascript:toggle('showAssignStudentForm')" class="btn btn-success">Assign Student to course</a>
                            </div>
<br>
<div class="container text-left">

                             <a href="javascript:toggle('showAdminsForm')"  class="btn btn-success">Create Admin</a>
              </div>




			<br>

<div class="container col-md-5" id="showCoursesForm" style="display: none;" >
		<div class="card"  >
			<div class="card-body">

					<form action="/createCourse" method="POSTt" >


				<caption>
					<h2>


            			Create course

					</h2>
				</caption>


				<fieldset class="form-group">
					<label>Course id</label> <input type="text"
						value="<c:out value='${course.courseID}' />" class="form-control"
						name="courseID" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Title</label> <input type="text"
						value="<c:out value='${course.title}' />" class="form-control"
						name="title">
				</fieldset>


				<button type="submit" class="btn btn-success"   >Add</button>
				</form>
			</div>
		</div>
</div>

<div class="container col-md-5" id="showStudentForm" style="display: none;" >
		<div class="card">
			<div class="card-body">

					<form action="/addStudent" method="POSTt">

				<caption>
					<h2>

            			Add new student
					</h2>
				</caption>


				<fieldset class="form-group">
					<label>Student Id</label> <input type="text"
						value="<c:out value='${student.id}' />" class="form-control"
						name="studentID" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Student name</label> <input type="text"
						value="<c:out value='${student.name}' />" class="form-control"
						name="name">
				</fieldset>

				<fieldset class="form-group">
                					<label>Student major</label> <input type="text"
                						value="<c:out value='${student.major}' />" class="form-control"
                						name="major">
                				</fieldset>

				<button type="submit" class="btn btn-success">Add</button>
				</form>
			</div>
		</div>
		</div>

<div class="container col-md-5" id="showInstructorsForm" style="display: none;" >
		<div class="card">
			<div class="card-body">

					<form action="/addInstructor" method="POSTt">

				<caption>
					<h2>

            			Add new instructor
					</h2>
				</caption>


				<fieldset class="form-group">
					<label>Instructor Id</label> <input type="text"
						value="<c:out value='${instructor.id}' />" class="form-control"
						name="instructorId" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Instructor name</label> <input type="text"
						value="<c:out value='${instructor.name}' />" class="form-control"
						name="name">
				</fieldset>

				<button type="submit" class="btn btn-success">Add</button>
				</form>
			</div>
		</div>
	</div>

	<div class="container col-md-5" id="showAssignStudentForm" style="display: none;"  >
    		<div class="card">
    			<div class="card-body">

    				<form action="/assignStudentToCourse" method="POSTt">

    					<h2>

    Assign student to course

    					</h2>

    				<fieldset class="form-group">


    					<label>Course Title</label>

    					<select name="courseId">
                            <c:forEach items="${listCourses}" var="courseId">
                                <option value="${courseId.id}">${courseId.title}</option>
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

    				<button type="submit" class="btn btn-success">Add</button>
    				</form>
    			</div>
    		</div>
    	</div>


<div class="container col-md-5" id="showAssignInstructorForm" style="display: none;" >
		<div class="card">
			<div class="card-body">


					<form action="/assignInstructorToCourse" method="POSTt">

				<caption>
					<h2>


assign Instructor To Course

					</h2>
				</caption>


				<fieldset class="form-group">
				<label>Course title</label>

                    					<select name="courseId">
                                            <c:forEach items="${listCourses}" var="courseId">
                                                <option value="${courseId.id}">${courseId.title}</option>
                                            </c:forEach>
                                        </select>
				</fieldset>

				<fieldset class="form-group">
					<label>Instructor Name</label>

					<select name="instructorId">
                                                                            <c:forEach items="${listInstructors}" var="instructorId">
                                                                                <option value="${instructorId.id}">${instructorId.name}</option>
                                                                            </c:forEach>
                                                                        </select>
				</fieldset>

				<button type="submit" class="btn btn-success">Add</button>
				</form>
			</div>
		</div>
	</div>


<div class="container col-md-5" id="showAdminsForm" style="display: none;" >
		<div class="card">
			<div class="card-body">

				<c:if test="${admin == null}">
					<form action="/addAdmin" method="POSTt">
				</c:if>

				<caption>
					<h2>

						<c:if test="${admin == null}">
            			Add new admin
            		</c:if>
					</h2>
				</caption>


				<fieldset class="form-group">
					<label>Admin Id</label> <input type="text"
						value="<c:out value='${admin.id}' />" class="form-control"
						name="id" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Admin name</label> <input type="text"
						value="<c:out value='${admin.name}' />" class="form-control"
						name="name">
				</fieldset>

				<button type="submit" class="btn btn-success">Add</button>
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