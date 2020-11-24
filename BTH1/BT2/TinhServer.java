

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TinhServer extends Thread {

	DataInputStream din;
	DataOutputStream dos;

	public TinhServer(DataInputStream din, DataOutputStream dos) {
		this.din = din;
		this.dos = dos;
	}

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8888);
		System.out.println("Server started");
		while (true) {
			Socket socket = server.accept();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream din = new DataInputStream(socket.getInputStream());
			TinhServer bai2Server = new TinhServer(din, dos);
			bai2Server.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String st = din.readUTF().trim();
				String elementMath[] = null;
				elementMath = processString(st);
				elementMath = postfix(elementMath);
				String kq = valueMath(elementMath);
				dos.writeUTF(kq + "");
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String[] postfix(String[] elementMath) {
		String s1 = "", E[];
		Stack<String> S = new Stack<String>();
		for (int i = 0; i < elementMath.length; i++) {
			char c = elementMath[i].charAt(0);
			if (!isOperator(c))
				s1 = s1 + " " + elementMath[i];
			else {
				if (c == '(')
					S.push(elementMath[i]);
				else {
					if (c == ')') {
						char c1;
						do {
							c1 = S.peek().charAt(0);
							if (c1 != '(')
								s1 = s1 + " "+ S.peek();
							S.pop();
						} while (c1 != '(');
					} else {
						while (!S.isEmpty() && priority(S.peek().charAt(0)) >= priority(c)) {
							s1 = s1 + " "+ S.peek();
							S.pop();
						}
						S.push(elementMath[i]);
					}
				}
			}
		}
		while (!S.isEmpty()) {
			s1 = s1 + " " + S.peek();
			S.pop();
		}
		s1 = s1.trim();
		E = s1.split(" ");
		return E;
	}

	public String[] processString(String sMath) {
		String s1 = "", elementMath[] = null;
		sMath = sMath.trim();
		sMath = sMath.replaceAll("\\s+", " ");
		for (int i = 0; i < sMath.length(); i++) {
			char c = sMath.charAt(i);
			if (!isOperator(c))
				s1 = s1 + c;
			else
				s1 = s1 + " " + c + " ";
		}
		s1 = s1.trim();
		s1 = s1.replaceAll("\\s+", " ");
		elementMath = s1.split(" ");
		return elementMath;
	}

	public boolean isOperator(char c) {
		char operator[] = { '+', '-', '*', '/', ')', '(' };
		Arrays.sort(operator);
		if (Arrays.binarySearch(operator, c) > -1)
			return true;
		else
			return false;
	}

	public int priority(char c) {
		if (c == '+' || c == '-')
			return 1;
		else if (c == '*' || c == '/')
			return 2;
		else
			return 0;
	}

	public String valueMath(String[] elementMath) {
		Stack<String> S = new Stack<String>();
		for (int i = 0; i < elementMath.length; i++) {
			char c = elementMath[i].charAt(0);
			if (!isOperator(c))
				S.push(elementMath[i]);
			else {
				double num = 0f;
				double num1 = Float.parseFloat(S.pop());
				double num2 = Float.parseFloat(S.pop());
				switch (c) {
				case '+':
					num = num2 + num1;
					break;
				case '-':
					num = num2 - num1;
					break;
				case '*':
					num = num2 * num1;
					break;
				case '/':
					num = num2 / num1;
					break;
				default:
					break;
				}
				S.push(Double.toString(num));
			}
		}
		return S.pop();
	}
}

