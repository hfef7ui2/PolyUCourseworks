package module;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSend {
	private String inputMessage;

	private DatagramSocket ds;
	private String receiverIP;
	private ClientControl controller;
	boolean serverMode;
	static final int DESTINATIONPORT = 10001;
	
	public ClientSend(DatagramSocket ds, ClientControl c){
		this.ds = ds;
		controller = c;
	}

	public void send() throws Exception{

		byte[] buf = inputMessage.getBytes();
		DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName(receiverIP),DESTINATIONPORT);
		ds.send(dp);
		System.out.println("message sent");
		if(!serverMode)
			controller.appendText(inputMessage);
	}

	public void fillInputBoard(String s){
		inputMessage = new String(s);
	}

	public void setIP(String s) {
		receiverIP = s;
	}
	
	public void setServerMode(){
		serverMode = true;
	}
	
	public void setP2PMode(){
		serverMode = false;
	}
}
