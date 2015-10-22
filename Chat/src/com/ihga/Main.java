package com.ihga;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;

public class Main {
	
	private static JFrame frame;
	private static ChatArea chatArea;
	
	private static Scanner sc;
	
	private static Socket client;
	private static ServerSocket host;
	
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main(String[] args){
		String input = "";
		sc = new Scanner(System.in);
		while(!input.equals("client") || !input.equals("server")){
			System.out.println("Client of server?");
			input = sc.next();
			if(input.equals("client")){
				//Create client
				createClientSocket();
			}else if(input.equals("server")){
				//Create server
				createServerSocket();
			}			
		}		
	}
	
	private static IP getInput() {
		System.out.println("Geef IP");
		String ip = sc.next();
		System.out.println("Geef port");
		int port = sc.nextInt();
		return new IP(ip, port);
	}
	
	private static void createClientSocket(){
		IP ip = getInput();
		createGUI();
		try {
			System.setProperty("javax.net.ssl.trustStore", "clienttrust");
		    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			client = ssf.createSocket(ip.ip, ip.port);
			addMessage("Starting client...");
			openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addMessage("Connection terminated");
		}
		
	}
	
	private static void createServerSocket(){
		System.out.println("Geef poort");
		int port = sc.nextInt();
		createGUI();
		try {
			addMessage("Starting server...");
			System.setProperty("javax.net.ssl.trustStore", "clienttrust");
		    SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			host = ssf.createServerSocket(port);
			while(true){
				addMessage("Waiting for client...");
				client = host.accept();
				openConnection();				
			}			
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			addMessage("Connection terminated");
		}
	}
	
	private static void createGUI(){
		frame = new JFrame("Chat with your mateys");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chatArea = new ChatArea();
		
		frame.add(chatArea);
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}			
		});		
	}
	
	private static void openConnection(){
		try{
			addMessage("Connection established with " + client.getInetAddress().getHostAddress());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
			while(true){
				String msgIn = in.readLine();
				addMessage(client.getInetAddress().getHostAddress() + ": " + msgIn);		
			}
		}catch(SocketException e){
			e.printStackTrace();
			addMessage(e.getMessage());
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			addMessage(e.getMessage());
		}
		
	}
	
	public static void addMessage(String msg){
		chatArea.addMessage(msg);
		frame.requestFocus();
	}
	
	public static void sendMessage(String msg){
		addMessage("me: " + msg);
		try{
			out.println(msg);
			out.flush();			
		}catch(NullPointerException e){
			addMessage("No connection!");
		}
	}

}
