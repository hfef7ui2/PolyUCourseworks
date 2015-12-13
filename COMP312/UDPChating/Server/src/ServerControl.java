import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class ServerControl {
	public static ArrayList<ClientRecord> clientList = new ArrayList<ClientRecord>();
	public static MessageQueue messageQueue= new MessageQueue();
	public static int  clientNum= 0;
	public static boolean state = true;

	public static void main(String[] args) throws IOException {
		System.out.println("Server is open");
	    DatagramSocket soc = new DatagramSocket(10001);

		new Thread(new ServerSend(soc)).start();
		new Thread(new ServerRece(soc)).start();
	}
	
	public static void addClient(String i, String n){
		clientList.add(new ClientRecord(i, n));
	}
}

class ClientRecord{
	String ip;
	String name;
	
	public ClientRecord(String i, String n){
		ip = i;
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public String getIp(){
		return ip;
	}
}
