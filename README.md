# UDP
 Java-based application that demonstrates the fundamental principles of UDP (User Datagram Protocol) communication.
  The server waits for incoming datagram packets from clients on a specified port and, upon receiving a message, echoes the message back to the sender. This process is handled in an infinite loop, making the server capable of handling continuous requests without interruption.

- Note: It is recommended to split the terminal in your IDE so you can run both client and server simultaneously. By doing so, you can observe the real-time transfer of packets between the client and server, which can be insightful for understanding UDP's behavior 


## What I Learned
- UDP Communication: Gained hands-on experience with UDP, understanding its connectionless nature and how it differs from TCP.
  -  UDP's connectionless protocol, which sends datagrams without establishing a prior connection, unlike TCP, which requires a three-way handshake to set up a secure connection before data transfer
- Network Programming: Got a better understanding of sockets and handling I/O exceptions that may occur during network communication.
- Byte Manipulation: Became adept at converting strings to byte arrays and vice versa, essential for sending and receiving data over a network
- Learned how to use Java's DatagramSocket and DatagramPacket classes to send and receive packets in a network.

  
