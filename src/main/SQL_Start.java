package main;

import java.util.ArrayList;
import java.util.Scanner;

import beansAreBack.movieBean;
import database.SQLconnection;

public class SQL_Start {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please type the name of a movie or the name of an actor or actress");
		String movie = scanner.nextLine();
		
		ArrayList <movieBean> movies = new ArrayList<movieBean>();
		
		if (SQLconnection.connectSQL()) {
			// System.out.println("database is ready");
			movies = SQLconnection.stateSQL(movie, movie, movies);
		}
		
		for (int i = 0; i < movies.size(); i++) {
			System.out.print(movies.get(i).getMovieName());
			System.out.print(" - ");
			System.out.print(movies.get(i).getMovieYear());
			System.out.print(" - ");
			System.out.print(movies.get(i).getMovieDirector());
			System.out.print(" - ");
			System.out.print(movies.get(i).getMovieStars());
			System.out.print(" - ");
			System.out.print(movies.get(i).getMovieAwards());
			System.out.println("");
		}
		scanner.close();
	}
}
	