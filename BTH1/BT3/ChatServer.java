
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ChatServer {
	List<Socket>clientList = new ArrayList<Socket>();
	public static void main(String[] args) {
		new ChatServer();
		}
	public ChatServer() {
		try {
			ServerSocket server = new ServerSocket(7777);
			System.out.println("Server is started");
			while (true) {
				Socket socket = server.accept();
				new AThread(socket,this).start();
				clientList.add(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class AThread extends Thread  {
	ChatServer server=null;
	Socket socket = null;
	DataInputStream din =null;
	DataOutputStream dos = null;
	public AThread(Socket socket, ChatServer x) {
		
		this.socket = socket;
		this.server=x;
	}
	public void SendToClients(ChatServer x, String s) {
		System.out.println(x.clientList.size());
		for(int i=0;i<x.clientList.size();i++) {
			try {
//				DataInputStream in = new DataInputStream(x.clientList.get(i).getInputStream());
				DataOutputStream out = new DataOutputStream(x.clientList.get(i).getOutputStream());
				out.writeUTF(s);
				out.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public synchronized void ThucThi() {
		try {
			din = new DataInputStream(socket.getInputStream());
//			dos = new DataOutputStream(socket.getOutputStream());

			while (true) {
				String s = din.readUTF();
				SendToClients(server, s);
					
			}

		} catch (IOException e1) {
			
		}finally {
			
				try {
//					if (socket != null) socket.close();
					if (din !=null) din.close();
					if (dos !=null) dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
	public void run() {
		ThucThi();
	}
	
}
