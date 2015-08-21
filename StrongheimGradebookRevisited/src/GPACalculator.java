

import java.io.IOException;
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
 * Servlet implementation class GPACalculator
 */
@WebServlet("/GPACalculator")
public class GPACalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Statement st;
	private static Connection con ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPACalculator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// <a href="GPACalculator?GPA=Y" class="btn pull-left btn-primary btn-lg" role="button">Calculate GPA</a>
  
		String message = "";
		message= updateGPA(request);
		
		request.setAttribute("message", message);
	
		getServletContext().getRequestDispatcher("/GPA.jsp").forward(
				request, response);

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
	private String updateGPA(HttpServletRequest request){
		openConnection();
		int id= Integer.parseInt(request.getParameter("studentId"));
		String classId = request.getParameter("classId");
		String message="";
		double gpaValue=0.0;
		ResultSet rs;
		ArrayList<String> aType = new ArrayList<String>();
		ArrayList<Integer> score = new ArrayList<Integer>();
		String sql= "select type, grade from GRADEBOOK_REVISITED where STUDENTID = " + id + "and classID = '" + classId + "'";
		try {
			rs = st.executeQuery(sql);
			int weight;
			while(rs.next()){
				aType.add(rs.getString(1));
				score.add(rs.getInt(2));
			}
				//if (rs.getString(1).equalsIgnoreCase("quiz")){
			for(int i=0;i<aType.size();i++){	
				ResultSet rs1=null;
				sql= "select " + aType.get(i) + " from weights where classId = '" + classId + "'";
				System.out.println(sql);
				rs1=st.executeQuery(sql);
				if(rs1.next()){
					weight=rs1.getInt(1);
					gpaValue += (score.get(i) * weight)/100;
				}
			}
			sql="Update GRADEBOOK_REVISITED set gpa = " + gpaValue + "where  STUDENTID = " + id + "and classId ='" +classId+"'";
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message+="<table border=2 width = 20% background-color:Light grey>"; 
		message+="<tr><th> Calculated GPA </th></tr>";
		message+="<tr><td>"+ gpaValue+ "</td></tr>";
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

}
