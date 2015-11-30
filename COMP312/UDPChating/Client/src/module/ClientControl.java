package module;
import java.io.*;
import java.net.DatagramSocket;

import javax.swing.JTextArea;


public class ClientControl {
	private ClientSend send;
	private ClientRece rece;
	private static String serverIP = "40.74.138.27";
	private JTextArea textArea;
	DatagramSocket soc;
	
	/**
	 * @param args
	 * @throws IOException 
	 */	
	public ClientControl() throws IOException {
		soc = new DatagramSocket();
		textArea = new JTextArea();
		
		send = new ClientSend(soc, this);
		rece = new ClientRece(soc, this);
		
		new Thread(rece).start();	
	}
	
	public void inputMessage(String s) throws Exception{
		send.fillInputBoard(s);
		send.send();
	}
	
	public void connectToServer(String s) throws Exception{
		textArea.append("Connecting to server...\n");
		textArea.append("Connecting to server.....\n");
		textArea.append("Connecting to server.......\n");
		send.setIP(serverIP);
		send.setPort(10001);
		inputMessage("INIT_HELO" +"."+s);
	}
	
	public void connectToP2P(String s, String p){
		send.setIP(s);
		send.setPort(Integer.parseInt(p));
	}

	public void appendText(String s){
		textArea.append(s + "\r\n");
	}
	
	public JTextArea getTextArea(){
		return textArea;
	}
	
	public void setServerMode() {
		send.setServerMode();
	}
	
	public void setP2PMode() {
		send.setP2PMode();
	}
	
	public void clearText(){
		textArea.setText("");
	}
	
	public int getPort(){
		return soc.getLocalPort();
	}
}
