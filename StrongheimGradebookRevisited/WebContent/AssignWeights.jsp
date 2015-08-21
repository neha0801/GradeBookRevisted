<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Strongheim's Gradebook</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style>
body {
	font-family: "Bookman Old Style";
	color: black;
	background-color: #a6d2d2;
}
</style>
<%
String weights = request.getParameter("weights");
%>
</head>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
      <div class="navbar-header"style=font-family:Bookman>
      <a class="navbar-brand" style=color:red>Prof. Strongheim's Gradebook  </a>
    </div>
    <div>
      <ul class="nav navbar-nav" style=font-family:Bookman>
        <li ><a href="GradeBook.jsp"style=color:white><b>Home</b></a></li>
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Reports<span class="caret"></span></a>
          <ul class="dropdown-menu">
			<li><a href="Reports?reportType=A" style=color:black><b>All assignments by a student</b></a></li>
	        <li><a href="Reports?reportType=B" style=color:black><b>All assignments of a particular type by anyone</b></a></li>
	        <li><a href="Reports?reportType=C" style=color:black><b>All assignments of a particular type by a particular student</b></a></li>
	        <li><a href="Reports?reportType=D" style=color:black><b>The average for a student</b></a></li>
	        <li><a href="Reports?reportType=E" style=color:black><b>The average for a student by assignment type</b></a></li>
	        <li><a href="Reports?reportType=F" style=color:black><b>Highest and lowest grade for a particular assignment type </b></a></li>
          </ul>
        </li>
       <li ><a class = active href="GPA.jsp"style=color:white><b>GPA Calculator</b></a></li>
      </ul>
    </div>
  </div>
</nav>
<body>
	<div class="container">
	<h1>Assignment Weights</h1>
		<form action=GradeBook method="post">	
				<label style=font-size:20px>Class Number</label>
				<input type="text" name="classId"></input><br></br>	
				
				<label style=font-size:20px>Homework:</label><br></br>
				<input type="text" name="homework"></input>
				<br></br>
				<label style=font-size:20px>Quiz:</label><br></br>
				<input type="text" name="quiz"></input>
				<br></br>
				<label style=font-size:20px>Test:</label><br></br>
				<input type="text" name="test"></input>
				<br></br>
				<label style=font-size:20px>Project:</label><br></br>
				<input type="text" name="project"></input>
				<br></br>		
				<input type="hidden" name="hiddenWeights" value="<%=weights %>"></input>		
				<input class="btn pull-left btn-success btn-lg" type= "submit" value="Save"></input>				
		</form>
		<a href="AssignWeights.jsp" class="btn pull-left btn-warning btn-lg" role="button">Cancel</a>
		</div>
</body>
</html>