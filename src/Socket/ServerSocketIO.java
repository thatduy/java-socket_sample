package Socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.DataOutputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;

import Code.GlobalVar;

public class ServerSocketIO {

	public final static int SOCKET_PORT = 13267;
	ServerSocket servsock = null;
	Socket sock = null;
	ServerSocket socket;
	Socket client;

	public void startServer(JLabel logLabel) throws Exception {
		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				try {
					
					socket = new ServerSocket(SOCKET_PORT);
					
					for (int i = 0; i < GlobalVar.FILE_NAME.size(); i++) {
						client = socket.accept();
					GlobalVar.log += "Accepted connection : " + client + "\n";
					System.out.println("size "+GlobalVar.FILE_NAME.size());
					
						DataOutputStream d = new DataOutputStream(client.getOutputStream());

						d.writeUTF(GlobalVar.FILE_NAME.get(i) + ";" + GlobalVar.FILE_NAME.size());
						BufferedInputStream in = new BufferedInputStream(
								new FileInputStream(GlobalVar.FILE_TO_SEND.get(i)));

						BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());

						GlobalVar.log += "SENDING " + GlobalVar.FILE_TO_SEND.get(i) + " \n";
						int len = 0;
						byte[] buffer = new byte[1024 * 50];
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
							System.out.print("#");
						}
						in.close();
						out.flush();
						out.close();
						System.out.println("\nDone server!");
						GlobalVar.log += "SEND successfully \n";
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								/* update GUI code */
								logLabel.setText(GlobalVar.standarString(GlobalVar.log));
							}
						});
					}
					

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
				} finally {
					
					try {
						socket.close();
						client.close();
						System.out.println("CLose all");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();

	}

}
