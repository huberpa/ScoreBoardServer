package com.scoreboard;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// printAllUsers();
		// System.out.println(MySQLConnection.checkUserLogin("Kay",
		// "berkling"));
		// System.out.println(MySQLConnection.registerUser("Sebastiaasn",
		// "12"));

		// ExternalFileReader.readBundesligaGames();
		// ExternalFileReader.initializeBundeligaTabelle();
		// ExternalFileReader.readBundeligaErgebnisse();

		ServerSocket server = null;
		try {
			server = new ServerSocket(1991);
			new Thread(new AcceptThread(server)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printAllUsers() {
		ResultSet allUsersResult = com.scoreboard.MySQLConnection.getAllUsers();
		if (allUsersResult != null) {
			try {
				while (allUsersResult.next()) {
					System.out.println(allUsersResult.getString("Name") + ", "
							+ allUsersResult.getString("Passwort"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
