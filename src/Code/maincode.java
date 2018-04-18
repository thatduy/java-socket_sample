package Code;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ui.MainUI;

public class maincode {
	 public final static int SOCKET_PORT = 13267;      // you may change this
	  public final static String SERVER = "127.0.0.1";  // localhost
	  public final static String
	       FILE_TO_RECEIVED = "C:/Users/ASUS/Desktop/image_ser.jpg";  // you may change this, I give a
	                                                            // different name because i don't want to
	                                                            // overwrite the one used by server...

	  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
	                                               // should bigger than the file to be downloaded
	
	
	  public final static String FILE_TO_SEND = "C:/Users/ASUS/Desktop/image.jpg";  // you may change this
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		MainUI mainUI = new MainUI("Socket IO tranfer file");
		mainUI.showWindow();
		//server();
		
	}
	
	public static void server() throws Exception {
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    ServerSocket servsock = null;
	    Socket sock = null;
	    try {
	      servsock = new ServerSocket(SOCKET_PORT);
	      while (true) {
	        System.out.println("Waiting...");
	        
	        try {
	          sock = servsock.accept();
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
	      if (servsock != null) servsock.close();
	    }
	}
	
	
}
