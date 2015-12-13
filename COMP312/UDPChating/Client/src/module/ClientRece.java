package module;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientRece implements Runnable {

	private DatagramSocket ds;
	ClientControl controller;

	public ClientRece(DatagramSocket ds, ClientControl c) {
		this.ds = ds;
		controller = c;
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);

				ds.receive(dp);
				System.out.println("message received");

				String text = new String(dp.getData(), 0, dp.getLength());
				controller.appendText(text);
				System.out.println(text);
			}
		} catch (Exception e) {

		}

	}

}