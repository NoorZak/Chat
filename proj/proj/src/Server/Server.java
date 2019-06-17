/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.sql.*;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author skynet
 */
public class Server {
	
	JFrame serverGui;
	public  JTextArea displayWindow;
	
        public final ServerSocket serverSocket;
	
        public  Socket socket;
	
        
        public Hashtable<Socket, ObjectOutputStream> outputStreams;//to store the value of  sockect whith their outpuut streasms 
	public Hashtable<String, ObjectOutputStream> clients;// the name of user with output streams
	
	public Server(int port) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		
                
     ImageIcon icon = new ImageIcon("D:\\New\\new.jpg");

               String[] themes = {"theme1", "theme2", "theme3","theme4"};
      
           String t = (String)JOptionPane.showInputDialog(null, "Select Your Choice??", 
                "Choices", JOptionPane.QUESTION_MESSAGE, icon, themes, themes[0]);
               
               
               
        com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "INSERT YOUR LICENSE KEY HERE", "Company");
        if(t.equals("theme1")){
        UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
              
        }
        
        else if(t.equals("theme2")){
        UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                   
        }
        
        else if (t.equals("theme3")){
        UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
              
        }
        
        else if (t.equals("theme4")){
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
             
        }
           
                
                serverGui = new JFrame("Server");
		serverGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverGui.setSize(500, 500);
		displayWindow = new JTextArea();
		displayWindow.setFont(new Font("Aril",Font.BOLD,14));
                serverGui.add(new JScrollPane(displayWindow), BorderLayout.CENTER);
		serverGui.setVisible(true);
                
                

		
		outputStreams = new Hashtable<Socket, ObjectOutputStream>();
		clients = new Hashtable<String, ObjectOutputStream>();
		
		serverSocket = new ServerSocket(port);
		showMessage("Waiting for clients at"+serverSocket);
                
	}

	public void waitingForClients() throws IOException, ClassNotFoundException, SQLException{
	//	wait for client to connect with
		while (true){
			socket = serverSocket.accept();
			
			new ServerThread(this, socket);
		}
	}
	
	//displaying message on Server Gui
	public void showMessage(final String message) {
	
	displayWindow.append(message);
	}
        
        
	public void sendToAll(Object data) throws IOException{
		
		for (Enumeration<ObjectOutputStream> e = getOutputStreams(); e.hasMoreElements(); ){
			//since we don't want server to remove one client and at the same time sending message to it
                        //to avoid logout or any event while sending
                        synchronized (outputStreams){
				ObjectOutputStream tempOutput = e.nextElement();
				tempOutput.writeObject(data);
				tempOutput.flush();
			}
		}
	}

		public Enumeration<ObjectOutputStream> getOutputStreams() {
		return outputStreams.elements();
	}
	
	public void sendPrivately( Object username,  Object message)  throws Exception {
		ObjectOutputStream tempOutput = clients.get(username);
                
		tempOutput.writeObject(message);
		tempOutput.flush();
	}
	
		public void removeClient(String username) throws IOException{
			
			synchronized (clients){
				clients.remove(username);
				sendToAll("!" + clients.keySet()); 
		
                        }
		}
	
	public void removeConnection(Socket socket, String username) throws IOException{
		
		synchronized (outputStreams){
			outputStreams.remove(socket);
		}
		
			showMessage("\n\n" + username + "(" + socket.getInetAddress().getHostAddress() + ") is offline");

	}
}








