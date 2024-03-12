import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;
/**
 * This class is a simple UDP client that sends a ping message to a UDP server
 * and receives a pong message back. The client measures the round-trip time (RTT)
 * and calculates the throughput of the message exchange.
 */
public class EGutierrezUDPClient {
    /**
   * The main method that runs the UDP client.
   *
   * @param args Command line arguments. Expects one argument: the ping message
   */
  public static void main(String[] args) {
    // Ensure that the command line argument (the ping message) is provided
    if (args.length != 1) {
      System.out.println("Java UDPClient <ping_message>");
      return;
    }

    // The ping message to send.
    String pingMessage = args[0];

    try {
      // Create a UDP socket for sending and receiving packets
      DatagramSocket clientSocket = new DatagramSocket();

      // Server details 
      String serverName = "localhost";   // or you can use an IP address -> String serverName = "192.168.1.152"; 
      InetAddress IPAddress = InetAddress.getByName(serverName);
      int port = 10999;// The destination port on the server

      // Convert the ping message to bytes for sending.
      byte[] sendData = pingMessage.trim().getBytes("UTF-8");

      // Inform the user that the ping message is being sent
      System.out.println("Sending ping message: " + pingMessage);

      // Add this line to log the "Ping" message content
      System.out.println("Sent Ping Message: " + new String(sendData, "UTF-8"));

      // Prepare to receive the response from the server
      byte[] receiveData = new byte[1024]; // A buffer to store the incoming data
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

       // Send data to client
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
      clientSocket.send(sendPacket);

      // Start the timer 
      long startTime = System.currentTimeMillis();

      //Recieve the packet
      clientSocket.receive(receivePacket);

      // Stop the timer
      long endTime = System.currentTimeMillis();

      // Calculate RTT is ms
      long rttMs = endTime - startTime;

      // Calculate RTT in seconds 
      double secondsRTT = (double)rttMs/1000;
     

      // Extract the pong message from the response packet.
      String receivedPong = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8"); // Specify UTF-8

      // Check if the received pong message matches the sent ping message.
      if (receivedPong.equals(pingMessage.trim())) {

        // Calculate the number of bits of the pingMessage 
        int pingLengthBits = pingMessage.length() * 8;
        
        // Display the ping bits 
        System.out.println(pingLengthBits);

       // Calculate the throughput in megabits per second (Mbps)
        double throughputMbps = (((double)pingLengthBits / (secondsRTT/2)) / 1e6);

        // Format RTT and throughput to two decimal places for better readability
        String formattedRTT = String.format("%.2f", (double)rttMs);
        String formattedThroughput = String.format("%.2f", (double)throughputMbps);

        // Output the results to the console
        System.out.println("Pong matches the sent ping");
        System.out.println("Ping Length(bits): " + pingLengthBits);
        System.out.println("RTT in ms:"+formattedRTT);
        System.out.println("Throughput (Mbps):"+ formattedThroughput);
      } else {
        System.out.println("Error:Received Pong does not match ping");
      }
      // Close the client socket
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Converts an array of bytes to a hexadecimal string representation.
   *
   * @param bytes The array of bytes to convert.
   * @return The hexadecimal string representation of the byte array.
   */
  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);
    for (byte b : bytes) {
        hexString.append(String.format("%02X ", b));
    }
    return hexString.toString();
}

}
