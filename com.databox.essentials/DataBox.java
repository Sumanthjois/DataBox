/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.essentials;

import com.databox.generator.QueryGenerator;
import com.databox.DataBase;
import com.databox.generator.Worker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class DataBox implements DataBase{
   
     QueryGenerator queryGenerator;
     Connection connection;
     private boolean result = false;
    
     public DataBox(String Driver,String DB_Url) {
         queryGenerator = new QueryGenerator();
         connection = Connector.getConnection(Driver, DB_Url);
         
     }
    
     public DataBox(String Driver,String DB_Url,String username,String password) {
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
    public int Delete(String TableName,String whereClause) {
        String query = queryGenerator.getQuery(TableName,whereClause,QueryGenerator.Delete);
        int result = 0; 
        try {
             Statement s = connection.createStatement();
              result = s.executeUpdate(query);
            
         } catch (SQLException ex) {
            System.out.println(ex);
         }
        
        return result;
    }

    @Override
    public int Update(String TableName,Map newValues, String whereClause) {
            int result  = 0;  
         try {
             String query = queryGenerator.getQuery(TableName, newValues, whereClause, QueryGenerator.Update);
             Statement statement = connection.createStatement();
             result =  statement.executeUpdate(query);
            
         } catch (SQLException ex) {
             System.out.println(ex);
         }
         return result;
    }

    public int preparedUpdate(String TableName,Map newValues,String whereClause,Set whereValues){
        int result = 0;
        PreparedStatement ps =  queryGenerator.getQuery(TableName,newValues,whereClause,whereValues,connection,QueryGenerator.Update);
         try {
             result = ps.executeUpdate();
         } catch (SQLException ex) {
             System.out.println(ex);
         }
       return result;
    }
    
    @Override
    public ResultSet Select(String TableName,Set<String> values) {
     String query =  queryGenerator.getQuery(TableName, values, QueryGenerator.Select);
     
     return getResultSet(query);
    }
    public ResultSet Select(String TableName,Set<String> values,String whereColumn,String whereValue,String operator){
        String query = queryGenerator.getQuery(TableName, values, QueryGenerator.SelectWithWhere,whereColumn,whereValue,operator);
        
        return getResultSet(query);
    }
    
    public ResultSet preparedSelect(String TableName,Set<String> values,String whereColumn,String whereValue,String operator){
       
      PreparedStatement ps =  queryGenerator.getQuery(TableName, values, QueryGenerator.SelectWithWhere,connection,whereColumn,whereValue,operator);
       
      return getResultSet(ps);
    }
    
    public ResultSet Select(String TableName,Set<String> values,String whereQuery){
      String query =  queryGenerator.getQuery(TableName, values, QueryGenerator.Select);
      if(whereQuery.charAt(0)!=' ' ){
          whereQuery =" "+whereQuery;
      }
      query+= whereQuery;
      return getResultSet(query);
    }
    
    public ResultSet preparedSelect(String TableName,Set<String> values,String whereQuery,List whereValues){
      String query =  queryGenerator.getQuery(TableName, values, QueryGenerator.Select);
      if(whereQuery.charAt(0)!=' ' ){
          whereQuery =" "+whereQuery;
      }
      query+= whereQuery;
      PreparedStatement ps = queryGenerator.getPreparedStatement(query,whereValues,connection);
        return getResultSet(ps);
    }
    
    
    public int preparedDelete(String TableName,String whereClause,List whereValues){
       int result = 0;
       String query =  QueryGenerator.Delete + TableName + " " + whereClause;
       PreparedStatement ps = queryGenerator.getPreparedStatement(query,whereValues,connection);
         try {
            int i =  ps.executeUpdate();
            
         } catch (SQLException ex) {
             System.out.println(ex);
         }
        return result;
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
    
    
    private ResultSet getResultSet(PreparedStatement ps){
         ResultSet rs = null;
        try {
          rs = ps.executeQuery();
         } catch (SQLException ex) {
            System.out.println(ex);
         }
      return rs;
    }
    
}
