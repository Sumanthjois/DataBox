/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.essentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jois
 */
public class QueryGenerator {
   public static final String Insert = "INSERT INTO ";
   private Set<String> tableHeaders = null;
   private String query = null;
   private final String PREPARED_VALUE = "?";
   private final String COMMA =",";
   private final String SPACE = " ";
   private final String BLANK = "";
    
    public String getQuery(String TableName,Map values,String action){
          constructQuery(action,values,TableName);
          return query;
    }
    
    public PreparedStatement getQuery(String TableName, Map values,String action,Connection connection){
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
                          query += getHeaders(values)+")"+" VALUES (";         
                          query += getValues(values) +")";
                          break;  
                          
         }
        
    
    }
    
   private void constructPreparedQuery(String action,Map values,String TableName){
       
       switch(action)
       {
           case Insert:query = action + TableName +" (";
                       query += getHeaders(values)+")"+" VALUES (";
                       query += getPreparedValues(values)+")";
                       
                       System.out.println(query);
                       break; 
       }
   }
    
    //get Table headers
    private String getHeaders(Map values){
        
        String header = BLANK;
              
        tableHeaders = values.keySet();
        int length = tableHeaders.size()-1;
        int i=0;
        
        for(String keys : tableHeaders){
            if(i == length){
                header +=SPACE+keys;
            } 
            else if(i==0){
                header +=keys+COMMA;
            }
            else{
                header +=SPACE+keys+COMMA;
            }
            i++;
        }
        
         return header;
    }
    
    //get data to insert
    private String getValues(Map value){
      
        String values = BLANK;
        
        int length = tableHeaders.size() - 1;
        int i=0;
        
        for(String keys:tableHeaders){
            
            if(i == length){
                 values+= SPACE+value.get(keys);
            }
            else if(i==0){
                 values+= value.get(keys)+COMMA;
            }
            else{
                 values+= SPACE+value.get(keys)+COMMA;
            }
           
            
            i++;
        }
        
        return values;
    }
    
    //method for creating question Marks
    private String getPreparedValues(Map value){
        String values = BLANK;
        int length = tableHeaders.size() - 1 ;
     
        for(int i= 0; i<= length ; i++){
            if(i == length){
                values+=PREPARED_VALUE;
            }else{
                values+=PREPARED_VALUE+COMMA;
            }
          
        }
        
        return values;
    }
    
   //Used to set values of a prepared statement
    private void constructPreparedValues(Map value,PreparedStatement ps) throws SQLException{
        int i = 1;
       
        for(String key:tableHeaders){
         
            ps.setObject(i,value.get(key));
          
            i++;
        }
    }
}
