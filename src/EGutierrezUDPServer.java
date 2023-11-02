import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class EGutierrezUDPServer {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null; // Declare DatagramSocket outside the try-catch block to ensure it's closed later.
        try {
            serverSocket = new DatagramSocket(10999);
             byte[] receiveData = new byte[1024];
             byte[] sendData = new byte[1024];
              System.out.println("UDP Server waiting for client on port " + serverSocket.getLocalPort() + "...");

              while(true){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

                System.out.println("RECEIVED: from IPAddress " + IPAddress + " and from port " + port + " the data: " + receivedSentence);

                //send data back to client 
                sendData = receivedSentence.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

              serverSocket.send(sendPacket);
                Arrays.fill(receiveData, (byte) 0);
                Arrays.fill(sendData, (byte) 0);

              }
            
            //     System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Close the DatagramSocket in a finally block.
            }
        }
    }
}
