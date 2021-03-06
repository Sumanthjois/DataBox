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



/**
 *
 * @author Jois
 */
public class QueryGenerator {
   
   //instance variables 
   public static final String Update = "UPDATE "; 
   public static final String Delete = "DELETE FROM ";
   public static final String Insert = "INSERT INTO ";
   public static final String Select = "SELECT ";
   public static final String SelectWithWhere = "Select ";
   private Set<String> tableHeaders = null;
   private String action;
   private String TableName;
   private Map values;
   private String query = null;
   private Map<String,Object> selections = null;
   private AssistantQueryGenerator assistant;
   private Worker worker = null;
   private String whereColumn = null;
   private String whereValue = null;
   
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
        selections =  Worker.toMap(values);
        setRequiredValues(selections,action,TableName);
        constructQuery();
        return query;
    }
    
     public String getQuery(String TableName,String whereClause,String action ){
       setRequiredValues(TableName,whereClause,action);
        constructQuery();
        return query;
    }
    
    public String getQuery(String TableName,Set<String> values,String action,String whereColumn,String whereValue,String operator){
      selections = Worker.toMap(values);
      setRequiredValues(selections,action,TableName,whereColumn,whereValue,operator);
      constructQuery();
      
      return query;
    }
    
    
    public String getQuery(String TableName,Map newValues,String whereClause,String action){
        
        setRequiredValues(TableName,newValues,action,whereClause);
        
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
   
    public PreparedStatement getQuery(String TableName,Set values,String action,Connection connection,String whereColumn,String whereValue,String operator){
        selections = Worker.toMap(values);
        setRequiredValues(selections,action,TableName,whereColumn,whereValue,operator);
        PreparedStatement ps = null;
        constructPreparedQuery();
        try{
            ps = connection.prepareStatement(query);
            ps.setObject(1, whereValue);
        }catch(SQLException ex){
            
        }
        return ps;
    }
   
    public PreparedStatement getQuery(String TableName,Map newValues,String whereClause,Set<String> whereValues,Connection connection,String action){
        int i = 1;
        setRequiredValues(TableName,newValues,action,whereClause);
        constructPreparedQuery();
        PreparedStatement ps = null;
       try {
           ps = connection.prepareStatement(query);
           for(String tableHeaders : tableHeaders){
               ps.setObject(i, newValues.get(tableHeaders));
               i++;
           }
           for(String whereValue : whereValues){
               ps.setObject(i, whereValue);
                 i++;
              }
       } catch (SQLException ex) {
           System.out.println(ex);
       }
        return ps;
    } 
    
    private void constructQuery(){
      
        
         switch(action){
        
             case Insert:          query =  worker.getInsertQuery(Worker.Normal);
                                   break;  
             case Select:          query = worker.getSelectQuery(Worker.SelectNormal);
                                   break;
             case SelectWithWhere: query = worker.getSelectQuery(Worker.SelectWhere);
                                   break;  
             case Delete:          query = worker.getDeleteQuery(Worker.DeleteNormal);
                                   break;                      
             case Update:          query = worker.getUpdateQuery(Worker.UpdateNormal);
                                   break;                           
         }
        
    
    }
    
   private void constructPreparedQuery(){
       
       switch(action)
       {
           case Insert         : query = worker.getInsertQuery(Worker.Prepared);
                                 break; 
           
           case SelectWithWhere: query = worker.getSelectQuery(Worker.SelectPrepared); 
                                 break;   
           
           case Update         : query = worker.getUpdateQuery(Worker.UpdatePrepared);
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
    
    private void setRequiredValues(Map values,String action,String TableName,String whereColumn ,String whereValue,String operator){
        assistant.setContent(values);
        this.values = values;
        this.action = action;
        this.TableName = TableName;
        tableHeaders = assistant.getTableHeaders();
        worker = new Worker(assistant,values,action,TableName,whereColumn,whereValue,operator);
    }
    
    private void setRequiredValues(String TableName,String whereClause,String action){
        this.action = action;
        this.TableName = TableName;
        worker = new Worker(TableName,whereClause);
    }
   
    private void setRequiredValues(String TableName,Map newValues,String action,String whereValues){
        assistant.setContent(newValues);
        this.action = action;
        this.TableName = TableName;
        tableHeaders = assistant.getTableHeaders();
        worker = new Worker(TableName,assistant,newValues,whereValues); 
        
    }
    
   public PreparedStatement getPreparedStatement(String query,List<String> whereValues,Connection connection){
       return Worker.setPreparedValues(query, whereValues,connection);
   }
    
}
