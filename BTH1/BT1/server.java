import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8989);
            System.out.println("Server is started");
            while (true) {
                Socket socket = server.accept();
                new ThreadSocket(socket).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

class ThreadSocket extends Thread {
    Socket socket = null;
    DataInputStream din = null;
    DataOutputStream dout = null;
    Scanner sc = new Scanner(System.in);

    public ThreadSocket(Socket socket) {

        this.socket = socket;
    }

    String DoiChuHoa(String s) {
        char x[] = s.toCharArray();
        s = "";
        for (int i = 0; i < x.length; i++) {
            int ascii = x[i];
            if (ascii >= 97 && ascii <= 122) {
                s += (char) (ascii -= 32);
            } else {
                s += (char) ascii;
            }
        }
        return s;

    }

    String DoiChuThuong(String s) {
        char x[] = s.toCharArray();
        s = "";
        for (int i = 0; i < x.length; i++) {
            int ascii = x[i];
            if (ascii >= 65 && ascii <= 90) {
                s += (char) (ascii += 32);
            } else {
                s += (char) ascii;
            }
        }
        return s;

    }

    String VuaHoaVuaThuong(String s) {
        char x[] = s.toCharArray();
        s = "";
        for (int i = 0; i < x.length; i++) {
            int ascii = x[i];
            if (ascii >= 97 && ascii <= 122) {
                s += (char) (ascii -= 32);
            } else if (ascii >= 65 && ascii <= 90) {
                s += (char) (ascii += 32);
            } else {
                s += (char) ascii;
            }
        }
        return s;

    }

    int DemSoTu(String s) {
        String[] x = s.split(" ");
        int i = x.length;
        return i;
    }

    public synchronized void ThucThi() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while (true) {
                String s = din.readUTF();
                dout.writeUTF(DoiChuHoa(s));
                dout.flush();
                dout.writeUTF(DoiChuThuong(s));
                dout.flush();
                dout.writeUTF(VuaHoaVuaThuong(s));
                dout.flush();
                dout.writeInt(DemSoTu(s));
                dout.flush();
                sc.reset();
            }

        } catch (IOException e1) {

        } finally {

            try {
                if (socket != null)
                    socket.close();
                if (din != null)
                    din.close();
                if (dout != null)
                    dout.close();
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
