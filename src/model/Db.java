package model;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import  config.DbConfig;




public class Db{
	
	  Connection conn = null;
	  Statement stmt = null;
	  ResultSet rs=null;
	  static  Map<String, String> dbconf=DbConfig.getconf();
	  
	  private Db(){
		   
	   }
	   
	  private static Db instacnedb=new Db();
	  
	  public static  Db instance(){
		   return instacnedb;
		   
	  }
	   
	  
	    
	    public  Statement connect(){
	    	try{
	             // 注册 JDBC 驱动
	             Class.forName(dbconf.get("JDBC_DRIVER"));
	    		
	             // 打开链接
	             System.out.println("连接数据库...");
	             


	             this.conn = DriverManager.getConnection(dbconf.get("DB_URL"),dbconf.get("USER"),dbconf.get("PASS"));
	         
	             // 执行查询
	             System.out.println(" 实例化Statement对象...");
	             this.stmt = conn.createStatement();
	             
	         }catch(Exception e){
	             // 处理 Class.forName 错误
	             e.printStackTrace();
	    	
	    }
			return this.stmt;
	    }
	    
	    public  void close(){
	    	
	    	 try{
                 if(this.stmt!=null) this.stmt.close();
             }catch(SQLException se2){
             }// 什么都不做
             try{
                 if(this.conn!=null) this.conn.close();
             }catch(SQLException se){
                 se.printStackTrace();
             }
	    	
	    }
	    
	    public void closeResultSet(){
	    	
	    	try{
                if(this.rs!=null) this.rs.close();
            }catch(SQLException se2){
            }
	    	
	    }
	    
	    
	    public  ResultSet  get(String sqls){
	    	
	    	try{
	    	
	    	     Statement stmt =instance().connect();
	             ResultSet rs = stmt.executeQuery(sqls);
	             return  rs;
	        
	        }catch(SQLException se){
            // 处理 JDBC 错误
                se.printStackTrace();
	        }
			return null;
	    	
}
       public  int  wirte(String sqls){
	    	
	    	try{
	    	
	    	     Statement stmt =instance().connect();
	    	     int rs=0;
	    	     rs = stmt.executeUpdate(sqls);
	             return  rs;
	        
	        }catch(SQLException se){
            // 处理 JDBC 错误
                se.printStackTrace();
	        }
			return -1;
	    	
}
	    
	    
	    
}
