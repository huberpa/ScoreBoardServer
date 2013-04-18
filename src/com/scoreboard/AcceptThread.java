package com.scoreboard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * AcceptThread läuft in einer Endlosschleife und wartet darauf, dass sich neue
 * Clients verbinden.
 */
public class AcceptThread implements Runnable {

	private ServerSocket serverSock;
	private String anmeldung;

	/**
	 * Im Konstruktor wird der "ServerSocket" übergeben.
	 */
	public AcceptThread(ServerSocket serverSock) {
		this.serverSock = serverSock;
	}

	/**
	 * Wartet bis sich ein Client verbindet und liest dann die Nachricht aus.
	 * Dabei wird unterschieden, ob der Client sich nur einloggen will oder neu
	 * registrieren.
	 */
	@Override
	public void run() {

		if (serverSock != null) {
			while (!Thread.interrupted()) {
				try {
					Socket clientSock = serverSock.accept();
					System.out.println(clientSock.getInetAddress());
					BufferedReader br = new BufferedReader(
							new InputStreamReader(clientSock.getInputStream()));
					int counter = 0;

					while (!Thread.interrupted()) {

						if (br.ready()) {

							anmeldung = br.readLine();
							System.out.println(anmeldung);
							Thread.currentThread().interrupt();
						}
					}

					counter++;
					doWait();

					if (counter > 1000) {

						Thread.currentThread().interrupt();
						clientSock.close();
					}
				}

				catch (Exception e) {
					e.printStackTrace();
				}
				doWait();
			}
		}
	}

	/**
	 * Pausiert den Thread um 10 Millisekunden.
	 */
	public static void doWait() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
