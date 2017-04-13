import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server     = new ServerSocket(3333);
        Socket       client     = null;
        boolean      stopFlag   = false;
        while(!stopFlag) {
            client = server.accept();
            System.out.print("与客户端连接成功\n");
            new Thread(new ServerThread(client)).start(); // 为每个客户端都开启一个线程
        }
        server.close();
    }
}
