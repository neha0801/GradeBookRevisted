<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Strongheim's Reports</title>
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
	background-color: #a6d2d2;
}
h1{
	text-align:center;
	font-family:Edwardian Script ITC;
	font-size: 80px;
	text-color: blue
}
table{
	text-align:center;
	border-color:#00ffff;
	background-color: grey;
	border-collapse: seperate;
	color: blue
}
</style>
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
         <li ><a href="GPA.jsp"style=color:white><b>GPA Calculator</b></a></li>
      </ul>
    </div>
  </div>
</nav>
<body>
			<h1><b>Reports</b></h1>
			${input}
	${message}
</body>
</html>