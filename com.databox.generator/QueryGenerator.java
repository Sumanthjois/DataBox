/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




public class QueryGenerator {
   
   //instance variables 
   
  
   public static final String Insert = "INSERT INTO ";
   public static final String Select = "SELECT ";
   private Set<String> tableHeaders = null;
   private String action;
   private String TableName;
   private Map values;
   private String query = null;
   private Map<String,Object> selections = null;
   private AssistantQueryGenerator assistant;
   private Worker worker = null;
   
   public QueryGenerator(){
        assistant = new AssistantQueryGenerator();
   }
    
    //For inserting data into table
    public String getQuery(String TableName,Map values,String action){
          setRequiredValues(values,action,TableName);
          constructQuery();
          
          return query;
    }
    
    //For selecting data form table
    public String getQuery(String TableName,Set<String> values,String action){
        selections = new TreeMap<>();
        for(String tableHeaders:values){
           
            selections.put(tableHeaders, null);
           
           
        }
        setRequiredValues(selections,action,TableName);
        constructQuery();
        return query;
    }
    
    public PreparedStatement getQuery(String TableName, Map values,String action,Connection connection){
        setRequiredValues(values,action,TableName);
        PreparedStatement ps= null;
        constructPreparedQuery();
       try {
           ps = connection.prepareStatement(query);
           constructPreparedValues(ps);
           
           
       } catch (SQLException ex) {
           System.out.println(ex );
       }
        return ps;
    }
   
    
    
    private void constructQuery(){
      
        
         switch(action){
        
             case Insert: query =  worker.getInsertQuery(Worker.Normal);
                          break;  
             case Select: query = worker.getSelectQuery(Worker.SelectNormal);
                          
                          break;
                          
         }
        
    
    }
    
   private void constructPreparedQuery(){
       
       switch(action)
       {
           case Insert:query =  worker.getInsertQuery(Worker.Prepared);
                       System.out.println(query);
                       break; 
       }
   }
    
    
   
    
   //Used to set values of a prepared statement
    private void constructPreparedValues(PreparedStatement ps) throws SQLException{
        int i = 1;
       
        for(String key:tableHeaders){
         
            ps.setObject(i,values.get(key));
          
            i++;
        }
    }
    
   private void setRequiredValues(Map values,String action,String TableName){
        assistant.setContent(values);
        this.values = values;
        this.action = action;
        this.TableName = TableName;
        tableHeaders = assistant.getTableHeaders();
        worker = new Worker(assistant,values,action,TableName);
    }
    
    
   
   
    
}
