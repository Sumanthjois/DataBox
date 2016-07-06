package com.databox;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


public interface DataBase {
    public boolean Insert(String TableName,Map values);
    public boolean Delete(String TableName,Map values);
    public boolean Update(String TableName,Map newValues,Map whereValues);
    public ResultSet Select(String TableName,List<String> values);
}
