
package com.databox.essentials;

import java.util.Map;
import java.util.Set;



public class QueryGenerator {
   public static final String Insert = "INSERT INTO ";
   private Set<String> tableHeaders = null;
   private String query = null;
   private final String comma =",";
   private final String space = " ";
   private final String blank = "";
    
    public String getQuery(String TableName,Map values,String action){
          constructQuery(action,values,TableName);
          return query;
    }
    
   
    private void constructQuery(String action,Map values,String TableName){
      
        
         switch(action){
        
             case Insert: query = action + TableName + " (";
                          query += getHeaders(values)+")"+" VALUES (";         
                          query += getValues(values) +")";
                          break;  
                          
         }
        
    
    }
    
    
    //get Table headers
    private String getHeaders(Map values){
        
        String header = blank;
              
        tableHeaders = values.keySet();
        int length = tableHeaders.size()-1;
        int i=0;
        
        for(String keys : tableHeaders){
            if(i == length){
                header +=space+keys;
            } 
            else if(i==0){
                header +=keys+comma;
            }
            else{
                header +=space+keys+comma;
            }
            i++;
        }
        
         return header;
    }
    
    //get data to insert
    private String getValues(Map value){
      
        String values = blank;
        
        int length = tableHeaders.size() - 1;
        int i=0;
        
        for(String keys:tableHeaders){
            
            if(i == length){
                 values+= space+value.get(keys);
            }
            else if(i==0){
                 values+= value.get(keys)+comma;
            }
            else{
                 values+= space+value.get(keys)+comma;
            }
           
            
            i++;
        }
        
        return values;
    }
    
}
