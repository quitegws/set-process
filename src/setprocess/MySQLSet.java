package setprocess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

public class MySQLSet {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		insertSets(SetUtils.generateSets(2, 1000000, 10000,256));
		long endTime = System.currentTimeMillis();
		System.out.println("insertSet time is " + (endTime - startTime) + " ms");

	}
	
	public static void insertSets(Set[] sets){
		Connection conn = getConnection();
		
		// statement用来执行SQL语句
		PreparedStatement psts = null;
		PreparedStatement psts2 = null;
		try {
			String sql = "insert into ipaddress (ip) values (?) ;";
			String sql2 = "insert into ipaddress2 (ip) values (?) ;";
			int count = 0;
			int count2 =0;
			psts = conn.prepareStatement(sql); 
			psts2 = conn.prepareStatement(sql2); 
			conn.setAutoCommit(false); // 设置手动提交  
			
			Set<?> set = sets[0];
			Iterator<?> iter = set.iterator();
			while (iter.hasNext()) {
				count++;
				String ip = (String) iter.next();
				psts.setString(1, ip);
				psts.addBatch();          // 加入批量处理 
				if (count % 10000 == 0) {
					psts.executeBatch();
					conn.commit();
					System.out.println("insert 10000 ips into " + " table ipaddress");
				}
			}
			
			Set<?> set2 = sets[1];
			Iterator<?> iter2 = set2.iterator();
			while (iter2.hasNext()) {
				count2++;
				String ip = (String) iter2.next();
				psts2.setString(1, ip);
				psts2.addBatch();          // 加入批量处理 
				if (count2 % 10000 == 0) {
					psts2.executeBatch();
					conn.commit();
					System.out.println("insert 10000 ips into " + " table ipaddress2");
				}
			}
			psts.executeBatch(); // 执行批量处理 
			psts2.executeBatch(); // 执行批量处理 
	        conn.commit();  // 提交 
	        
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
			if (psts != null ) {
				try {
					psts.close();
				} catch (SQLException e) {
				}
			}
			if (psts2 != null ) {
				try {
					psts.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	
	public static Connection getConnection(){
		// 驱动程序名
//        String driver = "com.mysql.cj.jdbc.Driver";

        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/test";

        // MySQL配置时的用户名
        String user = "gws"; 

        // MySQL配置时的密码
        String password = "123456";

        try { 

	         Connection conn = DriverManager.getConnection(url, user, password);
	
	         if(!conn.isClosed()) 
	        	 System.out.println("return connection done!");

	         return conn;

        }catch(Exception e) {
         e.printStackTrace();
        }
        
        return null;
	}

	
	
}
