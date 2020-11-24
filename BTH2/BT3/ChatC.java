package udp_Trinh;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class ChatC {
	private DatagramSocket clientSocket;
	private JFrame frmClient;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel lblNewLabel;
	private InetAddress IPAddress;
	private boolean isConnected;

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Dialog();
			}
		});
	}

	public ChatC() throws Exception {
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName("localhost");
		initFrame();
		addScrollTextArea();
		addClientLabel();
		addTextField();
		addSendButton();
		getMsgReceiveThread().start();
	}

	public void sendMessage(String s) throws Exception {
		s = lblNewLabel.getText() + ": " + s;
		byte[] sendData = new byte[1024];
		sendData = s.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1006);
		clientSocket.send(sendPacket);
	}

	public String receiveMessage() throws Exception {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		return new String(receivePacket.getData()).trim();
	}

	public Thread getMsgReceiveThread() {
		return new Thread() {
			public void run() {
				try {
					do {
						try {
							sendMessage("GREETINGS");
							isConnected = true;
						} catch (Exception e) {
						}
					} while (!isConnected);

					while (true) {
						String str = receiveMessage();
						textArea.append(str + "\n");
					}
				} catch (Exception e) {
				}
			}
		};
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initFrame() {
		frmClient = new JFrame();
		frmClient.setTitle("Client Chat");
		frmClient.setBounds(400, 400, 900, 600);
		frmClient.setResizable(false);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 180, 440, 160 };
		gridBagLayout.rowHeights = new int[] { 400, 70 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0 };
		frmClient.getContentPane().setLayout(gridBagLayout);
	}

	void addScrollTextArea() {
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(5, 5, 10, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmClient.getContentPane().add(scrollPane, gbc_scrollPane);

		textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.BLACK);
		textArea.setEnabled(false);
		textArea.setFont(new Font("Tahoma", Font.BOLD, 20));
		scrollPane.setViewportView(textArea);
	}

	void addClientLabel() {
		lblNewLabel = new JLabel("Client");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		frmClient.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
	}

	void addTextField() {
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		frmClient.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
	}

	void addSendButton() {
		JButton sendButton = new JButton("Send");
		sendButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String str = textField.getText();
					if (str.isEmpty())
						return;
					sendMessage(str);
					textField.setText("");
				} catch (Exception ex) {
				}
			}
		});
		GridBagConstraints gbc_sendButton = new GridBagConstraints();
		gbc_sendButton.fill = GridBagConstraints.BOTH;
		gbc_sendButton.insets = new Insets(0, 0, 5, 5);
		gbc_sendButton.gridx = 2;
		gbc_sendButton.gridy = 1;
		frmClient.getContentPane().add(sendButton, gbc_sendButton);
	}

	static class Dialog {
		public Dialog() {
			JFrame f = new JFrame("Chat Room");
			JLabel label = new JLabel("Enter Name: ");
			label.setFont(new Font("Tahoma", Font.BOLD, 20));
			label.setBounds(20, 20, 150, 60);
			final JTextField name = new JTextField();
			name.setFont(new Font("Tahoma", Font.BOLD, 20));
			name.setBounds(180, 20, 350, 60);
			JButton button = new JButton("Create");
			button.setFont(new Font("Tahoma", Font.BOLD, 20));
			button.setBounds(550, 20, 120, 60);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ChatC window = new ChatC();
						window.lblNewLabel.setText(name.getText());
						window.frmClient.setVisible(true);
						f.setVisible(false);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			f.getContentPane().add(label);
			f.getContentPane().add(name);
			f.getContentPane().add(button);
			f.setBounds(400, 400, 700, 140);
			f.getContentPane().setLayout(null);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setResizable(false);
		}
	}
}
