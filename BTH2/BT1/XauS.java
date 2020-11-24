
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XauS extends Thread{
	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket;
	public XauS(DatagramSocket serverSocket, DatagramPacket receive) throws Exception{
		this.receivePacket = receive;
		this.serverSocket = serverSocket;
		start();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("Server is started");
		DatagramSocket serverSocket = new DatagramSocket(1004);
		while (true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			new XauS(serverSocket, receivePacket);
		}
	}
	
	public void run() {
		try {
			byte[] sendData = new byte[10024];
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String str = new String(receivePacket.getData());
			str = str.trim();
			sendData = ("\t Chuoi dao:" + inverseString(str) +
					"\n\t Chuoi thuong:" + lowerCase(str) +
					"\n\t Chuoi hoa:" + upperCase(str) +
					"\n\t Chuoi vua hoa vua thuong:" + upperCaseLowerCase(str) +
					"\n\t Tan so xuat hien:" + frequencyApperance(str) + " \n").getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		} catch(Exception e) {}
	}
	
	public static String inverseString(String str) {
		String newStr = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			newStr += (char)str.charAt(i);
		}
		return newStr;
	}
	
	public static String lowerCase(String str) {
		String newStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
				newStr += (char)(str.charAt(i) + 32);
			} else newStr += (char)str.charAt(i);
		}
		return newStr;
	}
	
	public static String upperCase(String str) {
		String newStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
				newStr += (char)(str.charAt(i) - 32);
			} else newStr += (char)str.charAt(i);
		}
		return newStr;
	}
	
	public static String upperCaseLowerCase(String str) {
		String newStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
				newStr += (char)(str.charAt(i) - 32);
			}
			else if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
				newStr += (char)(str.charAt(i) + 32);
			} else newStr += (char)str.charAt(i);
		}
		return newStr;
	}
	
	public static String frequencyApperance(String str) {
		String newStr = "";
		str += ' ';
		Map<String, Integer> map = new HashMap<String, Integer>();
		int i = 0, j = 0;
		int len = str.length();
		while (j < len - 1 ) {
			if (str.charAt(j) != ' ' && str.charAt(j + 1) == ' ') {
				String s = str.substring(i, j + 1);
				if (map.containsKey(s)) map.put(s, map.get(s) + 1);
				else map.put(s, 1);
				i = j + 2;
				j += 2;
			}
			else if (str.charAt(j) == ' ') i = ++j;
			else j++;
		}
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String s = itr.next(); 
			newStr += s + ": " + map.get(s) +"; ";
		}
		return newStr;
	}
	
	public static int[] countWordAndVowel(String str) {
		str += ' ';
		while (str.length() > 1 && str.charAt(0) == ' ') str = str.substring(1);
		int i = 0, j = 0;
		int wordNumber = 0, vowelNumber = 0; 
		int len = str.length();
		while (j < len - 1 ) {
			if (str.charAt(j) != ' ' && str.charAt(j + 1) == ' ') {
				wordNumber++;
				i = j + 2;
				j += 2;
			}
			else j++;
		}
		
		for (i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'a' || str.charAt(i) == 'A'
					|| str.charAt(i) == 'o' || str.charAt(i) == 'O'
					|| str.charAt(i) == 'e' || str.charAt(i) == 'E'
					|| str.charAt(i) == 'i' || str.charAt(i) == 'I'
					|| str.charAt(i) == 'u' || str.charAt(i) == 'U') {
				vowelNumber++;
			}
		}
		int[] a = {wordNumber, vowelNumber};
		return a;
	}
}
