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

				// 2,�������ݰ���
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);

				// 3,ʹ�ý��շ��������ݴ洢�����ݰ��С�
				ds.receive(dp);// ����ʽ�ġ�

				// 4��ͨ�����ݰ�����ķ������������е�����,���磬��ַ���˿ڣ��������ݡ�
				String text = new String(dp.getData(), 0, dp.getLength());
				controller.appendText(text);
				System.out.println(text);
				

			}
		} catch (Exception e) {

		}

	}

}