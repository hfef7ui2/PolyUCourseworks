import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerRece implements Runnable {

	private DatagramSocket ds;

	public ServerRece(DatagramSocket ds) {
		this.ds = ds;
	}

	@Override
	public void run() {
		try {
			while (true) {

				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				
				ds.receive(dp);

				String ip = dp.getAddress().getHostAddress();
				String text = new String(dp.getData(), 0, dp.getLength());
				
				//if a new client comes
				if(text.startsWith("INIT_HELO")){
					System.out.println("Receive connection");
					
					String[] temp = text.split("\\.");
					ServerControl.addClient(ip, temp[1]);
					
					ServerControl.messageQueue.push(temp[1] + "(" + ip + ")" + ":: connected");
					
					ServerControl.clientNum++;
					
					System.out.println("Number of messages: " + ServerControl.messageQueue.size());
					System.out.println("IP: " + ip);
					
					continue;
				}else if(text.startsWith("DIS_BYE")){//if a client leave
					ServerControl.clientNum--;

					System.out.println("Receive disconection");
					
					String[] temp = text.split("\\.");
					
					for(int i = 0; i < ServerControl.clientNum; i++){
						if(ServerControl.clientList.get(i).getName().equals(temp[1]))
								ServerControl.clientList.remove(i);
					}
					
					ServerControl.messageQueue.push(temp[1] + "(" + ip + ")" + ":: leaved");

					continue;
				}else{//if it is a normal message
					System.out.println("Receive text");
					
					String[] temp = text.split("\\.");
					
					ServerControl.messageQueue.push(temp[0] + "(" + ip + ")" + ":: " + temp[1]);
					System.out.println("Number of messages: " + ServerControl.messageQueue.size());
				}
								
				if(ServerControl.state = false)
					break;
			}
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}