import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private static final int TIMEOUT = 5000;
    private static final int MAXNUM  = 5;
    public static void main(String args[])throws IOException {
        String  Data = "Hello UDP Server";
        byte[]  buf  = new byte[1024];

        /**
         * UDPsocket        UDP 的 socket 接口
         * Localhost        本机的IP地址
         * RequestPacket    封装请求的 Packet
         * ResponsePacket   用来接收响应的Packet
         */


        DatagramSocket UDPsocket      = new DatagramSocket(9000);
        InetAddress    Localhost      = InetAddress.getLocalHost(); // 获得本机的InetAddress信息
        DatagramPacket RequestPacket  = new DatagramPacket(Data.getBytes(), Data.length(), Localhost, 3333);
        DatagramPacket ResponsePacket = new DatagramPacket(buf, 1024);

        UDPsocket.setSoTimeout(TIMEOUT);

        int tries = 0; // 由于UDP是不可靠传输，所以没 有收到响应时，可以重发Request
        boolean receivedResponse = false;

        while(!receivedResponse && tries < MAXNUM) {

            // 发送请求

            UDPsocket.send(RequestPacket);
            try {
                UDPsocket.receive(ResponsePacket);
                if(!ResponsePacket.getAddress().equals(Localhost)) {
                    throw new IOException("Received Packet From An Unkown Sourse");
                }
                receivedResponse = true;
            } catch(InterruptedIOException e) {
                tries++;
                System.out.println("Time Out, " + (MAXNUM - tries) + "more tries");
            }
        }

        // 处理响应
        if(receivedResponse) {
            System.out.println("Client Received Data From Server: ");
            String res = new String(ResponsePacket.getData(), 0, ResponsePacket.getLength()) + " from " + ResponsePacket.getAddress().getHostAddress() + ":" + ResponsePacket.getPort();
            System.out.println(res);
            ResponsePacket.setLength(1024);
        } else {
            System.out.println("No Response --- Give Up");
        }
        UDPsocket.close();
    }
}
