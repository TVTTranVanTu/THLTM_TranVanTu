package udp_Trinh;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class TinhS extends Thread{
	private DatagramSocket socketServer;
	private DatagramPacket receivePacket;
	public TinhS(DatagramSocket serverSocket, DatagramPacket receivePacket) throws Exception{
		this.socketServer = serverSocket;
		this.receivePacket = receivePacket;
		start();
	}
	
	public static void main(String[] args) {
		try {
			DatagramSocket serverSocket = new DatagramSocket(1005);
			System.out.println("Server is started");
			while (true) {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				new TinhS(serverSocket, receivePacket);
			}
		} catch (Exception e) {}
	}
	
	public void run() {
		try {
			String text = new String(receivePacket.getData()).trim();
			if (text.startsWith("-")) text = "0" + text;
			text = text.replaceAll(" ", "");
			String result;
			try {
				result = calculate(text);
			} catch(Exception e) {
				result = "Error Expression";
			}
			byte[] sendData = new byte[1024];
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = result.getBytes(StandardCharsets.US_ASCII);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			socketServer.send(sendPacket);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static int getPriority(char c) {
		if (c == '(') return 0;
		else if (c == '+' || c == '-') return 1;
		else return 2;
	}
	
	public static boolean isDigit(char c) {
		if (c >= '0' && c <= '9') return true;
		return false;
	}
	
	public static boolean isOperation(char c) {
		if (c == '-' || c == '+' || c == '/' || c == '*') return true;
		return false;
	}
	
	public static boolean isOperation(String s) {
		if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) return true;
		return false;
	}
	
	public static float cal(float nd1, float nd2, char op) throws Exception{
		switch (op) {
			case '+': return nd1 + nd2;
			case '-': return nd1 - nd2;
			case '*': return nd1 * nd2;
			case '/': 
				if (nd2 == 0) throw new Exception();
				return nd1 / nd2;
		}
		return 0;
	}

	public static String calculate(String str) throws Exception{
		Stack<String> ndStack = new Stack<String>();
		Stack<Character> opStack = new Stack<Character>();
		int len = str.length(), i = 0;
		while (i < len) {
			char c = str.charAt(i);
			if (isDigit(c)) {
				int j = i + 1;
				while (j < len && isDigit(str.charAt(j))) j++;
				ndStack.push(str.substring(i,j));
				i = j;
			}
			else if ( c == '(') {
				opStack.push(c);
				i++;
			}
			else if (isOperation(c)) {
				while (!opStack.isEmpty() && isOperation(opStack.peek()) 
						&& getPriority(c) <= getPriority(opStack.peek())) {
					ndStack.push("" + opStack.pop());	
				}
				opStack.push(c);
				i++;
			}
			else if (c == ')') {
				while (!opStack.isEmpty() && opStack.peek() != '(') {
					ndStack.push("" + opStack.pop());
				}
				opStack.pop();
				i++;
			} 
			else throw new Exception();
		}
		while (!opStack.isEmpty()) ndStack.push("" + opStack.pop());
		Iterator<String> itr = ndStack.iterator();
		List<String> list = new ArrayList<String>();
		while (itr.hasNext()) {
			String st = itr.next();
			if (isOperation(st)) {
				float nd2 = Float.parseFloat(list.remove(list.size() - 1));
				float nd1 = Float.parseFloat(list.remove(list.size() - 1));
				list.add(Float.toString(cal(nd1, nd2, st.charAt(0))));
			} else {
				list.add(st);
			}
		}
		return list.get(0);
	}
}
