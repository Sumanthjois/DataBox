/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox.generator;

import java.util.Map;
import java.util.Set;


public class AssistantQueryGenerator {
   
   private Set<String> tableHeaders = null; 
   private final String PREPARED_VALUE = "?";
   private final String COMMA =",";
   private final String SPACE = " ";
   private final String BLANK = "";
   private final String SINGLE_QUOTE = "'";
   private Map content = null;
   private int No_Of_Columns;

    public Map getContent() {
        return content;
    }

    public void setContent(Map content) {
        this.content = content;
        setTableHeaders();
    }
   
   
   
     //get Table headers
    public String getHeaders(Map values){
        
        String header = BLANK;
              
        
       
        int i=0;
        
        for(String keys : tableHeaders){
            if(i == No_Of_Columns){
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
    public String getValues(Map value){
      
        String values = BLANK;
        
       
        int i=0;
        
        for(String keys:tableHeaders){
            
            
            if(i == No_Of_Columns){
                 values+= SPACE+singleQuote((String) value.get(keys));
            }
            else if(i==0){
                 values+= singleQuote((String) value.get(keys))+COMMA;
            }
            else{
                 values+= SPACE+singleQuote((String) value.get(keys))+COMMA;
            }
           
            
            i++;
        }
        
        return values;
    }
    
    
    //method for creating question Marks
    public String getPreparedValues(Map value){
        String values = BLANK;
       
     
        for(int i= 0; i<= No_Of_Columns ; i++){
            if(i == No_Of_Columns){
                values+=PREPARED_VALUE;
            }else{
                values+=PREPARED_VALUE+COMMA;
            }
          
        }
        
        return values;
    }
   
    private String singleQuote(String insideQuote){
        if(insideQuote == null){
            return insideQuote;
        }else{
            return SINGLE_QUOTE+insideQuote+SINGLE_QUOTE;
        }
        
        
    }
    
    
    
    public Set<String> getTableHeaders(){
     
        return tableHeaders ;
    }
    
    public void setTableHeaders(){
        tableHeaders = content.keySet();
        No_Of_Columns = tableHeaders.size() - 1 ;
    }
}
