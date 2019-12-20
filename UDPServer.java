import java.net.*;
import java.util.Arrays;

public class UDPServer {

    public static void main(String[] argv)
        throws Exception {
          byte[] sendBuffer = "Broadcast message was received".getBytes();
          byte receiveBuffer[] = new byte[1024];
          String b_address="127.0.0.1";
          int cn=0; // client number
          int i=1; // counter for clients

          if (argv.length!=2){
              System.out.println
              ("Usage(server): java UDPServer \"Broadcast address\" \"Number of clients\" \n");
              System.exit(0);
          } else {
            b_address=argv[0]; // broadcast address
            cn=Integer.parseInt(argv[1]); // the number of clients
          }

          try{
            while (true) {
              // Please write here your code to create a socket address
              // to communicate with the server.
              // For the port number, please use "49513".
              InetSocketAddress broadcastAddress =
                  new InetSocketAddress(b_address, 49513);

              DatagramSocket ClientSocket = new DatagramSocket();

              // Please write here your code to send the packet of "sendBuffer".
              DatagramPacket sendPacket =
                  new DatagramPacket(sendBuffer,sendBuffer.length,broadcastAddress);
              ClientSocket.send(sendPacket);

              // In the for-loop below,
              // the ack from each client who receives the UDP packet
              // should be sent to the server.
              // Please use the available port number from 49513+i
              // to communicate with the client "i".
              for(i=1;i<=cn;i++){
                  System.out.println(i);

                DatagramPacket receivePacket =
                    new DatagramPacket(receiveBuffer,receiveBuffer.length);
                DatagramSocket receiveSocket = new DatagramSocket(49513+i);
                receiveSocket.setSoTimeout( 1000 ); // timeout in [ms].
                // Receive the ack from a client.
                ClientSocket.receive(receivePacket);

                // If the server successfully received the ack,
                // then display it with the hostname and close the corresponding socket.
                if (receivePacket.getLength()!=0){
                  System.out.println
                      (new String(Arrays.copyOf(receivePacket.getData(),receivePacket.getLength()),"UTF-8")); // desplay the received data.
                  receiveSocket.close(); // close the socket
                }
              }

              // if the server receives an ack from all clients, then stop.
              if (cn==i-1){
                ClientSocket.close(); // close the socket
                break;
              }
              i=1;
          }
        } catch (Exception ignored){
        }
    }
}