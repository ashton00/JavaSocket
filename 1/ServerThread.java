import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket client = null;
    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {

            /**
             * SocketRes Socket的输出流，可以看成响应
             * SocketReq Socket的输入流，可以看成请求
             * stopFlag  是否关闭标志
             */
            PrintStream     SocketRes  = new PrintStream(client.getOutputStream());
            BufferedReader  SocketReq  = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean         stopFlag   = false;
            while(!stopFlag) {
                String req = SocketReq.readLine();
                System.out.println("客户端发送数据为：" + req);
                if(req == null || "".equals(req)) {
                    stopFlag = true;
                } else {
                    if("quit".equals(req)) {
                        System.out.println("客户端断开连接");
                        stopFlag = true;
                    } else {
                        SocketRes.println("echo : " + req);
                    }
                }
            }
            SocketRes.close();  // 关闭Socket输出流
            client.close();     // 关闭Socket
        } catch(Exception err) {
            err.printStackTrace();
        }

    }
}
