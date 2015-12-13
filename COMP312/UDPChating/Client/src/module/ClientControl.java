package module;
import java.io.*;
import java.net.DatagramSocket;
import javax.swing.JTextArea;


public class ClientControl {
	private ClientSend send;
	private ClientRece rece;
	private static String serverIP = "40.74.136.51";//The server has been closed after the presentation, as the rent of server is expensive.
	private JTextArea textArea;
	DatagramSocket soc;
	
	/**
	 * @param args
	 * @throws IOException
	 */	
	public ClientControl() throws IOException {
		soc = new DatagramSocket(10001);
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
		inputMessage("INIT_HELO" +"."+s);
	}
	
	public void connectToP2P(String s){
		send.setIP(s);
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
}
