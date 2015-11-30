

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
				if(!ServerDemo.messageQueue.empty()){
					String toSend = ServerDemo.messageQueue.pop();
					toSend += " (from server)";
					byte[] buf = toSend.getBytes();
					for(int i = 0; i < ServerDemo.clientNum; i++){
						DatagramPacket dp = 
								new DatagramPacket(buf,buf.length,
										InetAddress.getByName(ServerDemo.clientList.get(i)),
										10001);
						ds.send(dp);
					}
				}
				if(ServerDemo.state = false)
					break;						
			}

			ds.close();
		} catch (Exception e) {
		}
	}

}
