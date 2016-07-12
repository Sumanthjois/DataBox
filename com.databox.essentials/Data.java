/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.essentials;

import com.databox.generator.QueryGenerator;
import com.databox.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;




public class Data implements DataBase{
   
     QueryGenerator queryGenerator;
     Connection connection;
     private boolean result = false;
    
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
          
        result = false;
        String query = queryGenerator.getQuery(TableName,values,QueryGenerator.Insert);
         try {
             Statement s = connection.createStatement();
             s.executeUpdate(query);
             result = true;
         } catch (SQLException ex) {
           System.out.println(ex);
         }
        
         System.out.println(query);
        return result;
    }
    
    public boolean preparedInsert(String TableName,Map values){
        
      PreparedStatement ps =   queryGenerator.getQuery(TableName, values, QueryGenerator.Insert, connection);
      result = false;
         
         try {
             ps.execute();
             result  = true;
         } catch (SQLException ex) {
            System.out.println(ex);
         }
      
      return  result;
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
    public ResultSet Select(String TableName,Set<String> values) {
     String query =  queryGenerator.getQuery(TableName, values, QueryGenerator.Select);
     
     return getResultSet(query);
    }
    public ResultSet Select(String TableName,Set<String>values,String whereColumn,String whereValue,String operator){
        String query = queryGenerator.getQuery(TableName, values, QueryGenerator.SelectWithWhere,whereColumn,whereValue,operator);
        
        return getResultSet(query);
    }
    
    private ResultSet getResultSet(String query){
         ResultSet result = null;
         try {
             Statement statement = connection.createStatement();
             result = statement.executeQuery(query);
         } catch (SQLException ex) {
             System.out.println(ex);
         }
        
        return result;
    }
}
