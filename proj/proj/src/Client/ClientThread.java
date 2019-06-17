/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.ClientGUI;
import static Server.ServerThread.conn;
import static Server.ServerThread.myStm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author skynet
 */

public class ClientThread implements Runnable{
        public static   Connection conn;    
        public static Statement myStm;
        public static ResultSet myRs;
    
	public Socket SOCK;
	public ObjectInputStream in;
	public String[] currentUsers;
	ArrayList<String>offlineUsers = new ArrayList(ClientGUI.createdUsers);
	
        //Constructor getting the socket
	public ClientThread(Socket X){
		this.SOCK = X;
        
        
        }

	@Override
	public void run() {
		
		try{
				in = new ObjectInputStream(SOCK.getInputStream());
				CheckStream();
			
		}catch(Exception E){
			JOptionPane.showMessageDialog(null, E);
		}
		
	}
	
	
	
	
	
	
	public void CheckStream() throws IOException, ClassNotFoundException, SQLException{
		while(true){
			RECEIVE();
		}
	}
	
	
	
	
	
	
	public  void RECEIVE() throws IOException, ClassNotFoundException, SQLException{
		
                     Class.forName("com.mysql.cj.jdbc.Driver");

              conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proj?useTimezone=true&serverTimezone=UTC","root","root");
              
                
            
            if(!in.equals(null)){
               
			String message = (String) in.readObject();
			
			
			if(message.startsWith("!")) {
				String temp1 = message.substring(1);
					temp1 = temp1.replace("[", "");
					temp1 = temp1.replace("]", "");
				
				currentUsers = temp1.split(", ");
				
                                Arrays.sort(currentUsers);
                                offlineUsers=new ArrayList(ClientGUI.createdUsers);
                                for(String user : currentUsers)
                                    offlineUsers.remove(user);
				try {
				
					SwingUtilities.invokeLater(
						new Runnable(){
							public void run() {
                
                                                            
                            
                	ClientGUI.onlineList.setListData(currentUsers);
                                                    
                	ClientGUI.offlineusersList.setListData(offlineUsers.toArray());
                
           
              	
         
                                                        }} );}
  
                                                            
               				
				catch (Exception e) {
                                    
					JOptionPane.showMessageDialog(null, "lists connot be inserted");
				}
			}
			
		
			else if(message.startsWith("@EE@|")) {
				final String temp2 = message.substring(5);
				
			                                
                                                    ClientGUI.chatTxt.append("\n"+temp2);

                        }
			
			else if(message.startsWith("@")){
				final String temp3 = message.substring(1);
				
			                            ClientGUI.chatTxt.append("\n"+temp3);
                        
                                                                }
			
                        
		}
	}
	
	
	
	
	
	public  void SEND(final String str) throws IOException{
		String writeStr;
		if(str.startsWith("@"))
                {
		
                    ClientGUI.chatTxt.append("\n" + ClientGUI.userName + ": " + str);
                				
					
                writeStr = str;
                }
                
		else
                        
                {
                    writeStr = "@EE@|" + ClientGUI.userName + ": " + str;
               
                }
		
                
                ClientGUI.output.writeObject(writeStr);
		
                
                ClientGUI.output.flush();
	
			
	}
	
                                                                }