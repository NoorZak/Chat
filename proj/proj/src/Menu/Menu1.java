/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;
import Client.ClientGUI;
import Client.ClientThread;
import Server.Server;
import java.io.IOException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author skynet
 */
public class Menu1 {
    static   Connection conn;
    
    static Statement myStm;
    static ResultSet myRs;
    
    static ResultSet myRs1;
   static ArrayList<String> createdUsers = new ArrayList();
   static String createdUsersArr[] ;
    /**
     * @param args the command line arguments
     */
 
   
    
    public static void men1() throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
    
        
        
        Class.forName("com.mysql.cj.jdbc.Driver");

              conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proj?useTimezone=true&serverTimezone=UTC","root","root");
           com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "INSERT YOUR LICENSE KEY HERE", "Company");
                        UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
    
  
      
               String[] options = {"CreateUser", "Login", "RunServer"};
           ImageIcon icon = new ImageIcon("D:\\New\\new.jpg");

           String n = (String)JOptionPane.showInputDialog(null, "Select Your Choice??", 
                "Choices", JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);
       
           if (n.equals("CreateUser"))
           {
           
   	
 
    String    userName  = JOptionPane.showInputDialog("Enter Your Name");
             String Pass = JOptionPane.showInputDialog("Enter Your Password");
      
           
           String sql = "insert into User (name,password)values ('"+userName+"','"+Pass+"')";
          try {
              myStm = conn.createStatement();

          if (myStm.executeUpdate(sql)==1) 
          {  JOptionPane.showMessageDialog(null, "Successfully Inserted ");
                     
           
              men1();
              
          }
          
          }
          
          catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Already\"Exists");
              men1();
          
          
   
          }
  
  
             
           }
    
      else    if (n.equals("Login"))
           {
      
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
           
            ClientGUI c= new  ClientGUI();
 
            c.setVisible(true);
       
           }


           else    if (n.equals("RunServer"))
           {
               Server server = new Server(5555);
               
		server.waitingForClients();
	
     
           }
             
    }
 

         
 

 
 
 
 
 
          
          
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
           
         men1();
        
    }
}
           
