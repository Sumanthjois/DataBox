
package com.databox.essentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
   
    
    public static Connection getConnection(String Driver,String DB_Url) {
      
        Connection connection = null;
        
        try{
              registerDriver(Driver);
              connection = DriverManager.getConnection(DB_Url);
              
        }catch(ClassNotFoundException | SQLException exception){
            System.out.println(exception);
        }
      
        return connection;
    }
   
   public static Connection getConnection(String Driver,String DB_Url,String username,String password) {
         
       Connection connection = null;
       
        try{
              registerDriver(Driver);
              connection = DriverManager.getConnection(DB_Url,username,password);
              
        }catch(ClassNotFoundException | SQLException exception){
            System.out.println(exception);
        }

         
         return connection;
    }
   
   
   
   private  static void registerDriver(String Driver) throws ClassNotFoundException {
       Class.forName(Driver);
   }
}
