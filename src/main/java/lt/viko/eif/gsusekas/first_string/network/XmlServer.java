package lt.viko.eif.gsusekas.first_string.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

/**
 * Simple XML server that sends an XML file to a client over localhost.
 */
@Component
public class XmlServer {

    private static final int PORT = 5000;

    public void sendXmlFile(String filePath) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for client connection...");

            try (Socket socket = serverSocket.accept();
                 FileInputStream fileInputStream = new FileInputStream(filePath);
                 BufferedInputStream bufferedFileInput = new BufferedInputStream(fileInputStream);
                 OutputStream outputStream = socket.getOutputStream();
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {

                System.out.println("Client connected: " + socket.getInetAddress());

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = bufferedFileInput.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

                bufferedOutputStream.flush();
                socket.shutdownOutput();

                System.out.println("XML file sent successfully.");
            }

            System.out.println("Server finished work.");

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
