package udp_Trinh;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;
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
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class TinhC {
	private JFrame frmCalculator;
	private JTextField textField;
	private JTextField result;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TinhC window = new TinhC();
					window.frmCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TinhC() throws Exception{
		initialize();
	}

	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmCalculator.setTitle("Calculator");
		frmCalculator.setBounds(100, 100, 694, 381);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {176, 279, 100};
		gridBagLayout.rowHeights = new int[] {76, 85, 30, 63, 27};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		frmCalculator.getContentPane().setLayout(gridBagLayout);
		JLabel lblNewLabel = new JLabel("Enter expression:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frmCalculator.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frmCalculator.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton calculateBtn = new JButton("Calculate");
		calculateBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		calculateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InetAddress IPAddress = InetAddress.getByName("localhost");
					String str = textField.getText();
					if (str.isEmpty()) return;
					DatagramSocket clientSocket = new DatagramSocket();
					byte[] sendData = new byte[1024];
					sendData = str.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1005);
					clientSocket.send(sendPacket);
					byte[] receiveData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					String resultText = new String(receiveData, StandardCharsets.US_ASCII).trim();
					result.setText(resultText);
					clientSocket.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_calculateBtn = new GridBagConstraints();
		gbc_calculateBtn.insets = new Insets(0, 0, 5, 0);
		gbc_calculateBtn.fill = GridBagConstraints.BOTH;
		gbc_calculateBtn.gridx = 2;
		gbc_calculateBtn.gridy = 1;
		frmCalculator.getContentPane().add(calculateBtn, gbc_calculateBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Result: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		frmCalculator.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		result = new JTextField();
		result.setFont(new Font("Tahoma", Font.PLAIN, 20));
		result.setEnabled(false);
		GridBagConstraints gbc_result = new GridBagConstraints();
		gbc_result.insets = new Insets(0, 0, 5, 5);
		gbc_result.fill = GridBagConstraints.BOTH;
		gbc_result.gridx = 1;
		gbc_result.gridy = 3;
		frmCalculator.getContentPane().add(result, gbc_result);
		result.setColumns(10);
	}

}
