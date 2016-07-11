/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.generator;

import java.util.Map;


public class Worker {
    
    public static final String Prepared = "PreparedStatement";
    public static final String Normal = "NormalStatement"; 
    public static final String SelectNormal = "NormalSelectStatement";
    private Map values;
    private String action;
    private String TableName;
    private AssistantQueryGenerator assistant;
    
     public Worker(AssistantQueryGenerator assistant,Map values,String action,String TableName){
         this.action = action;
         this.assistant = assistant;
         this.values = values;
         this.TableName = TableName;
      }
   
       //get query for Insert 
       public String getInsertQuery(String statementType){
        String preparedValues = null;
        if(statementType.equals(Prepared)){
            preparedValues = assistant.getPreparedValues(values);
        }else{
            preparedValues = assistant.getValues(values);
        }
        return action + TableName +" (" +assistant.getHeaders(values,statementType)+")"+" VALUES ("+preparedValues+")";
     }
    
       
       
       //get query for Select 
       public String getSelectQuery(String statementType){
           
           return action + assistant.getHeaders(values,statementType) + " FROM " + TableName;
       }
              
         
       
}
