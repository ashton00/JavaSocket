import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args)throws IOException{
        String data = "Hello UDPclient";
        byte[] buf      = new byte[1024];

        /**
         * UDPsocket     UDP的 socket 接口
         * RequestPacket 用于接收 UDP 数据包
         */

        DatagramSocket UDPsocket     = new DatagramSocket(3333);
        DatagramPacket RequestPacket = new DatagramPacket(buf, 1024);
        boolean        stopFlag      = false;

        System.out.println("Server is On，Waiting For Client To Send Data......");
        while(!stopFlag) {

            // 接收请求数据

            UDPsocket.receive(RequestPacket);
            System.out.println("Server Received Data From Client：");

            String request = new String(RequestPacket.getData(), 0, RequestPacket.getLength()) +
                    " from " + RequestPacket.getAddress().getHostAddress() + ":" + RequestPacket.getPort();

            System.out.println(request);

            // 发送响应数据

            DatagramPacket ResponsePacket = new DatagramPacket(data.getBytes(), data.length(), RequestPacket.getAddress(), 9000);
            UDPsocket.send(ResponsePacket);

            // 由于 RequestPacket 在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            // 所以这里要将 RequestPacket 的内部消息长度重新置为1024
            RequestPacket.setLength(1024);
        }
        UDPsocket.close();
    }
}
