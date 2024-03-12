import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * The EGutierrezUDPServer class implements a simple UDP server that receives data from clients and echoes it back.
 * It is designed to run continously, listening for incoming UDP packets and responding with the same data it receives.
 * It must be manually stopped by the user, for instance, by terminating the program's 
 * execution, or it will otherwise run indefinitely.
 */
public class EGutierrezUDPServer {
     /**
   * The main method that sets up the server and processes incoming packets.
   *
   * @param args Command line arguments, not used in this application.
   */
    public static void main(String[] args) {
     // Declare DatagramSocket outside the try-catch block to ensure it's closed later.
        DatagramSocket serverSocket = null; 
        try {
            // Open a DatagramSocket on port 10999.
            serverSocket = new DatagramSocket(10999);
            byte[] receiveData = new byte[1024]; // Buffer for incoming packets
            byte[] sendData = new byte[1024]; // Buffer for outgoing packets

            // Inform that the server is up and ready to receive packets
            System.out.println("UDP Server waiting for client on port " + serverSocket.getLocalPort() + "...");

            // Server main loop: always waits for new packets
              while(true){
                // Create a packet to receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // Receive packet from a client
                serverSocket.receive(receivePacket);

                // Retrieve the address and port of the client.
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                // Convert the received data into a string using the UTF-8
                String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

                System.out.println("RECEIVED: from IPAddress " + IPAddress + " and from port " + port + " the data: " + receivedSentence);

                //send data back to client 
                sendData = receivedSentence.getBytes();

                // Log the received data and the client's information
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                // Send the received data back to the client.
                serverSocket.send(sendPacket);

                // Reset the buffer to avoid sending stale data
                Arrays.fill(receiveData, (byte) 0);
                Arrays.fill(sendData, (byte) 0);

              }
        } catch (IOException e) {
            // Log any exception that occurs during the socket operation
            e.printStackTrace();
        } finally {
            // Ensure the server socket is closed when the program ends.
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Close the DatagramSocket in a finally block.
            }
        }
    }
}
