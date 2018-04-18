package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogRecord;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.ws.handler.Handler;

import Code.GlobalVar;
import Socket.ClientSocketIO;
import Socket.ServerSocketIO;

public class MainUI extends JFrame {
	public final static int SOCKET_PORT = 13267;
	public final static String FILE_TO_SEND = "C:/Users/ASUS/Desktop/socket/image.jpg";
	public final static String SERVER = "localhost"; // localhost
	public final static String FILE_TO_RECEIVED = "C:/Users/ASUS/Desktop/image_server.jpg"; // you may change this, I
																							// give a
	// different name because i don't want to
	// overwrite the one used by server...

	public final static int FILE_SIZE = 6022386; // file size temporary hard coded
													// should bigger than the file to be downloaded

	boolean isServerOn = false;
	ServerSocket servsock = null;
	Socket sock = null;
	JButton btnServer, btnImport;
	private JButton btnSend;
	private JButton btnScan;
	private JTextField urlImport;
	protected File[] fileValues;
	private JLabel log;

	public MainUI(String title) {
		this.setTitle(title);
		addView();
		addEvents();
		
	}


	private void addView() {
		// TODO Auto-generated method stub
		// Add button start - stop server
		Container contaner = this.getContentPane();
		JPanel panelMain = new JPanel();
		btnServer = new JButton("SEND FILE");
		
		btnScan = new JButton("GET FILE");
		urlImport = new JTextField();
		urlImport.setPreferredSize(new Dimension(580, 30));
		btnImport = new JButton("IMPORT");
		JPanel panelImport = new JPanel(); 
		panelImport.add(urlImport);
		panelImport.add(btnImport);
		panelMain.add(panelImport);
		panelMain.add(btnServer);
		panelMain.add(btnScan);
		
		log = new JLabel();
		JPanel pnLog = new JPanel();
		
		log.setText("");
		pnLog.setBackground(Color.WHITE);
		pnLog.setPreferredSize(new Dimension(720, 400));
		pnLog.add(log);
		panelMain.add(pnLog);
		contaner.add(panelMain);
		//contaner.add(panelImport);

	}

	

	private void addEvents() {
		// TODO Auto-generated method stub
		btnServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(GlobalVar.FILE_TO_SEND == null) {
					JOptionPane.showMessageDialog(null, "Choose file before send");
				} else {
					try {
						GlobalVar.log += "Started Server\n";
						log.setText(GlobalVar.standarString(GlobalVar.log));
						new ServerSocketIO().startServer(log);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				

			}
		});

		btnScan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					GlobalVar.log += "Started Client\n";
					log.setText(GlobalVar.standarString(GlobalVar.log));
					new ClientSocketIO().startClient(log);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			


			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				//In response to a button click:
				int returnVal = fc.showOpenDialog(btnImport);
				String cString = "";
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileValues = fc.getSelectedFiles();
					for (File file : fileValues) {
						GlobalVar.FILE_TO_SEND.add(file.getAbsolutePath());
						GlobalVar.FILE_NAME.add(file.getName());
						cString+=file.getAbsolutePath() + ";";
					}

					
		            urlImport.setText(cString);
		            
		            
		        }
			}
		});
	}

	public void showWindow() {
		this.setSize(720, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
