package search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Customer {
	
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Scanner sc = new Scanner(System.in);
    private String repInput;
    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
        	// The MySQL driver is a JAR file that must be in the Build Path
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/Clients?user=root&password=password");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query



            // PreparedStatements can use variables and are more efficient
            // The primary reason to use prepared statements: More Secure!
            // Secondary reason: you don't have to worry about quotes in your strings!
            
            /*       select statement      */
            String last = "";
            repInput="1";
            while(repInput.equals("1")){
            System.out.println("Enter last name: ");
            last = sc.nextLine();
            preparedStatement = connect.prepareStatement("SELECT * from Clients.customers where LastName= ?");
            preparedStatement.setString(1, last);
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);
            System.out.println("Enter 1 to search again or 2 to modify address:");
            repInput=sc.nextLine();
           
            /*       select statement      */
           
            }
            
            
            if (repInput.equals("2")){
                /*   update statement     */
                preparedStatement=connect.prepareStatement("update Clients.customers SET StreetAddress=?, City=?, State=?, ZipCode=?  WHERE LastName=?");
                System.out.println("Enter the new street Address:");
                String streetAddress = sc.nextLine();
                System.out.println("Enter the new City:");
                String city = sc.nextLine();
                System.out.println("Enter the new State:");
                String state = sc.nextLine();
                System.out.println("Enter the new ZipCode:");
                String zipCode = sc.nextLine();
                
                preparedStatement.setString(1, streetAddress);
                preparedStatement.setString(2, city);
                preparedStatement.setString(3, state);
                preparedStatement.setString(4, zipCode);
                preparedStatement.setString(5, last);
                preparedStatement.executeUpdate();
                preparedStatement=connect.prepareStatement("select * from Clients.customers where LastName=?");
                preparedStatement.setString(1, last);
                preparedStatement.executeQuery();}
            else{preparedStatement=connect.prepareStatement("select * from Clients.customers");
            		preparedStatement.executeQuery();}
            
            
            /*   update statement      */
            


            
            
            
            /*     insert statement*/  
            preparedStatement = connect
                    .prepareStatement("insert into  Clients.customers values (?, ?, ?, ? , ?, ?,?,?,?,?, ?,default)");
            // "myuser, webpage, , summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "b");
            preparedStatement.setString(3, "c");
            preparedStatement.setString(4, "d");
            preparedStatement.setString(5, "e");
            preparedStatement.setString(6, "f");
            preparedStatement.setString(7, "g");
            preparedStatement.setInt(8, 1);
            preparedStatement.setString(9, "i");
            preparedStatement.setString(10, "j");
            preparedStatement.setString(11, "k");
            preparedStatement.executeUpdate();
            
            /*     insert statement*/  
            


            /*  // Remove again the insert comment
            preparedStatement = connect.prepareStatement("delete from Clients.customers where FullName= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement.executeQuery("select * from Clients.customers");
           writeMetaData(resultSet);*/
            
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

   /* private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

       // System.out.println("The columns in the table are: ");

        //System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }*/

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String fullname = resultSet.getString("FullName");
            String title = resultSet.getString("Title");
            String firstname = resultSet.getString("FirstName");
            String lastname = resultSet.getString("LastName");
            String streetaddress = resultSet.getString("StreetAddress");
            String city = resultSet.getString("City");
            String state = resultSet.getString("State");
            String zipCode = resultSet.getString("ZipCode");
            String emailAddress = resultSet.getString("EmailAddress");
            String position = resultSet.getString("Position");
            String company = resultSet.getString("Company");
            String id = resultSet.getString("id");

            System.out.println("Customer Number:"+id );
            System.out.println(title+ " " +fullname );
            System.out.println(streetaddress);
            System.out.println(city +", "+ state+ " "+zipCode);
            System.out.println(emailAddress);
            System.out.println(position +" at "+ company);
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
