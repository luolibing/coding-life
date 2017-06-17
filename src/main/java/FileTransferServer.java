import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by LuoLiBing on 15/11/21.
 */
public class FileTransferServer {
    public final static int SOCKET_PORT = 13267;  // you may change this
    public final static String FILE_TO_SEND = "/test.sh";  // you may change this

    public static void main (String [] args ) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket serverSocket = null;
        Socket sock = null;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = serverSocket.accept();
                    System.out.println("Accepted connection : " + sock);
                    // send file
                    File myFile = new File (FILE_TO_SEND);
                    byte [] mybytearray  = new byte [(int)myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray,0,mybytearray.length);
                    os = sock.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray,0,mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                }
                finally {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if (sock!=null) sock.close();
                }
            }
        }
        finally {
            if (serverSocket != null) serverSocket.close();
        }
    }
}