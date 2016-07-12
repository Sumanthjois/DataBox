/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.generator;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Jois
 */
public class Worker {
    
    public static final String Prepared = "PreparedStatement";
    public static final String Normal = "NormalStatement"; 
    public static final String SelectNormal = "NormalSelectStatement";
    public static final String SelectWhere = "SelectWithWhere";
    private Map values;
    private String action;
    private String TableName;
    private AssistantQueryGenerator assistant;
    private String whereColumn = null;
    private String whereValue = null;
    private String operator = null;
    
     public Worker(AssistantQueryGenerator assistant,Map values,String action,String TableName){
         this.action = action;
         this.assistant = assistant;
         this.values = values;
         this.TableName = TableName;
      }
     
     public Worker(AssistantQueryGenerator assistant,Map values,String action,String TableName,String whereColumn,String whereValue,String operator){
         this.action = action;
         this.assistant = assistant;
         this.values = values;
         this.TableName = TableName;
         this.whereColumn = whereColumn;
         this.whereValue = whereValue;
         this.operator = operator;
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
           String query = action + assistant.getHeaders(values,statementType) + " FROM " + TableName;
           
           if(statementType.equals(SelectWhere)){
               query+=" WHERE "+whereColumn+" "+operator+" '"+whereValue+"'" ;
             query =   query.replace("Select", "SELECT");
           }
           return query;
       
       }
           
     
         
       public static Map toMap(Set<String> set){
           Map selections = new TreeMap();
            for(String tableHeaders:set){
              selections.put(tableHeaders, null);
            }
           return selections;
       }
       
}
