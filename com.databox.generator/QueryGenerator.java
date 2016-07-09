/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;



/**
 *
 * @author Jois
 */
public class QueryGenerator {
   public static final String Insert = "INSERT INTO ";
   private Set<String> tableHeaders = null;
   private String query = null;
   private AssistantQueryGenerator assistant;
   
   public QueryGenerator(){
        assistant = new AssistantQueryGenerator();
   }
    
    public String getQuery(String TableName,Map values,String action){
          setRequiredValues(values);
          constructQuery(action,values,TableName);
          
          return query;
    }
    
    public PreparedStatement getQuery(String TableName, Map values,String action,Connection connection){
        setRequiredValues(values);
        PreparedStatement ps= null;
        constructPreparedQuery(action,values,TableName);
       try {
           ps = connection.prepareStatement(query);
           constructPreparedValues(values,ps);
           
           
       } catch (SQLException ex) {
           System.out.println(ex );
       }
        return ps;
    }
   
    
    
    private void constructQuery(String action,Map values,String TableName){
      
        
         switch(action){
        
             case Insert: query = action + TableName + " (";
                          query += assistant.getHeaders(values)+")"+" VALUES (";         
                          query += assistant.getValues(values) +")";
                          break;  
                          
         }
        
    
    }
    
   private void constructPreparedQuery(String action,Map values,String TableName){
       
       switch(action)
       {
           case Insert:query = action + TableName +" (";
                       query += assistant.getHeaders(values)+")"+" VALUES (";
                       query += assistant.getPreparedValues(values)+")";
                       
                       System.out.println(query);
                       break; 
       }
   }
    
    
   
    
   //Used to set values of a prepared statement
    private void constructPreparedValues(Map value,PreparedStatement ps) throws SQLException{
        int i = 1;
       
        for(String key:tableHeaders){
         
            ps.setObject(i,value.get(key));
          
            i++;
        }
    }
    
    public void setRequiredValues(Map values){
        assistant.setContent(values);
        tableHeaders = assistant.getTableHeaders();
    }
}
