
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class ChatClient extends JFrame{
	Socket socket;
	String name;
	DataInputStream din;
	DataOutputStream dos;
	public static void main(String[] args) throws Exception {	
		new ChatClient();
	}
	public ChatClient() {
		
//		System.out.println("Nhap ten may");
//		Scanner sc = new Scanner(System.in);
//		name = sc.nextLine();
		
		name = JOptionPane.showInputDialog("Nhap vao ten may");
		this.setTitle(name);
		this.setSize(600,500);
		this.setLayout(null);
		
		final JTextArea txa = new JTextArea();
		txa.setFont(new Font("arial ",Font.PLAIN,15));
		txa.setBounds(0,0,590,400);
		txa.setEditable(false);
		this.add(txa);
		
		final JTextField ta= new JTextField();
        ta.setFont(new Font("arial ",Font.PLAIN,15));
        ta.setHorizontalAlignment(JTextField.LEFT);
        ta.setBounds(80,400,300,40);
        this.add(ta);
        
        JButton bta = new JButton("Send");
        bta.setFont(new Font("arial", Font.PLAIN,15));
        bta.setHorizontalAlignment(JButton.CENTER);
        bta.setBounds(400,400,90,40);
       
        bta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dos = new DataOutputStream(socket.getOutputStream());
					String a=ta.getText();
					dos.writeUTF(name+": "+a);
					dos.flush();
					ta.setText("");
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				
			}
		});
        this.add(bta);
        
        this.setVisible(true);
        
        try {
			this.socket = new Socket("localhost",7777);
			System.out.println("da ket noi");
			while(true) {
				din = new DataInputStream(socket.getInputStream());
				String s=din.readUTF();
				txa.setText(txa.getText()+s+"\n");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
	}

}
