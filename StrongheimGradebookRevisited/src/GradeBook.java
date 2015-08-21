import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class GradeBook
 */
@WebServlet("/GradeBook")
public class GradeBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Statement st;
	private static Connection con ;
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GradeBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/GradeBook.jsp").forward(
				request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String editWeights = request.getParameter("hiddenWeights");
			if(editWeights!=null){
			if(editWeights.equalsIgnoreCase("y")){
				updateWeights(request);
				getServletContext().getRequestDispatcher("/GPA.jsp").forward(
						request, response);
			}
		}else{
			processRequest(request, response);
			getServletContext().getRequestDispatcher("/GradeBook.jsp").forward(
					request, response);
			}
	}

	private void updateWeights(HttpServletRequest request){
		openConnection();
		int quiz= Integer.parseInt(request.getParameter("quiz"));
		int test= Integer.parseInt(request.getParameter("test"));
		int homework= Integer.parseInt(request.getParameter("homework"));
		int project= Integer.parseInt(request.getParameter("project"));
		String  classId = request.getParameter("classId");
		try {
			String sql= "Delete From Weights where classID = '" + classId + "'";
			System.out.println(sql);
			st.executeQuery(sql);
			sql="insert into weights values('"+classId +"', "+ quiz + ","+ test +"," + project + "," + homework + ")";
			System.out.println(sql);
			st.executeQuery(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	private void addToDatabase(String classId, int studentId, String assign, String aType,String sDate, int grade) {

		openConnection();
		try {
			String insertQuery = "insert into GRADEBOOK_REVISITED values ("+studentId + ",'" + assign + "','"
				+ aType+"','"+ classId +"', To_Date('"+sDate +"','mm/dd/yyyy'),"+ grade + ",0)";
			// print it for debug (appears in server log files)
			System.out.println("sql:" + insertQuery);
			// let's execute
			st.executeQuery(insertQuery);
			// that's all folks.
			con.close();
		} catch (SQLException  ex) {
			System.out.println(ex.getMessage());
		}
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> errorMessages = new ArrayList<String>();
		String assignment,aType="",sDate,classId;
		String tempStr;
		int studentId =0, grade = 0;
		

		classId = (String) request.getParameter("classId");
		if (classId == null) {
			errorMessages
					.add("Assignment parameter is missing (check input form!)");
		} else {
			classId = classId.trim();
			if (classId.length() == 0) {
				errorMessages.add("Assignment is empty...");
			}
		}
		assignment = (String) request.getParameter("assign");
		if (assignment == null) {
			errorMessages
					.add("Assignment parameter is missing (check input form!)");
		} else {
			assignment = assignment.trim();
			if (assignment.length() == 0) {
				errorMessages.add("Assignment is empty...");
			}
		}
		tempStr = (String) request.getParameter("type");
		if (tempStr == null) {
			errorMessages
					.add("Assignment type parameter is missing (check input form!)");
		}else if(tempStr.equalsIgnoreCase("quiz") || tempStr.equalsIgnoreCase("Homework")
				||tempStr.equalsIgnoreCase("Project") || tempStr.equalsIgnoreCase("test")) {
			aType=tempStr;
		}
		else {
			tempStr = tempStr.trim();
			if (tempStr.length() == 0) {
				errorMessages.add("Assignment type is empty...");
			}
		}
		sDate = (String) request.getParameter("sDate");
		if (sDate == null) {
			errorMessages
					.add("Submission Date parameter is missing (check input form!)");
		} else {
			sDate = sDate.trim();
			if (sDate.length() == 0) {
				errorMessages.add("Submission Date is empty...");
			}
		}

		tempStr = (String) request.getParameter("studentId");
		if (tempStr == null) {
			errorMessages.add("Student Id parameter is missing (check input form!)");
		} else {
			try {
				studentId = Integer.parseInt(tempStr);
			} catch (NumberFormatException ex) {
				errorMessages.add("Student Id must be a number");
			}
		}
		tempStr = (String) request.getParameter("grade");
		if (tempStr == null) {
			errorMessages.add("Grade parameter is missing (check input form!)");
		} else {
			try {
				grade = Integer.parseInt(tempStr);
			} catch (NumberFormatException ex) {
				errorMessages.add("Grade must be a number");
			}
		}
		// do we have any errors?
		if (errorMessages.size() != 0) {
			showErrorMessage(errorMessages, response);
		} else {
			addToDatabase(classId,studentId, assignment, aType, sDate,  grade);
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