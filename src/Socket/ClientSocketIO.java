package Socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.io.InputStream;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


import javax.swing.JLabel;

import Code.GlobalVar;

public class ClientSocketIO {
	public final static int SOCKET_PORT = 13267;
	public final static String FILE_TO_SEND = "C:/Users/ASUS/Desktop/image.jpg";
	public String SERVER = ""; // localhost
	public String FILE_TO_RECEIVED = ""; //
	boolean flag = false;

	public static int FILE_SIZE = 6022386;

	boolean isServerOn = false;
	ServerSocket servsock = null;
	Socket sock = null;
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	
	public void startClient(JLabel logLabel) throws Exception {
		final byte[] ip;
		try {
			ip = InetAddress.getLocalHost().getAddress();
			System.out.println();
		} catch (Exception e) {
			return;
		}
		for (int i = 1; i <= 254; i++) {
			final int j = i;
			new Thread(new Runnable() { // new thread for
				public void run() {
					try {
						ip[3] = (byte) j;
						InetAddress address = InetAddress.getByAddress(ip);
						String output = address.toString().substring(1);
						if (address.isReachable(1000)) {
							
							processClient(output);
						}
					}catch (Exception e) {
						System.out.println("CLIENT "+ e.toString());
					} finally {
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								/* update GUI code */
								logLabel.setText(GlobalVar.standarString(GlobalVar.log));
							}
						});
					}
				}
			}).start();;
		}
		
		
   }
	public void processClient(String ip) throws Exception{
		int i = -1;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		Socket socket = null;
		while (i != 0) {
			socket = new Socket(ip, SOCKET_PORT);
			File desktop = new File(System.getProperty("user.home"), "Desktop");
			FILE_TO_RECEIVED = desktop.getAbsolutePath() + "\\";
	        
	        
	        InputStream is = socket.getInputStream();
			DataInputStream d = new DataInputStream(is);
			GlobalVar.log += ip + " is connected \n";
			String data = d.readUTF().trim();
			String s = data.split(";")[0];
			if(i != -1) {
				i = Integer.parseInt(data.split(";")[1]) ;
			}
			i--;
			String name = FILE_TO_RECEIVED + s;
	         in = 
	             new BufferedInputStream(socket.getInputStream());

	         out = 
	             new BufferedOutputStream(new FileOutputStream(name));
	             
	        int len = 0;
	        byte[] buffer = new byte[1024*50];
	        while ((len = in.read(buffer)) > 0) {
	             out.write(buffer, 0, len);
	             System.out.print("#");
	        }
	        
	        GlobalVar.log += "RECEIVED at " + name + "\n";
	        GlobalVar.log+="RECEIVED successfully \n";
	        System.out.println("\nDone client!");
	        in.close();
	        out.flush();
	        out.close();
	        socket.close();
			
		}
		
        
		
	}
	
}
