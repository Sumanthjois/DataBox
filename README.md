# DataBox
Databox is just an Api for helping all those people who are fed up of writing tons of JDBC code.

# What is DataBox?
Fed up of writning tons of code for just updating or inserting a small data into your database? Well DataBox is a Api which helps you do all jdbc operations with using really less lines of code. It mainly reduces the boilerplate code.

# What will you need to know to contribute?
You will have to know java and have a idea about jdbc. I think thats good enough!

# Just an example on how this Api works!
  In jdbc if you want to insert a data into a table using prepared statement..It's really lots of code! For example it may look like this:
   ```
               Connection connection = DriverManager.getConnection(DB_URL);
               PreparedStatement statement = connection.prepareStatement(query);
               statement.setObject(setValue);
               ..........
               ..........
               statement.execute();
  ```
**Using DataBox this whole bunch of code can be reduced to one line. For example:**
  ```
              DataBox data = new DataBox(Driver,Database_Url);
              data.preparedInsert(TableName,values);
  ```
 
* Where:
  * DataBox: is the class which will help you to do all type of interactions with the DataBase.
  * Database_Url: is the url of the Database you want to connect to.
  * TableName: is the table you want to insert data into.
  * values: is of type Map. For example:
                               
   ```
                               Map values = new TreeMap();
                               values.put(ColumnName,Value);
                               values.put(ColumnName,Value);
                               ........
                               ........
  ```
                               
# Note:
    For now we will only providing api for performing all the basic operations regarding JDBC.We wil provide api for more complicated operations (For example: mixing up insert and select) on request.
    I hope to complete this as soon as possible. The basic and essential operations required for devlopers will be completed soon.
    If you can help please do. If you have any queries you can contact me here:  joissumanth@gmail.com
            
