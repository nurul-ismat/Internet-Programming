/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.*;

/**
 *
 * @author GATES
 */
public class JdbcUtility {
    
    Connection con = null;
    String driver;
    String url;
    String userName;
    String password;
    
    PreparedStatement psInsertStudent = null;
    PreparedStatement psSelectAllStudent = null;
    PreparedStatement psSelectStudentById = null;
    PreparedStatement psUpdateStudentById = null;  
    PreparedStatement psDeleteStudentById = null; 
    PreparedStatement psInsertUser = null; 
    PreparedStatement psSelectUserInfoByLogin = null;
   
    public JdbcUtility(String driver,
                       String url,
                       String userName,
                       String password) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    public  void jdbcConnect(){
        
        try {
         
            Class.forName (driver);
            con = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData dma = con.getMetaData ();
            System.out.println("\nConnected to " + dma.getURL());
            System.out.println("Driver       " + dma.getDriverName());
            System.out.println("Version      " + dma.getDriverVersion());
            System.out.println("");
            
	}
	catch (SQLException ex){
            
            while (ex != null){
		System.out.println ("SQLState: " + ex.getSQLState ());
                System.out.println ("Message:  " + ex.getMessage ());
		System.out.println ("Vendor:   " + ex.getErrorCode ());
                ex = ex.getNextException ();
		System.out.println ("");
            }

            System.out.println("Connection to the database error");
	}
	catch (java.lang.Exception ex){
            ex.printStackTrace ();
	}
    }
    
    public Connection jdbcGetConnection(){
        return con;
    }
    
    public void jdbcConClose(){
        
   	try {
         con.close();
   	}
   	catch (Exception ex){
            ex.printStackTrace ();
        }
        
    }
    
    public void prepareSQLStatementInsertStudent(){
        
        try {
           
            //create SQL statement
            String sqlInsertStudent = "INSERT INTO students(name, matriks, age, addeddate)" +
                                      " VALUES(?, ?, ?, NOW())";            
            
            //prepare statement
            psInsertStudent = con.prepareStatement(sqlInsertStudent);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
    
    public PreparedStatement getPsInsertStudent(){
        return psInsertStudent;
    }
    
    public void prepareSQLStatementSelectAllStudent(){
        
        try {
           
            //create SQL statement
            String sqlSelectAllStudent = "SELECT * FROM students";            
            
            //prepare statement
            psSelectAllStudent = con.prepareStatement(sqlSelectAllStudent);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
    
    public PreparedStatement getPsSelectAllStudent(){
        return psSelectAllStudent;
    }
    
    public void prepareSQLStatementSelectStudentById(){
        
        try {
           
            //create SQL statement
            String sqlSelectStudentById = "SELECT *" + 
                                          " FROM students" +
                                          " WHERE id = ?";            
            
            //prepare statement
            psSelectStudentById = con.prepareStatement(sqlSelectStudentById);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
    
    public PreparedStatement getPsSelectStudentById(){
        return psSelectStudentById;
    }
    
    public void prepareSQLStatementUpdateStudentById(){
        
        try {
           
            //create SQL statement
            String sqlUpdateStudentById = "UPDATE students" +
                                          " set name = ?," +
                                          " age = ?" +
                                          " WHERE id = ?";            
            
            //prepare statement
            psUpdateStudentById = con.prepareStatement(sqlUpdateStudentById);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }

    public PreparedStatement getPsUpdateStudentById(){
        return psUpdateStudentById;
    }    
    
    //psDeleteStudentById
    public void prepareSQLStatementDeleteStudentById(){
        
        try {
           
            //create SQL statement
            String sqlDeleteStudentById = "DELETE FROM students" +
                                          " WHERE id = ?";            
            
            //prepare statement
            psDeleteStudentById = con.prepareStatement(sqlDeleteStudentById);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    } 
    
    public PreparedStatement getPsDeleteStudentById(){
        return psDeleteStudentById;
    } 
    
    //psInsertUser
    public void prepareSQLStatementInsertUser(){
        
        try {
           
            //create SQL statement                                 //passwordhash
            String sqlInsertUser = "INSERT INTO users(name, login, password, salt, addeddate)" +
                                   " VALUES(?, ?, ?, ?, NOW())";            
            
            //prepare statement
            psInsertUser = con.prepareStatement(sqlInsertUser);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    } 

    public PreparedStatement getPsInsertUser(){
        return psInsertUser;
    }   
    
    //PreparedStatement psSelectUserInfoByLogin = null;
    public void prepareSQLStatementSelectUserInfoByLogin(){
        
        try {
           
            //create SQL statement
            String sqlSelectStudentById = "SELECT *" + 
                                          " FROM users" +
                                          " WHERE login = ?";            
            
            //prepare statement
            psSelectUserInfoByLogin = con.prepareStatement(sqlSelectStudentById);            
        }
        catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
    
    public PreparedStatement getPsSelectUserInfoByLogin(){
        return psSelectUserInfoByLogin;
    }    
}
