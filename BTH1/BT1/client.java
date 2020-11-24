import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class client extends JFrame {
    Socket socket;
    public final static String ip = "192.168.1.28";
    public final static int port = 8989;
    public static void main(String[] args) throws Exception{
        new client();
    }

    public client(){
        try {
            this.socket= new Socket(ip,port);
        }  catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        this.setTitle("Doi chuoi");
        this.setSize(700,500);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);

        JLabel la=new JLabel("Nhap chuoi");
        la.setFont(new Font("arial ",Font.BOLD,15));
        la.setBounds(50 ,50, 120, 50);
        this.add(la);

        JLabel lb= new JLabel("Chu hoa");
        lb.setFont(new Font("arial", Font.BOLD,15));
        lb.setBounds(50, 100, 120, 50);
        this.add(lb);

        JLabel lc= new JLabel("Chu thuong");
        lc.setFont(new Font("arial", Font.BOLD,15));
        lc.setBounds(50, 150, 120, 50);
        this.add(lc);

        JLabel ld= new JLabel("Vua hoa vua thuong");
        ld.setFont(new Font("arial", Font.BOLD,15));
        ld.setBounds(50, 200, 150, 50);
        this.add(ld);

        JLabel le= new JLabel("So Tu");
        le.setFont(new Font("arial", Font.BOLD,15));
        le.setBounds(50, 250, 120, 50);
        this.add(le);

        final JTextField ta = new JTextField();
        ta.setFont(new Font ("arial",Font.BOLD,15));
        ta.setHorizontalAlignment(JTextField.RIGHT);
        ta.setBounds(250,50,300,50);
        this.add(ta);

        final JTextField tb= new JTextField();
        tb.setFont(new Font("arial ",Font.BOLD,15));
        tb.setHorizontalAlignment(JTextField.RIGHT);
        tb.setBounds(250,100,300,50);
        this.add(tb);

        final JTextField tc= new JTextField();
        tc.setFont(new Font("arial ",Font.BOLD,15));
        tc.setHorizontalAlignment(JTextField.RIGHT);
        tc.setBounds(250,150,300,50);
        this.add(tc);

        final JTextField td= new JTextField();
        td.setFont(new Font("arial ",Font.BOLD,15));
        td.setHorizontalAlignment(JTextField.RIGHT);
        td.setBounds(250,200,300,50);
        this.add(td);

        final JTextField te= new JTextField();
        te.setFont(new Font("arial ",Font.BOLD,15));
        te.setHorizontalAlignment(JTextField.RIGHT);
        te.setBounds(250,250,300,50);
        this.add(te);

        JButton doi =new JButton("Doi Chuoi");
        doi.setFont(new Font("arial",Font.BOLD,15));
        doi.setHorizontalAlignment(JButton.CENTER);
        doi.setBounds(300,320,150,50);
        doi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String a=ta.getText();
                    dos.writeUTF(a);
                    dos.flush();
                    tb.setText(din.readUTF());
                    tc.setText(din.readUTF());
                    td.setText(din.readUTF());
                    te.setText(""+din.readInt());
                }
                catch(Exception e1) {
                    System.out.println(e1);
                }

            }
        });
        this.add(doi);
        this.setVisible(true);

    }
}

