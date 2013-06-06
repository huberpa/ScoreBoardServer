package com.scoreboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
	private static Connection connection = null;

	// Hostname intern
	// private static String dbHost = "10.3.15.124";
	private static String dbHost = "localhost";
	// extern
	// private static String dbHost = "193.196.7.45";

	// Port -- Standard: 3306
	private static String dbPort = "3306";

	// Datenbankname
	private static String datenbankName = "ScoreBoard";

	// Datenbankuser
	private static String dbUser = "root";

	// Datenbankpasswort
	private static String dbPasswort = "patrick1!";

	/**
	 * Importiert den ODBC-Treiber und stellt eine Verbindung mit dem Server her
	 */
	private MySQLConnection() {
		try {
			/*
			 * Datenbanktreiber für ODBC Schnittstellen laden. Für verschiedene
			 * ODBC-Datenbanken muss dieser Treiber nur einmal geladen werden.
			 */
			Class.forName("com.mysql.jdbc.Driver");

			/*
			 * Verbindung zur ODBC-Datenbank 'portfolio' herstellen. Es wird die
			 * JDBC-ODBC-Brücke verwendet.
			 */
			connection = DriverManager.getConnection("jdbc:mysql://" + dbHost
					+ ":" + dbPort + "/" + datenbankName + "?user=" + dbUser
					+ "&password=" + dbPasswort);

		} catch (ClassNotFoundException e) {
			fehlerAusgeben("Fehler beim öffnen des SQL-Treibers: 'com.mysql.jdbc.Driver'\n"
					+ "Treiber nicht gefunden\n\n"
					+ "Bestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
		} catch (SQLException e) {
			fehlerAusgeben("Fehler bei der Verbindung mit der MySQL-Datenbank\n"
					+ "Bitte DB-Name, DB-Port, DB-User und DB-Passwort überprüfen\n\n"
					+ "Bestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
		}
	}

	/**
	 * Öffnet ein neues Fenster und gibt eine fehlermeldung aus
	 * 
	 * @param fehlerString
	 *            Fehlermeldung die angezeigt werden soll
	 */
	private static void fehlerAusgeben(String fehlerString) {
		System.out.println(fehlerString);
	}

	public static boolean loescheTabelle(String tabelle) {

		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("DELETE FROM `" + tabelle
								+ "` WHERE 1");
				pstmt.executeUpdate();
				return true;
			} catch (SQLException ex) {
				fehlerAusgeben("Fehler beim Löschen der Daten aus der Tabelle: '"
						+ tabelle
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static ResultSet getAllUsers() {

		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("SELECT `Name`, `Passwort` FROM `USER` WHERE 1");
				pstmt.executeQuery();

				// Ergebnistabelle erzeugen und abholen.
				return pstmt.executeQuery();

			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;

	}

	public static ResultSet getAllGroupsFromUser(String userName) {

		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("SELECT `Gruppenname` FROM `VERBINDUNG` WHERE `Name` = ?");
				pstmt.setString(1, userName);
				pstmt.executeQuery();

				// Ergebnistabelle erzeugen und abholen.
				return pstmt.executeQuery();

			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static ResultSet getGroupRanking(String groupName) {

		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("SELECT `Name`, `Punktzahl` FROM `VERBINDUNG` WHERE `Gruppenname` = ? ORDER BY `Punktzahl` DESC");
				pstmt.setString(1, groupName);
				pstmt.executeQuery();

				// Ergebnistabelle erzeugen und abholen.
				return pstmt.executeQuery();

			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static boolean checkUserLogin(String userName, String userPassword) {
		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("SELECT `Name` FROM `USER` WHERE `Name` = ? AND `Passwort` = ?");
				pstmt.setString(1, userName);
				pstmt.setString(2, userPassword);

				// try to find tupel with userName and userPassword
				ResultSet loginResult = pstmt.executeQuery();
				if (loginResult != null && loginResult.next()) {
					return true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static boolean checkGroupLogin(String groupName, String groupPassword) {
		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("SELECT `Gruppenname` FROM `GRUPPEN` WHERE `Gruppenname` = ? AND `Gruppenpasswort` = ?");

				pstmt.setString(1, groupName);
				pstmt.setString(2, groupPassword);

				ResultSet result = pstmt.executeQuery();
				if (result != null && result.next()) {
					return true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Kunden_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static boolean registerUser(String userName, String userPassword) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("INSERT INTO `USER`(`Name`, `Passwort`) VALUES (?, ?)");
				pstmt.setString(1, userName.toLowerCase());
				pstmt.setString(2, userPassword);

				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static boolean addUserToGroup(String userName, String groupName) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("INSERT INTO `VERBINDUNG`(`Name`, `Gruppenname`, `Punktzahl`, `Spieltag`) VALUES (?, ?, 0, 0)");
				pstmt.setString(1, userName.toLowerCase());
				pstmt.setString(2, groupName.toLowerCase());

				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.println("user is already in group");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	/**
	 * Prüft ob eine Verbindung zum MySQL-Server besteht und baut wenn nicht
	 * eine auf
	 * 
	 * @return Verbindungsinstanz zum Server
	 */
	private static Connection getConnectionInstance() {
		try {
			if (connection == null) {
				new MySQLConnection();
				// System.out.println("connection war null, jetzt valid: "
				// + connection.isValid(1000) + ", closed: "
				// + connection.isClosed());
			} else {
				// System.out.println("connection != null, valid: "
				// + connection.isValid(1000) + ", closed: "
				// + connection.isClosed());
				if (connection.isClosed() || !connection.isValid(1000)) {
					new MySQLConnection();
					// System.out.println("neue connection, valid: "
					// + connection.isValid(1000) + ", closed: "
					// + connection.isClosed());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static ResultSet getBundeligaErgebnisse(int spielTag) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("SELECT `Heim-Tore`, `Gast-Tore` FROM `Bundesligaspielplan` WHERE `Spieltag` = ?");
				pstmt.setInt(1, spielTag);

				return pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static boolean saveBundesligaPlan(String row) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("INSERT INTO `Bundesligaspielplan`(`Spieltag`, `Termin`, `Heim`, `Gast`) "
								+ "VALUES (?, ?, ?, ?)");
				String[] colums = row.split(";");
				pstmt.setInt(1, Integer.parseInt(colums[0]));
				pstmt.setString(2, colums[1]);
				pstmt.setString(3, colums[2]);
				pstmt.setString(4, colums[3]);
				// pstmt.setString(2, groupName.toLowerCase());

				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static boolean updateBundesligaErgebnisse(String row) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				String[] columns = row.split(";");
				if (columns.length >= 6) {
					int heimTore = Integer.parseInt(columns[4]);
					int gastTore = Integer.parseInt(columns[5]);
					String heimMannschaft = columns[2];
					String gastMannschaft = columns[3];

					PreparedStatement pstmt;
					pstmt = connection
							.prepareStatement("UPDATE `Bundesligaspielplan` SET `Heim-Tore` = ?,`Gast-Tore` = ? "
									+ "WHERE `Heim` = ? and `Gast` = ?");
					pstmt.setInt(1, heimTore);
					pstmt.setInt(2, gastTore);
					pstmt.setString(3, heimMannschaft);
					pstmt.setString(4, gastMannschaft);
					pstmt.executeUpdate();

					if (heimTore > gastTore) {

					} else if (heimTore < gastTore) {

					} else {

					}

					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static ResultSet getBundesligaTabelle() {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("SELECT `Team`, `Spieltag`, `Geschossene-Tore`, `Gegentore`, `Tordifferenz`, `Punkte` FROM `Bundesligatabelle` WHERE 1 "
								+ "ORDER BY `Punkte` desc, `Tordifferenz` desc, `Geschossene-Tore` desc,`Team` asc");

				return pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static void updateBundesligaTabelle() {

	}

	public static boolean saveBundesligaTabelle(String team, int spieltag,
			int siege, int unentschieden, int niederlagen, int geschosseneTore,
			int gegentore, int torDifferenz, int punkte) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("UPDATE `Bundesligatabelle` SET `Spieltag`=?,`Siege`=?,`Unentschieden`=?,`Niederlage`=?,"
								+ "`Geschossene-Tore`=?,`Gegentore`=?,`Tordifferenz`=?,`Punkte`=?"
								+ " WHERE `Team` = ?");
				pstmt.setInt(1, spieltag);
				pstmt.setInt(2, siege);
				pstmt.setInt(3, unentschieden);
				pstmt.setInt(4, niederlagen);
				pstmt.setInt(5, geschosseneTore);
				pstmt.setInt(6, gegentore);
				pstmt.setInt(7, torDifferenz);
				pstmt.setInt(8, punkte);
				pstmt.setString(9, team);

				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return false;
	}

	public static ResultSet getBundeligaSpieltag(int spielTag) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("SELECT `Spieltag`, `Heim`, `Gast`, `Heim-Tore`, `Gast-Tore` "
								+ "FROM `Bundesligaspielplan` WHERE `Spieltag` = ?");
				pstmt.setInt(1, spielTag);

				return pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static ResultSet getTipp(int spielTag, String gruppe, String user) {
		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("SELECT `Heimtipp`, `Gasttipp` "
								+ "FROM `TIPPS` WHERE `Spieltag` = ? AND `Gruppenname` = ? AND `Name` = ?");
				pstmt.setInt(1, spielTag);
				pstmt.setString(2, gruppe);
				pstmt.setString(3, user);

				return pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("fehler beim Spielplan erstellen");
				// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
				// + "kunden_id"
				// +
				// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}

	public static boolean setTipp(int spieltag, String gruppe, String user,
			String heim, String gast, int heimTipp, int gastTipp) {

		connection = getConnectionInstance();
		if (connection != null) {
			try {
				PreparedStatement pstmt;
				pstmt = connection
						.prepareStatement("INSERT INTO `TIPPS`(`Spieltag`, `Name`, `Gruppenname`, `Heim`, `Gast`, `Heimtipp`, `Gasttipp`) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, spieltag);
				pstmt.setString(2, user);
				pstmt.setString(3, gruppe);
				pstmt.setString(4, heim);
				pstmt.setString(5, gast);
				pstmt.setInt(6, heimTipp);
				pstmt.setInt(7, gastTipp);

				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				try {
					PreparedStatement pstmt;
					pstmt = connection
							.prepareStatement("UPDATE `TIPPS` SET `Heimtipp`=?,`Gasttipp`=? WHERE `Heim` = ? AND `Gast` = ?");
					pstmt.setInt(1, heimTipp);
					pstmt.setInt(2, gastTipp);
					pstmt.setString(3, heim);
					pstmt.setString(4, gast);

					pstmt.executeUpdate();
					return true;
				} catch (SQLException e2) {
					e2.printStackTrace();
					// System.out.println("fehler beim Spielplan erstellen");
					// fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
					// + "kunden_id"
					// +
					// "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
				}
			}
		}
		return false;
	}

	/**
	 * Leitet den übergebenen SQL-Anfrage-String an den Server weiter und gibt
	 * die Resultate wieder zurück
	 * 
	 * @param sqlAnfrage
	 *            SQL-String der vom Server verarbeitet werden soll
	 * @return Ergebnis-Daten die der Server zurückliefert
	 */
	public static ResultSet getData(String sqlAnfrage) {
		connection = getConnectionInstance();
		if (connection != null) {
			// Anfrage-Statement erzeugen.
			Statement anfrageStatement;
			try {
				anfrageStatement = connection.createStatement();

				// Ergebnistabelle erzeugen und abholen.
				return anfrageStatement.executeQuery(sqlAnfrage);

			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Abfragen der Daten vom Server beim Ausführen des Befehls: \n'"
						+ sqlAnfrage
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
		return null;
	}
}