package udp_Trinh;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatS extends Thread {
	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket;
	private static List<InetAddress> listIP = new ArrayList<InetAddress>();
	private static List<Integer> listPort = new ArrayList<Integer>();
	private static Set<String> existingClient = new HashSet<String>();

	public ChatS(DatagramSocket serverSocket, DatagramPacket receivePacket) throws Exception {
		this.serverSocket = serverSocket;
		this.receivePacket = receivePacket;
		String ID = receivePacket.getAddress().toString() + receivePacket.getPort();
		if (!existingClient.contains(ID)) {
			existingClient.add(ID);
			listIP.add(receivePacket.getAddress());
			listPort.add(receivePacket.getPort());
		}
		start();
	}

	public void run() {
		try {
			byte[] sendData = new byte[1024];
			sendData = receivePacket.getData();
			for (int i = 0; i < listIP.size(); i++) {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, listIP.get(i),
						listPort.get(i));
				serverSocket.send(sendPacket);
			}
		} catch (Exception ex) {
		}
	}

	public static void main(String[] args) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(1006);
		System.out.println("Server is started");
		while (true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			new ChatS(serverSocket, receivePacket);
		}
	}
}
