import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket client = new Socket("127.0.0.1", 3333);
        client.setSoTimeout(10000); // 设置超时连接时间为10s

            /**
         * input        获取系统标准输入
         * SocketOutput 获取Socket的输出流
         * SocketInput  获取Socket的输入流
         * stopFlag     是否关闭，输入quit关闭
         */

        BufferedReader input         = new BufferedReader(new InputStreamReader(System.in));
        PrintStream    SocketOutput  = new PrintStream(client.getOutputStream());
        BufferedReader SocketInput   = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean        stopFlag      = false;

        while(!stopFlag) {
            System.out.println("请输入信息：");
            String str = input.readLine();  // 获取系统输入

            SocketOutput.println(str);      // 向Socket输出流写入

            if("quit".equals(str)) {
                stopFlag = true;
            } else {
                try {
                    String res = SocketInput.readLine(); // 获取Socket输入
                    System.out.println(res);
                } catch(SocketTimeoutException e) {
                    System.out.println("Socket Time Out, No Response");
                }
            }
        }

        input.close();  // 关闭系统输出流
        if(client != null) {
            client.close();
        }
    }
}
