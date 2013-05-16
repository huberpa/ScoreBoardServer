package com.scoreboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
	private static Connection verbindung = null;

	// Hostname
	private static String dbHost = "127.0.0.1";

	// Port -- Standard: 3306
	private static String dbPort = "3306";

	// Datenbankname
	private static String datenbankName = "portfolio";

	// Datenbankuser
	private static String dbUser = "root";

	// Datenbankpasswort
	private static String dbPasswort = "";

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
			verbindung = DriverManager.getConnection("jdbc:mysql://" + dbHost
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

		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
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

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Anlagen_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_AnlagenID(String[] arguments) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO anlagen_id (Anlagen_ID, Anlagenname, Zertifikat, Dokumentation, Bonität) "
								+ "VALUES (?, ?, ?, ?, ?)");
				pstmt.setInt(1, Integer.parseInt(arguments[0]));
				pstmt.setString(2, arguments[1]);
				if (arguments[2].equals("1")) {
					pstmt.setBoolean(3, true);
				} else {
					pstmt.setBoolean(3, false);
				}
				pstmt.setString(4, arguments[3]);
				pstmt.setString(5, arguments[4]);
				pstmt.executeUpdate();
			} catch (SQLException ex) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "anlagen_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Anlagen-Jahr_ID"
	 * ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_AnlagenJahrID(String[] arguments) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `anlagen-jahr_id`"
								+ "(`Anlagen_Jahr_ID`, `Anlagen_ID`, `Jahr`, `EEG 2009`, `Grundvergütung (09)`, `NaWaRo-Bonus (09)`, `Tech-Bonus`, "
								+ "`Formaldehyd-Bonus`, `biogene Reststoffe (09)`, `EEG 2012`, `Grundvergütung (12)`, `Biogene Reststoffe (12)`, "
								+ "`min Klasse 2`, `max Klasse 2`, `Aufbereitungsbonus`, `TankstellenGas`, `THG-Emissionswert`, `Anbauland`, `B2C-Gas`) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				pstmt.setInt(1, Integer.parseInt(arguments[0]));
				pstmt.setInt(2, Integer.parseInt(arguments[1]));
				pstmt.setInt(3, Integer.parseInt(arguments[2]));
				if (arguments[3].equals("1")) {
					pstmt.setBoolean(4, true);
				} else {
					pstmt.setBoolean(4, false);
				}
				if (arguments[4].equals("1")) {
					pstmt.setBoolean(5, true);
				} else {
					pstmt.setBoolean(5, false);
				}
				if (arguments[5].equals("1")) {
					pstmt.setBoolean(6, true);
				} else {
					pstmt.setBoolean(6, false);
				}
				if (arguments[6].length() > 0) {
					pstmt.setInt(7, Integer.parseInt(arguments[6]));
				} else {
					pstmt.setInt(7, -1);
				}
				if (arguments[7].equals("1")) {
					pstmt.setBoolean(8, true);
				} else {
					pstmt.setBoolean(8, false);
				}
				if (arguments[8].equals("1")) {
					pstmt.setBoolean(9, true);
				} else {
					pstmt.setBoolean(9, false);
				}
				if (arguments[9].equals("1")) {
					pstmt.setBoolean(10, true);
				} else {
					pstmt.setBoolean(10, false);
				}
				if (arguments[10].equals("1")) {
					pstmt.setBoolean(11, true);
				} else {
					pstmt.setBoolean(11, false);
				}
				if (arguments[11].equals("1")) {
					pstmt.setBoolean(12, true);
				} else {
					pstmt.setBoolean(12, false);
				}
				if (arguments[12].length() > 0) {
					pstmt.setInt(13, Integer.parseInt(arguments[12]));
				} else {
					pstmt.setInt(13, -1);
				}
				if (arguments[13].length() > 0) {
					pstmt.setInt(14, Integer.parseInt(arguments[13]));
				} else {
					pstmt.setInt(14, -1);
				}
				if (arguments[14].length() > 0) {
					pstmt.setInt(15, Integer.parseInt(arguments[14]));
				} else {
					pstmt.setInt(15, -1);
				}
				if (arguments[15].equals("1")) {
					pstmt.setBoolean(16, true);
				} else {
					pstmt.setBoolean(16, false);
				}
				pstmt.setDouble(17, Double.valueOf(arguments[16]));
				pstmt.setString(18, arguments[17]);
				if (arguments[18].equals("1")) {
					pstmt.setBoolean(19, true);
				} else {
					pstmt.setBoolean(19, false);
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				try {
					PreparedStatement pstmt = verbindung
							.prepareStatement("UPDATE `anlagen-jahr_id` SET "
									+ "`Anlagen_Jahr_ID` = ?, `Anlagen_ID` = ?, `Jahr` = ?, `EEG 2009` = ?, "
									+ "`Grundvergütung (09)` = ?, `NaWaRo-Bonus (09)` = ?, `Tech-Bonus` = ?, "
									+ "`Formaldehyd-Bonus` = ?, `biogene Reststoffe (09)` = ?, `EEG 2012` = ?, "
									+ "`Grundvergütung (12)` = ?, `Biogene Reststoffe (12)` = ?, "
									+ "`min Klasse 2` = ?, `max Klasse 2` = ?, `Aufbereitungsbonus` = ?, "
									+ "`TankstellenGas` = ?, `THG-Emissionswert` = ?, `Anbauland` = ?, `B2C-Gas` = ? "
									+ "WHERE `Anlagen_Jahr_ID` = ?");

					pstmt.setInt(1, Integer.parseInt(arguments[0]));
					pstmt.setInt(2, Integer.parseInt(arguments[1]));
					pstmt.setInt(3, Integer.parseInt(arguments[2]));
					if (arguments[3].equals("1")) {
						pstmt.setBoolean(4, true);
					} else {
						pstmt.setBoolean(4, false);
					}
					if (arguments[4].equals("1")) {
						pstmt.setBoolean(5, true);
					} else {
						pstmt.setBoolean(5, false);
					}
					if (arguments[5].equals("1")) {
						pstmt.setBoolean(6, true);
					} else {
						pstmt.setBoolean(6, false);
					}
					if (arguments[6].length() > 0) {
						pstmt.setInt(7, Integer.parseInt(arguments[6]));
					} else {
						pstmt.setInt(7, -1);
					}
					if (arguments[7].equals("1")) {
						pstmt.setBoolean(8, true);
					} else {
						pstmt.setBoolean(8, false);
					}
					if (arguments[8].equals("1")) {
						pstmt.setBoolean(9, true);
					} else {
						pstmt.setBoolean(9, false);
					}
					if (arguments[9].equals("1")) {
						pstmt.setBoolean(10, true);
					} else {
						pstmt.setBoolean(10, false);
					}
					if (arguments[10].equals("1")) {
						pstmt.setBoolean(11, true);
					} else {
						pstmt.setBoolean(11, false);
					}
					if (arguments[11].equals("1")) {
						pstmt.setBoolean(12, true);
					} else {
						pstmt.setBoolean(12, false);
					}
					if (arguments[12].length() > 0) {
						pstmt.setInt(13, Integer.parseInt(arguments[12]));
					} else {
						pstmt.setInt(13, -1);
					}
					if (arguments[13].length() > 0) {
						pstmt.setInt(14, Integer.parseInt(arguments[13]));
					} else {
						pstmt.setInt(14, -1);
					}
					if (arguments[14].length() > 0) {
						pstmt.setInt(15, Integer.parseInt(arguments[14]));
					} else {
						pstmt.setInt(15, -1);
					}
					if (arguments[15].equals("1")) {
						pstmt.setBoolean(16, true);
					} else {
						pstmt.setBoolean(16, false);
					}
					pstmt.setDouble(17, Double.valueOf(arguments[16]));
					pstmt.setString(18, arguments[17]);
					if (arguments[18].equals("1")) {
						pstmt.setBoolean(19, true);
					} else {
						pstmt.setBoolean(19, false);
					}
					pstmt.setInt(20, Integer.parseInt(arguments[0]));

					pstmt.executeUpdate();
				} catch (SQLException sqle) {
					fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
							+ "anlagen-jahr_id"
							+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
				}
			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle
	 * "Anlagen_Monatsmengen_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_AnlagenMonatsmengenID(String[] arguments) {

		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `anlagen_monatsmengen_id`(`Monat_ID`, `Anlagen_Jahr_ID`, `Monat`, "
								+ "`Ist-Gasmenge (Anlage)`, `Soll-Gasmenge (Anlage)`, `THG-Emissionswert`, `Anlagen_Jahr`, "
								+ "`Jahr_Monat`, `Anlagen_ID`) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

				pstmt.setInt(1, Integer.parseInt(arguments[0]));
				pstmt.setInt(2, Integer.parseInt(arguments[1]));
				pstmt.setString(3, arguments[2]);
				pstmt.setDouble(4, Double.valueOf(arguments[3]));
				pstmt.setDouble(5, Double.valueOf(arguments[4]));
				if (arguments[5] != null) {
					pstmt.setDouble(6, Double.valueOf(arguments[5]));
				} else {
					pstmt.setNull(6, java.sql.Types.DOUBLE);
				}
				pstmt.setInt(7, Integer.parseInt(arguments[6]));
				pstmt.setInt(8, Integer.parseInt(arguments[7]));
				pstmt.setInt(9, Integer.parseInt(arguments[8]));

				pstmt.executeUpdate();
			} catch (SQLException e) {
				try {
					PreparedStatement pstmt = verbindung
							.prepareStatement("UPDATE `anlagen_monatsmengen_id` SET `Soll-Gasmenge (Anlage)` = ?"
									+ " WHERE `Monat_ID` = ?");
					pstmt.setDouble(1, Double.valueOf(arguments[4]));
					pstmt.setInt(2, Integer.parseInt(arguments[0]));

					pstmt.executeUpdate();
				} catch (SQLException sqle) {
					fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
							+ "anlagen_monatsmengen_id"
							+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
				}

			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Kundenberater_ID"
	 * ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_KundenberaterID(String[] arguments) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `kundenberater_id`(`Kundenberater_ID`, `Kundenberater`, `Straße`, `PLZ`, `Ort`, `Telefonnummer`, `Handynummer`, `E-Mail`) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				int laenge = arguments.length;

				pstmt.setInt(1, Integer.parseInt(arguments[0]));
				pstmt.setString(2, arguments[1]);

				for (int i = 2; i < laenge; i++) {
					pstmt.setString(i + 1, arguments[i]);
				}
				while (laenge < 8) {
					pstmt.setString(laenge + 1, "");
					laenge++;
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "kundenberater_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Kunden_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_KundenID(String[] arguments) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt;
				int i = 0;
				if (arguments[0].equals("-1")) {
					pstmt = verbindung
							.prepareStatement("INSERT INTO `kunden_id`(`Name, Vorname`, `Stadtwerke`, `Biogasregister`, `Kundenanschrift`, `Anlagenanschrift`, `Kundenberater_ID`) "
									+ "VALUES (?, ?, ?, ?, ?, ?)");
				} else {
					pstmt = verbindung
							.prepareStatement("INSERT INTO `kunden_id`(`Kunden_ID`, `Name, Vorname`, `Stadtwerke`, `Biogasregister`, `Kundenanschrift`, `Anlagenanschrift`, `Kundenberater_ID`) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
					pstmt.setInt(1, Integer.parseInt(arguments[0]));
					i++;
				}

				pstmt.setString(i + 1, arguments[1]);
				i++;

				if (arguments[2].equals("1")) {
					pstmt.setBoolean(i + 1, true);
				} else {
					pstmt.setBoolean(i + 1, false);
				}
				i++;

				if (arguments[3].equals("1")) {
					pstmt.setBoolean(i + 1, true);
				} else {
					pstmt.setBoolean(i + 1, false);
				}
				i++;

				pstmt.setString(i + 1, arguments[4]);
				i++;

				pstmt.setString(i + 1, arguments[5]);
				i++;

				if (arguments[6].equals("")) {
					pstmt.setNull(i + 1, java.sql.Types.INTEGER);
				} else {
					pstmt.setInt(i + 1, Integer.parseInt(arguments[6]));
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "kunden_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle
	 * "Kunden_Monatsmengen_Vertrag_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_KundenMonatsmengenVertragID(String[] arguments) {

		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `kunden_monatsmengen_vertrag_id`(`Monat_Vetrag_ID`, `Vetrags-Jahr_ID`, `Monat_ID`, `Monat`, `Ist-Gasmenge`, `Soll-Gasmenge`, `THG-Emissionswert`, `CNG-Abgabemenge (t)`, `Jahr`, `Jahr_Monat`, `Anlagen_ID`) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				pstmt.setLong(1, Long.valueOf(arguments[0]));
				pstmt.setLong(2, Long.valueOf(arguments[1]));
				pstmt.setInt(3, Integer.parseInt(arguments[2]));
				pstmt.setString(4, arguments[3]);
				pstmt.setDouble(5, Double.valueOf(arguments[4]));
				pstmt.setDouble(6, Double.valueOf(arguments[5]));
				if (arguments[6] != null && arguments[6].length() > 0) {
					pstmt.setDouble(7, Double.valueOf(arguments[6]));
				} else {
					pstmt.setNull(7, java.sql.Types.DOUBLE);
				}
				if (arguments[7] != null) {
					pstmt.setDouble(8, Double.valueOf(arguments[7]));
				} else {
					pstmt.setNull(8, java.sql.Types.DOUBLE);
				}
				pstmt.setString(9, arguments[8]);
				pstmt.setInt(10, Integer.parseInt(arguments[9]));
				pstmt.setInt(11, Integer.parseInt(arguments[10]));

				pstmt.executeUpdate();
			} catch (SQLException e) {
				try {
					PreparedStatement pstmt = verbindung
							.prepareStatement("UPDATE `kunden_monatsmengen_vertrag_id` "
									+ "SET `Ist-Gasmenge` = ?,`Soll-Gasmenge` = ? "
									+ "WHERE `Monat_Vetrag_ID` = "
									+ arguments[0]);

					pstmt.setDouble(1, Double.valueOf(arguments[4]));
					pstmt.setDouble(2, Double.valueOf(arguments[5]));

					pstmt.executeUpdate();
				} catch (SQLException sqle) {
					fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
							+ "kunden_monatsmengen_vertrag_id"
							+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
				}
			}
		}

	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle "Kunden_Vertrag_ID"
	 * ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_KundenVertragID(String[] arguments) {

		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `kunden_vertrag_id`(`Vertrag_ID`, `Zählpunkt/Bilanzkreis_ID`, `Vertragseigenschaften`, `Vertrag`, `Tankstelle`, `B2C`) "
								+ "VALUES (?, ?, ?, ?, ?, ?)");

				pstmt.setInt(1, Integer.parseInt(arguments[0]));
				pstmt.setInt(2, Integer.parseInt(arguments[1]));
				if (arguments.length > 2) {
					pstmt.setString(3, arguments[2]);
				} else {
					pstmt.setString(3, "");
				}
				if (arguments.length > 3) {
					pstmt.setString(4, arguments[3]);
				} else {
					pstmt.setString(4, "");
				}
				if (arguments[4].equals("0")) {
					pstmt.setBoolean(5, false);
				} else {
					pstmt.setBoolean(5, true);
				}
				if (arguments[5].equals("0")) {
					pstmt.setBoolean(6, false);
				} else {
					pstmt.setBoolean(6, true);
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "kunden_vertrag_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}

	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle
	 * "Kunden_Vertrags-Jahr_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_KundenVertragsJahrID(String[] arguments) {

		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {
				PreparedStatement pstmt = verbindung
						.prepareStatement("INSERT INTO `kunden_vertrags-jahr_id`(`Vertrags-Jahr_ID`, `Vertrag_ID`, `Jahr`, `Min-Vertragsmenge`, `Max-Vertragsmenge`) "
								+ "VALUES (?, ?, ?, ?, ?)");

				pstmt.setLong(1, Long.valueOf(arguments[0]));
				pstmt.setInt(2, Integer.parseInt(arguments[1]));
				pstmt.setInt(3, Integer.parseInt(arguments[2]));
				if (arguments.length > 3) {
					if (arguments[3].equals("")) {
						pstmt.setNull(4, java.sql.Types.INTEGER);
					} else {
						pstmt.setInt(4, Integer.parseInt(arguments[3]));
					}
					if (arguments.length > 4) {
						if (arguments[4].equals("")) {
							pstmt.setNull(5, java.sql.Types.INTEGER);
						} else {
							pstmt.setInt(5, Integer.parseInt(arguments[4]));
						}
					} else {
						pstmt.setNull(5, java.sql.Types.INTEGER);
					}
				} else {
					pstmt.setNull(4, java.sql.Types.INTEGER);
					pstmt.setNull(5, java.sql.Types.INTEGER);
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "kunden_vertrags-jahr_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
	}

	/**
	 * Fügt eine neue Zeile in der Datenbank in die Tabelle
	 * "Zählpunkt_Bilanzkreis_ID" ein
	 * 
	 * @param arguments
	 *            Daten die gespeichert werden sollen
	 */
	public static void insertIn_ZaehlpunktBilanzkreisID(String[] arguments) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			try {

				PreparedStatement pstmt;
				int i = 1;
				if (arguments[0].equals("-1")) {
					pstmt = verbindung
							.prepareStatement("INSERT INTO `zählpunkt-bilanzkreis_id` "
									+ "(`Zählpunkt/Bilanzkreis_ID`, `Kunden_ID`, `Vertragsnummer`) "
									+ "VALUES (?, ?, ?)");
				} else {
					pstmt = verbindung
							.prepareStatement("INSERT INTO `zählpunkt-bilanzkreis_id` "
									+ "(`ZB_ID`, `Zählpunkt/Bilanzkreis_ID`, `Kunden_ID`, `Vertragsnummer`) "
									+ "VALUES (?, ?, ?, ?)");
					pstmt.setInt(i, Integer.parseInt(arguments[0]));
					i++;
				}

				pstmt.setString(i, arguments[1]);
				i++;

				pstmt.setInt(i, Integer.parseInt(arguments[2]));
				i++;

				if (arguments.length > 3) {
					pstmt.setString(i, arguments[3]);
				} else {
					pstmt.setString(i, "");
				}

				pstmt.executeUpdate();
			} catch (SQLException e) {
				fehlerAusgeben("Fehler beim Einfügen der Daten in die Tabelle: '"
						+ "zählpunkt-bilanzkreis_id"
						+ "'\n\nBestehen weiterhin Probleme, kontaktieren Sie bitte den Administrator Sebastian Kühne");
			}
		}
	}

	/**
	 * Prüft ob eine Verbindung zum MySQL-Server besteht und baut wenn nicht
	 * eine auf
	 * 
	 * @return Verbindungsinstanz zum Server
	 */
	private static Connection gibVerbindungsInstanz() {
		if (verbindung == null)
			new MySQLConnection();
		return verbindung;
	}

	/**
	 * Leitet den übergebenen SQL-Anfrage-String an den Server weiter und gibt
	 * die Resultate wieder zurück
	 * 
	 * @param sqlAnfrage
	 *            SQL-String der vom Server verarbeitet werden soll
	 * @return Ergebnis-Daten die der Server zurückliefert
	 */
	public static ResultSet gibDaten(String sqlAnfrage) {
		verbindung = gibVerbindungsInstanz();
		if (verbindung != null) {
			// Anfrage-Statement erzeugen.
			Statement anfrageStatement;
			try {
				anfrageStatement = verbindung.createStatement();

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