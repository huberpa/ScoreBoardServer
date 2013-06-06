package com.scoreboard;

public class BundesligaTabelle {

	private String[] mannschaften;

	// Spieldaten: ST, S, U, N, GeschT, GegT, Diff, Punkte
	private int[][] spielDaten;

	public BundesligaTabelle() {
		mannschaften = new String[18];
		mannschaften[0] = "B04";
		mannschaften[1] = "BMG";
		mannschaften[2] = "BVB";
		mannschaften[3] = "FCA";
		mannschaften[4] = "FCB";
		mannschaften[5] = "FCN";
		mannschaften[6] = "FDD";
		mannschaften[7] = "FRA";
		mannschaften[8] = "H96";
		mannschaften[9] = "HSV";
		mannschaften[10] = "M05";
		mannschaften[11] = "S04";
		mannschaften[12] = "SCF";
		mannschaften[13] = "SGF";
		mannschaften[14] = "SVW";
		mannschaften[15] = "TSG";
		mannschaften[16] = "VFB";
		mannschaften[17] = "WOB";

		spielDaten = new int[8][18];
		for (int zeile = 0; zeile < 18; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				spielDaten[spalte][zeile] = 0;
			}
		}
	}

	public boolean aktualisiereTabelle(String begegnungsDaten) {
		String[] columns = begegnungsDaten.split(";");
		if (columns.length >= 6) {
			int spieltag = Integer.parseInt(columns[0]);
			int heimTore = Integer.parseInt(columns[4]);
			int gastTore = Integer.parseInt(columns[5]);
			String heimMannschaft = columns[2];
			String gastMannschaft = columns[3];

			// Array-Index der Heim-Mannschaft bestimmen
			int heimIndex = -1;
			for (int i = 0; i < 18; i++) {
				if (mannschaften[i].equals(heimMannschaft)) {
					heimIndex = i;
					break;
				}
			}

			// Array-Index der Gast-Mannschaft bestimmen
			int gastIndex = -1;
			for (int i = 0; i < 18; i++) {
				if (mannschaften[i].equals(gastMannschaft)) {
					gastIndex = i;
					break;
				}
			}

			// Spieldaten: ST, S, U, N, GeschT, GegT, Diff, Punkte
			spielDaten[0][heimIndex] = spieltag;
			spielDaten[4][heimIndex] = spielDaten[4][heimIndex] + heimTore;
			spielDaten[5][heimIndex] = spielDaten[5][heimIndex] + gastTore;
			spielDaten[6][heimIndex] = spielDaten[4][heimIndex]
					- spielDaten[5][heimIndex];

			spielDaten[0][gastIndex] = spieltag;
			spielDaten[4][gastIndex] = spielDaten[4][gastIndex] + gastTore;
			spielDaten[5][gastIndex] = spielDaten[5][gastIndex] + heimTore;
			spielDaten[6][gastIndex] = spielDaten[4][gastIndex]
					- spielDaten[5][gastIndex];

			// Heimmannschaft hat gewonnen
			if (heimTore > gastTore) {
				// Tabelleneinträge der Heimmanschaft aktualisieren
				spielDaten[1][heimIndex]++;
				spielDaten[7][heimIndex] = spielDaten[7][heimIndex] + 3;

				// Tabelleneinträge der Gastmanschaft aktualisieren
				spielDaten[3][gastIndex]++;
			}

			// Gastmannschaft hat gewonnen
			else if (heimTore < gastTore) {
				// Tabelleneinträge der Heimmanschaft aktualisieren
				spielDaten[3][heimIndex]++;

				// Tabelleneinträge der Gastmanschaft aktualisieren
				spielDaten[1][gastIndex]++;
				spielDaten[7][gastIndex] = spielDaten[7][gastIndex] + 3;
			}

			// Unentschieden
			else {
				// Tabelleneinträge der Heimmanschaft aktualisieren
				spielDaten[2][heimIndex]++;
				spielDaten[7][heimIndex]++;

				// Tabelleneinträge der Gastmanschaft aktualisieren
				spielDaten[2][gastIndex]++;
				spielDaten[7][gastIndex]++;
			}

			System.out.println(heimMannschaft + " : " + gastMannschaft + " ("
					+ heimTore + ":" + gastTore + ")");
			System.out.println(mannschaften[heimIndex] + ", ST: "
					+ spielDaten[0][heimIndex] + ", S: "
					+ spielDaten[1][heimIndex] + ", U: "
					+ spielDaten[2][heimIndex] + ", N: "
					+ spielDaten[3][heimIndex] + ", GeschT: "
					+ spielDaten[4][heimIndex] + ", GegT: "
					+ spielDaten[5][heimIndex] + ", Diff: "
					+ spielDaten[6][heimIndex] + ", P: "
					+ spielDaten[7][heimIndex]);
			System.out.println(mannschaften[gastIndex] + ", ST: "
					+ spielDaten[0][gastIndex] + ", S: "
					+ spielDaten[1][gastIndex] + ", U: "
					+ spielDaten[2][gastIndex] + ", N: "
					+ spielDaten[3][gastIndex] + ", GeschT: "
					+ spielDaten[4][gastIndex] + ", GegT: "
					+ spielDaten[5][gastIndex] + ", Diff: "
					+ spielDaten[6][gastIndex] + ", P: "
					+ spielDaten[7][gastIndex]);
			System.out.println();

			return true;
		}
		return false;
	}

	public int[][] gibSpieldaten() {
		return spielDaten;
	}

	public String[] gibMannschaften() {
		return mannschaften;
	}
}
