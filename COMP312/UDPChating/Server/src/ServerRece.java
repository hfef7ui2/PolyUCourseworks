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

				// 2,创建数据包。
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);

				// 3,使用接收方法将数据存储到数据包中。
				ds.receive(dp);// 阻塞式的。

				// 4，通过数据包对象的方法，解析其中的数据,比如，地址，端口，数据内容。
				String ip = dp.getAddress().getHostAddress();
				int port = dp.getPort();
				String text = new String(dp.getData(), 0, dp.getLength());
				
				if(text.equals("INIT_HELO")){
					System.out.println("Receive connection");
					ServerDemo.clientList.add(ip);
					ServerDemo.messageQueue.push(ip + ":: connected");
					ServerDemo.clientNum++;
					System.out.println("Number of messages: " + ServerDemo.messageQueue.size());
					System.out.println("Port: " + port);
					continue;
				}else if(text.equals("886")){
					System.out.println("Receive disconection");
					for(int i = 0; i < ServerDemo.clientNum; i++){
						if(ServerDemo.clientList.get(i).equals(ip))
								ServerDemo.clientList.remove(i);
					}
					ServerDemo.messageQueue.push(ip + ":: leaved");
					ServerDemo.clientNum--;
					System.out.println("Number of messages: " + ServerDemo.messageQueue.size());
					continue;
				}else{
					System.out.println("Receive text");
					ServerDemo.messageQueue.push(text);
					System.out.println("Number of messages: " + ServerDemo.messageQueue.size());
				}
				if(ServerDemo.state = false)
					break;
			}
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}