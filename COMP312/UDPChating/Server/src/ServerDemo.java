import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class ServerDemo {
	public static ArrayList<String> clientList = new ArrayList<String>();
	public static MessageQueue messageQueue= new MessageQueue();
	public static int  clientNum= 0;
	public static boolean state = true;

	public static void main(String[] args) throws IOException {
		
		DatagramSocket send = new DatagramSocket(10002);
		DatagramSocket rece = new DatagramSocket(10001);
		new Thread(new ServerSend(send)).start();
		new Thread(new ServerRece(rece)).start();
		
	}
	
	
	

}
