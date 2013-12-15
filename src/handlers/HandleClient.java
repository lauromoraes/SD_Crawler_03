package handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class HandleClient extends Thread{
	
	private Socket socket;
	private BufferedReader in;
	private DataOutputStream out;
	
	public HandleClient(Socket socket) {
		this.socket= socket;
		this.in = this.setDataIn();
		this.out = this.setDataOut();
		System.out.print("Handle: criado novo Handler para a conexao com o cliente: ");
		System.out.println(this.socket.getLocalSocketAddress());
	}
	
	public BufferedReader setDataIn() {
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public DataOutputStream setDataOut() {
		DataOutputStream buffer = null;
		try {
			buffer = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public void finalize() {
		try {
			String address =  this.socket.getLocalSocketAddress().toString();
			this.in.close();
			this.out.close();
			this.socket.close();
			System.out.print("Handle: fechando conexao com o  cliente: ");
			System.out.println(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try{
			System.out.print("Handle: processando conexao com o cliente: ");
			System.out.println(this.socket.getLocalSocketAddress());
			// Realiza a leitura dos pacotes recebidos
			String data;
			String endLine = "END";
			while((data = this.in.readLine()) != null) {
				if(!data.startsWith(endLine)) {
					System.out.println(data);
				} else {
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.finalize();
	}
	
}
