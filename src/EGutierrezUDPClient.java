import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class EGutierrezUDPClient {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Java UDPClient <ping_message>");
      return;
    }
    // set it = to zero after confirmation; String pingMessage = args[0]
    String pingMessage = args[0];
    try {

      DatagramSocket clientSocket = new DatagramSocket();
      String serverName = "localhost";
      InetAddress IPAddress = InetAddress.getByName(serverName);
      // String serverName = "192.168.1.152";
      int port = 10999;

      byte[] sendData = pingMessage.trim().getBytes("UTF-8");

      System.out.println("Sending ping message: " + pingMessage);

      // Add this line to log the "Ping" message content
      System.out.println("Sent Ping Message: " + new String(sendData, "UTF-8"));

     

// receive data from server
      byte[] receiveData = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

       // send data
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
      clientSocket.send(sendPacket);

      // start timer
      long startTime = System.currentTimeMillis();

      
      clientSocket.receive(receivePacket);

      // stop timer
      long endTime = System.currentTimeMillis();
      // calculate RTT is ms
      long rttMs = endTime - startTime;

      //calculate RTT in seconds 
      double secondsRTT = (double)rttMs/1000;
     

      // receive pong from server
      String receivedPong = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8"); // Specify UTF-8



      // pong matches sentence validation
      if (receivedPong.equals(pingMessage.trim())) {

        // calculation of pingMessage bits, and throughput
        int pingLengthBits = pingMessage.length() * 8;
      
        System.out.println(pingLengthBits);
        //1e6 is the same as saying 1 million 
        double throughputMbps = (((double)pingLengthBits / (secondsRTT/2)) / 1e6);

        //format the RTT too two decimal places 
        String formattedRTT = String.format("%.2f", (double)rttMs);
        String formattedThroughput = String.format("%.2f", (double)throughputMbps);
        // output
        System.out.println("Pong matches the sent ping");
        System.out.println("Ping Length(bits): " + pingLengthBits);
        System.out.println("RTT in ms:"+formattedRTT);
        System.out.println("Throughput (Mbps):"+ formattedThroughput);
      } else {
        System.out.println("Error:Received Pong does not match ping");
      }

      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);
    for (byte b : bytes) {
        hexString.append(String.format("%02X ", b));
    }
    return hexString.toString();
}

}
