package com.scoreboard;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * AcceptThread läuft in einer Endlosschleife und wartet darauf, dass sich neue
 * Clients verbinden.
 */
public class AcceptThread implements Runnable {

	private ServerSocket serverSock;

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
			System.out.println("warte auf Verbindung");
			while (!Thread.interrupted()) {
				try {
					Socket clientSock = serverSock.accept();
					System.out.println(clientSock.getInetAddress());

					new Thread(new QueryHandler(clientSock)).start();

				} catch (Exception e) {
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
