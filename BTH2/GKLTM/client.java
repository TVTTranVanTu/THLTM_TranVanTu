
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class client extends JFrame {

    DataInputStream din;
    DataOutputStream dos;

    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO Auto-generated method stub
        new TinhClient();

    }

    public client() throws UnknownHostException, IOException {

        Socket socket = new Socket("localhost", 8888);
        din = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        this.setTitle("Bai 1 client");
        this.setSize(800, 800);
        this.setLayout(null);

        JTextArea ja = new JTextArea();
        ja.setFont(new Font("arial", Font.BOLD, 15));
        ja.setBounds(10, 10, 750, 600);
        ja.setEditable(false);
        this.add(ja);

        JTextField tf = new JTextField();
        tf.setFont(new Font("arial", Font.BOLD, 15));
        tf.setHorizontalAlignment(JTextField.LEFT);
        tf.setBounds(10, 630, 600, 40);
        this.add(tf);

        JButton view = new JButton("Calculate");
        view.setFont(new Font("arial", Font.BOLD, 15));
        view.setHorizontalAlignment(JTextField.CENTER);
        view.setBounds(630, 630, 100, 40);
        view.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (tf.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui long nhap bieu thuc");
                    return;
                }
                try {
                    dos.writeUTF(tf.getText().trim());
                    dos.flush();
                    ja.setText(ja.getText() + "Client: " + tf.getText() + "\n");
                    String st = din.readUTF();
                    ja.setText(ja.getText() + "Server: " + st + "\n");
                    tf.setText("");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        this.add(view);
        this.setVisible(true);
    }
}
