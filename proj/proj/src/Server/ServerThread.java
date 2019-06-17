/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import Server.Server;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.*;

/**
 *
 * @author skynet
 */
public class ServerThread extends Thread {
        
	private Server server;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String username;
	private Object message;
        ArrayList<String> onlineUsers = new ArrayList();
        public static   Connection conn;
    
        public static Statement myStm;
        public static ResultSet myRs;
    
	public ServerThread( Server server,  Socket socket) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		
              Class.forName("com.mysql.cj.jdbc.Driver");

              conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proj?useTimezone=true&serverTimezone=UTC","root","root");
 
                this.server = server;
		this.socket = socket;
		output = new ObjectOutputStream(this.socket.getOutputStream());
                output.flush();
		input = new ObjectInputStream(this.socket.getInputStream());
		
		username = (String) input.readObject();
		
		server.clients.put(username, output);
		server.outputStreams.put(socket, output);//to add to hashtable
		
		server.sendToAll("!" + server.clients.keySet()); // to send to clients names
		
		server.showMessage("\n\n" + username + "(" + socket.getInetAddress().getHostAddress() + ") is online :) ");
                
                                    myStm = conn.createStatement();
                                    
                                    String sql = "select name from onlineusers";
                                    myRs = myStm.executeQuery(sql);
                                    onlineUsers.clear();
                                    while (myRs.next()){
                                        
                                         onlineUsers.add(myRs.getString("name"));
                                     }
                 
               
//starting the thread
                
                start();
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		
		try {
			
                        
		
                        while(true ) {
				try{
                                 message = input.readObject();
                                    
					}catch (Exception e){
				
                                            
                                        // if we logout close the connections 
                                            break;
                                
                                }
				 
                                	
				if (message.toString().contains("@EE@")){
                                      
                                    
                                      myStm=conn.createStatement();
                                      String sqlSelectAllCreatedUsers ="select name from user";
                                      ResultSet myRs = myStm.executeQuery(sqlSelectAllCreatedUsers);
 
                                     while (myRs.next()){
                                 
                                     String sqlInsert = "insert into chat (name,txt)values ('"+myRs.getString("name")+"','"+message.toString().substring(5)+"' ) ";
                                      myStm=conn.createStatement();
                                     
                                      myStm.executeUpdate(sqlInsert);
                                     }
 
       
                                    
                                    server.sendToAll(message);}
				else {
                                    String reciever = message.toString().substring(1,message.toString().indexOf(':'));
                                  
			            String formattedMsg = "@" + username + message.toString().substring(message.toString().indexOf(':'), message.toString().length());
                                          myStm=conn.createStatement();
                                                      
                                    
                                    
                                    
                                    if (onlineUsers.contains(reciever))
                                        // Logged In
                                        server.sendPrivately(message.toString().substring(1, message.toString().indexOf(':')), formattedMsg);
                                     
                                    
                                    //Logged in Or Offline Users
                                    { myStm = conn.createStatement();
                                    String sqlInsert = "insert into chat (name,txt)values ('"+reciever+"' ,'"+formattedMsg.substring(1)+"')";
                                 
                                    myStm.executeUpdate(sqlInsert);
                                    }
                                    
                                
                                
                                }
			}
		}
                
                
                catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException ex) {
                    System.out.println("Error");   } 
                catch (Exception ex) { 
                    ex.printStackTrace();
            }
                
        finally{
			try {
				server.removeClient(username);
				server.removeConnection(socket, username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
                }                
    
        
        
		}}
        



