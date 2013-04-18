package com.scoreboard;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(9999);
			new Thread(new AcceptThread(server)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
