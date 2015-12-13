import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerSend implements Runnable {

	private DatagramSocket ds;

	public ServerSend(DatagramSocket ds){
		this.ds = ds;
	}

	@Override
	public void run() {

		try {
			while(true){
				if(!ServerControl.messageQueue.empty()){
					String toSend = ServerControl.messageQueue.pop();
					toSend += " (from server)";
					byte[] buf = toSend.getBytes();
					for(int i = 0; i < ServerControl.clientNum; i++){
						DatagramPacket dp = 
								new DatagramPacket(buf,buf.length,
										InetAddress.getByName(ServerControl.clientList.get(i).getIp()),
										10001);
						ds.send(dp);
						System.out.println("One message was sent to" + InetAddress.getByName(ServerControl.clientList.get(i).getIp()) + "/" + 10001);
					}
				}
				if(ServerControl.state = false)
					break;						
			}

			ds.close();
		} catch (Exception e) {
		}
	}

}
