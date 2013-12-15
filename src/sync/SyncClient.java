package sync;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SyncClient {
	
	private Socket sock;
	private BufferedReader dataIn;
	private DataOutputStream dataOut;
	
	public SyncClient(String host, Integer port) {
		this.sock		= this.setSocket(host, port);
		this.dataIn		= this.setDataIn();
		this.dataOut	= this.setDataOut();
	}
	
	public boolean sendData(String data) {
		boolean flag = false;
		try {
			this.dataOut.writeBytes(data);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String recvData() {
		String data = null;
		try {
			data = this.dataIn.readLine();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public Socket setSocket(String host, Integer port) {
		Socket sock = null;
		try {
			sock = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sock;
	}

	public BufferedReader setDataIn() {
		BufferedReader dataIn = null;
		try {
			dataIn = new BufferedReader( new InputStreamReader( this.sock.getInputStream() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataIn;
	}

	public DataOutputStream setDataOut() {
		DataOutputStream dataOut = null;
		try {
			dataOut = new DataOutputStream( this.sock.getOutputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataOut;
	}
	
	public void finalize() {
		try {
			this.sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
