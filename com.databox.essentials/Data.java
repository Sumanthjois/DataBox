/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.essentials;

import com.databox.DataBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jois
 */
public class Data implements DataBase{
   
     QueryGenerator queryGenerator;
     Connection connection;
    
     public Data(String Driver,String DB_Url) {
         queryGenerator = new QueryGenerator();
         connection = Connector.getConnection(Driver, DB_Url);
         
     }
    
     public Data(String Driver,String DB_Url,String username,String password) {
         queryGenerator = new QueryGenerator();
         connection = Connector.getConnection(Driver, DB_Url, username, password);
         
     } 
    
    @Override
    public boolean Insert(String TableName,Map values) {
          
        
        String query = queryGenerator.getQuery(TableName,values,QueryGenerator.Insert);
         try {
             Statement s = connection.createStatement();
             s.executeUpdate(query);
         } catch (SQLException ex) {
           System.out.println(ex);
         }
        
         System.out.println(query);
        return true;
    }

    @Override
    public boolean Delete(String TableName,Map values) {

        
        return true;
    }

    @Override
    public boolean Update(String TableName,Map newValues, Map whereValues) {
  
        return true;
    }

    @Override
    public ResultSet Select(String TableName,List<String> values) {
    
      return null;
    }
    
}
