package main;

import sync.SyncClient;
import sync.SyncServer;

public class Main extends Thread {
	
	
	public Main() {
		
	}

	public static void main(String[] args) {
		Main app = new Main();
		System.out.println("SERVER");
		app.start();
		System.out.println("CLIENT");
		app.syncClientApp();
		
		try {
			app.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void syncServerApp() {
		try {
			int port = 6969;
			SyncServer server = new SyncServer(port);
			server.serveforever();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void syncClientApp() {
		String host = "localhost";
		int port = 6969;
		SyncClient client = new SyncClient(host, port);
		try {
			String data = "teste01";
			client.sendData(data);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.syncServerApp();
	}

}
