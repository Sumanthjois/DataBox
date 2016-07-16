/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databox;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jois
 */
public interface DataBase {
    public boolean Insert(String TableName,Map values);
    public int Delete(String TableName,String whereClause);
    public boolean Update(String TableName,Map newValues,Map whereValues);
    public ResultSet Select(String TableName,Set<String> values);
}
