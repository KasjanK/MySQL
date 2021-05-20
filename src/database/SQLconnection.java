package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beansAreBack.movieBean;

public class SQLconnection {
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;	
	
	public static boolean connectSQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			System.out.println("Exception driver " + ex.getMessage());
			return false;
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", DatabaseLogin.getuName(), DatabaseLogin.getuPass());
			return true;
			
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}		
	}
	public static ArrayList<movieBean> stateSQL(String movieName, String movieStar, ArrayList<movieBean> movies) {

		String requestQuery = "SELECT `namn`, `år`, `regissör`, `skådespelare`, `priser` FROM `movie` WHERE `namn` LIKE ? UNION SELECT `namn`, `år`, `regissör`, `skådespelare`, `priser` FROM `movie` WHERE `skådespelare` LIKE ?";
		
		
		try {
			stmt = conn.prepareStatement(requestQuery);
			
			stmt.setString(1, "%" + movieName + "%");
			stmt.setString(2, "%" + movieStar + "%");
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				movieBean movie = new movieBean();
				
				movie.setMovieName(rs.getString("namn"));
				movie.setMovieYear(rs.getString("år"));
				movie.setMovieDirector(rs.getString("regissör"));
				movie.setMovieStars(rs.getString("skådespelare"));
				movie.setMovieAwards(rs.getString("priser"));
				
				movies.add(movie);
			}
			rs.close();
			
			conn.endRequest();
			conn.close();
			
			return movies;
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}
}
