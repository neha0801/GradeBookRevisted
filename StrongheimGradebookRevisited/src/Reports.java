import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Reports
 */
@WebServlet("/Reports")
public class Reports extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Statement st;
	private static Connection con ;
	private static ArrayList<String> errorMessages = new ArrayList<String>();
	private static int studentId=0;
	private static String aType="";
	private static String reportType="";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reports() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		reportType=request.getParameter("reportType");
		String input= createForm(reportType);
		
		request.setAttribute("input", input);
	
		getServletContext().getRequestDispatcher("/Reports.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private String createForm(String reportType){
		String input ="";
		if (reportType.equalsIgnoreCase("A") || reportType.equalsIgnoreCase("D") || reportType.equalsIgnoreCase("E")){
			input= "<form action=Reports method='post'><label style='font-size: 20px'>Student Id:</label> <input type='text' name='studentId'></input><br></br>"+
					"<label style=font-size:20px>Class Number</label><input type='text' name='classId'></input><br></br>" +
					"<input type='submit' value='Get Reports'></input></form>";
		}else if (reportType.equalsIgnoreCase("C")){
			input= "<form action=Reports method='post'><label style='font-size: 20px'>Student Id:</label> <input type='text' name='studentId'></input><br></br>"+
					"<label	style='font-size: 20px'>Assignment Type</label><input type='text' name='assignType'></input><br></br>"+
					"<label style=font-size:20px>Class Number</label><input type='text' name='classId'></input><br></br>"+
					"<input type='submit' value='Get Reports'></input></form>";
		} else if (reportType.equalsIgnoreCase("B") || reportType.equalsIgnoreCase("F")){
			input= "<form action=Reports method='post'><label	style='font-size: 20px'>Assignment Type</label><input type='text' name='assignType'></input><br></br>"+
					"<label style=font-size:20px>Class Number</label><input type='text' name='classId'></input><br></br>"+
					"<input type='submit' value='Get Reports'></input></form>";
		}
		return input;
	}
	private void openConnection(){
		String url = "jdbc:oracle:thin:testuser/password@localhost";
		Properties props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, props);
			st = con.createStatement();
		} catch (SQLException|ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connection established successfully...!!");
	
	}
	

	private String getReports(String classId, int id, String type, String reportType){
		openConnection();
		String sql="",message="";
		ResultSet rs=null;
		switch (reportType){
			case "A":	
				sql ="Select assignment from GRADEBOOK_REVISITED where classId = '"+ classId + "' and STUDENTID =" +id;
				message+= "<b>Report: All assignments of class " + classId + " for student " + id +"<b>";
				message += "<table border=2 width = 20% background-color:Light grey>"; 
				break;
			case "B": 
				sql ="Select assignment from GRADEBOOK_REVISITED where classId = '"+ classId + "' and TYPE = '" +type +"'";
				message+= "<b>Report: All assignments of class " + classId + " for Assignment type  " + type +"<b>";
				message += "<table border=2 width = 20% background-color:Light grey>"; 
				break;
			case "C":
				sql ="Select assignment from GRADEBOOK_REVISITED where classId = '"+ classId + "' and TYPE = '" +type +"' and studentId = " + id;
				message+= "<b>Report: All assignments  of class " + classId + " for student " + id +" of assignment type " + type +"<b>";
				message += "<table border=2 width = 20% background-color:Light grey>"; 
				break;
			case "D":
				sql ="Select ROUND(avg(grade),3) from GRADEBOOK_REVISITED where classId = '"+ classId + "' and  STUDENTID =" +id;
				message+= "<b>Report: Average Grade of class " + classId + "for student " + id +"<b>";
				message += "<table border=2 width = 20% background-color:Light grey>"; 
				break;
			case "E":
				sql ="Select type,ROUND(avg(grade),3) from GRADEBOOK_REVISITED  where classId = '"+ classId + "' and studentId = " + id + " group by type";
				message+= "<b>Report: Average Grade  of class " + classId + " for student " + id +" according to type of assignment<b>";
				message += "<table border=2 width = 30% background-color:Light grey>"; 
				break;
			case "F":
				sql="select max(grade), min(grade) from GRADEBOOK_REVISITED where classId = '"+ classId + "' and type= '" + type +"'" ;
				message+= "<b>Report: Highest and Lowest Score  of class " + classId + " for assignment " + type +"<b>";
				message += "<table border=2 width = 20% background-color:Light grey>"; 
				break;
		}
		System.out.println(sql);
		try{
			rs = st.executeQuery(sql);
			message+="<br></br>";	
			if(reportType.equalsIgnoreCase("F")){
						
				if (rs.next()) {
					message += "<tr><th><b>HIGHEST SCORE</b></th><th><b>LOWEST SCORE</b></th></tr>";
					message += ("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td></tr>");
				}
			}else if (reportType.equalsIgnoreCase("E")){
				message += "<tr><th><b>ASSIGNMENT TYPE</b></th><th><b>AVERAGE GRADE</b></th></tr>";
				while (rs.next()) {					
					message += ("<tr><td>" + rs.getString(1)  + "</td><td>" + rs.getString(2) +"</td></tr>");
				}
			}
			else if (reportType.equalsIgnoreCase("D")){
				if (rs.next()) {
					message += "<tr><th><b>AVERAGE GRADE</b></th></tr>";
					message += ("<tr><td>" + rs.getString(1)  + "</td></tr>");
				}
			}
			else{		
				message += "<tr><th><b>ASSIGNMENT NAME</b></th><tr>";
				while (rs.next()) {
					message += ("<tr><td>" + rs.getString(1) + "</td></tr>");
				}
			}
			// that's all folks...
			con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return message;
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String message="";
		
		
		String tempStr="";
		String classId;
		
		classId = request.getParameter("classId");
		if(classId.equalsIgnoreCase("")){
			errorMessages.add("Class ID parameter is empty!");
		}
		if (reportType.equalsIgnoreCase("A") || reportType.equalsIgnoreCase("D") || reportType.equalsIgnoreCase("E")){
			tempStr = request.getParameter("studentId");
			try {
				studentId = Integer.parseInt(tempStr);
			} catch (NumberFormatException ex) {
				errorMessages.add("Student Id must be a number");
			}
		}else if (reportType.equalsIgnoreCase("C")){
			tempStr = request.getParameter("studentId");
			try {
				studentId = Integer.parseInt(tempStr);
			} catch (NumberFormatException ex) {
				errorMessages.add("Student Id must be a number");
			}
			tempStr = request.getParameter("assignType");
			if(tempStr.equalsIgnoreCase("")){
				errorMessages.add("Assignment Type parameter is empty!");
			}else if (tempStr.equalsIgnoreCase("quiz") || tempStr.equalsIgnoreCase("Homework")
					||tempStr.equalsIgnoreCase("Project") || tempStr.equalsIgnoreCase("test")) {
				aType=tempStr;		
			}else {
				errorMessages.add("Assignment Type parameter is incorrect!");
			}
			
		} else if (reportType.equalsIgnoreCase("B") || reportType.equalsIgnoreCase("F")){
			tempStr = request.getParameter("assignType");
			if(tempStr.equalsIgnoreCase("")){
				errorMessages.add("Assignment Type parameter is empty!");
			}else if (tempStr.equalsIgnoreCase("quiz") || tempStr.equalsIgnoreCase("Homework")
					||tempStr.equalsIgnoreCase("Project") || tempStr.equalsIgnoreCase("test")) {
				aType=tempStr;		
			}else {
				errorMessages.add("Assignment Type parameter is incorrect!");
			}
		}

		if (errorMessages.size() != 0) {
			showErrorMessage(errorMessages, response);
		} else {
		
			message+=getReports(classId,studentId, aType, reportType);			
			message +="<div class='col-sm-offset-2 col-sm-10'><p><a href='GradeBook.jsp' class='btn btn-primary btn-lg' role='button'>Go to Home Page</a></p></div>";
			request.setAttribute("message", message);
			getServletContext().getRequestDispatcher("/Reports.jsp").forward(
					request, response);
		}
	}

	private void showErrorMessage(ArrayList<String> errMsgList,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("Your request is very important for us, but:<br>");
		for (int i = 0; i < errMsgList.size(); i++) {
			out.println("<li>" + errMsgList.get(i));
		}
		out.println("<br><br>");
		out.println("<a href='GradeBook.jsp'>go back and correct your input please...</a>");
		out.println("</body></html>");
		out.close();

	}
	

}