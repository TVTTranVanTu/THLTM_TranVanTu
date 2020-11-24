import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JScrollPane;

public class XauC{
	private JFrame frmXuLyXau;
	private JTextField textField;

	public static void main(String[] args) {
		try {
			XauC window = new XauC();
			window.frmXuLyXau.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XauC() throws Exception{
		initialize();
	}

	private void initialize() {
		frmXuLyXau = new JFrame();
		frmXuLyXau.setTitle("Xu ly xau");
		frmXuLyXau.setBounds(100, 100, 706, 431);
		frmXuLyXau.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {499, 148};
		gridBagLayout.rowHeights = new int[] {295, 69};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		frmXuLyXau.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 15, 20, 15);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmXuLyXau.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.setBackground(Color.BLACK);
		textArea.setLineWrap(true);
		textArea.setEnabled(false);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					String text = textField.getText().trim();					
					if (text.isEmpty()) return;
					DatagramSocket clientSocket = new DatagramSocket();
					InetAddress IPAddress = InetAddress.getByName("localhost");
					byte[] sendData = new byte[1024];
					byte[] recieveData = new byte[10024];
					sendData = text.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1004);
					clientSocket.send(sendPacket);
					textArea.append("\n Xau nhap:" + text + "\n");
					DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
					clientSocket.receive(recievePacket);
					ByteArrayInputStream bis = new ByteArrayInputStream(recievePacket.getData());
					BufferedReader dis =new BufferedReader(new InputStreamReader(bis));
					String result = dis.readLine();
					textArea.append(result + "\n");
					result = dis.readLine();
					textArea.append(result+ "\n");
					result = dis.readLine();
					textArea.append(result+ "\n");
					result = dis.readLine();
					textArea.append(result+ "\n");
					result = dis.readLine();
					textArea.append(result+ "\n");
					clientSocket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBackground(Color.WHITE);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 15, 10, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frmXuLyXau.getContentPane().add(textField, gbc_textField);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 10, 15);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		frmXuLyXau.getContentPane().add(btnNewButton, gbc_btnNewButton);
	}

}
