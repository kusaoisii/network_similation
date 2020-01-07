import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class UDPClient {
    public static void main(String[] argv)
        throws Exception {
        DatagramSocket receiveSocket = new DatagramSocket(49513); // a socket to receive message from the server.
        DatagramSocket serverSocket = new DatagramSocket(); // a socket to send message to the server.
        byte receiveBuffer[] = new byte[1024];
        byte sendBuffer[] = new byte[1024];
        String s_address; // server's address
        int ID=0;

        if (argv.length!=2){
            System.out.println
            ("Usage(client): java UDPServer \"Server's address\" \"Your ID\" \n");
        } else {
          s_address=argv[0]; // server address
          ID=Integer.parseInt(argv[1]); // the ID
        }

        // please write a code here to receive a UDP packet from the server.
        DatagramPacket receivePacket =
            new DatagramPacket(receiveBuffer,receiveBuffer.length);

        // bind a server address with its port number.
        InetSocketAddress serverAddress =
            new InetSocketAddress(argv[0], 49513+ID);

        while (true) {
          // write a code here to receive a broadcast message from the server.
          receiveSocket.receive(receivePacket);
          // display the received packet.
          System.out.println(new String(Arrays.copyOf(receivePacket.getData(),receivePacket.getLength()),"UTF-8"));

          byte ACK[]="ACK from: ".getBytes();
          InetAddress addr = InetAddress.getLocalHost();
          byte Host_Name[]=addr.getHostName().getBytes();
          ByteBuffer byteBuff=ByteBuffer.allocate(ACK.length + Host_Name.length);
          byteBuff.put(ACK);
          byteBuff.put(Host_Name);
          sendBuffer= byteBuff.array();

          // write a code here to send an ack to the server.
          DatagramPacket sendPacket =
              new DatagramPacket(sendBuffer,sendBuffer.length,serverAddress);
          serverSocket.send(sendPacket);
        }


    }
}