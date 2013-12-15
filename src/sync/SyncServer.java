package sync;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SyncServer {
	
	protected Integer port;
	protected ServerSocket sock;
	protected Socket client;
	
	public SyncServer(Integer port) {
		this.port = port;
		this.setSocket(this.port);
	}
	
	public boolean setSocket(Integer port) {
		boolean flag = false;
		try {
			this.sock = new ServerSocket(port);
			System.out.println("Server: Porta " + port + " aberta.");
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public void serveforever() {
		try{
			Scanner scan;
			while(true) {
				
				// Aguarda uma requisicao de um cliente
				this.client = this.sock.accept();
				System.out.print("Server: Nova conexao com o cliente: ");
				System.out.println( this.client.getInetAddress().getHostAddress() );
				
				// Cria um novo buffer para leitura do socket
				scan = new Scanner( this.client.getInputStream() );
				
				// Realiza a leitura dos pacotes recebidos
				while(scan.hasNextLine()) {
					String data = scan.next();
					System.out.println(data);
				}
				
				// Fecha o buffer de leitura
				scan.close();
				
				// Fecha a conexao tcp com o cliente
				this.client.close();
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			this.sock.close();
			System.out.println("Server: fechando servidor.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
