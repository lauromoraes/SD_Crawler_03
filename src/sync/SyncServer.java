package sync;

import handlers.HandleClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SyncServer {
	
	private Integer port;
	private ServerSocket sock;
	private LinkedList<HandleClient> handlers;
	
	public SyncServer(Integer port) {
		this.port = port;
		this.setSocket(this.port);
		this.handlers = new LinkedList<HandleClient>(); 
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
			while(true) {
				System.out.println("Server: aguardando nova conexao...");
				// Aguarda uma requisicao de um cliente
				Socket sock = this.sock.accept();
				
				// Adiciona um novo Handler para tratar o cliente
				this.handlers.addLast(new HandleClient( sock ));
				
				// Executa o Handler como uma thread
				this.handlers.getLast().start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			// Aguarda a finalizacao de cada uma das threads
			for(HandleClient client : this.handlers) {
				client.join();
			}
			// Fecha o socket do servidor
			this.sock.close();
			System.out.println("Server: fechando servidor.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
