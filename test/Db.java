
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import config.DbConfig;

public class Db {

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	int rs_wirte = 0;
	static Map<String, String> dbconf = DbConfig.getconf();

	private Db() {

	}

	private static Db instacnedb = new Db();

	public static Db instance() {
		return instacnedb;

	}

	public Statement connect() {
		try {

			Class.forName(dbconf.get("JDBC_DRIVER"));
			this.conn = DriverManager.getConnection(dbconf.get("DB_URL"), dbconf.get("USER"), dbconf.get("PASS"));
			this.stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.stmt;
	}

	public void close() {

		try {
			if (this.stmt != null)
				this.stmt.close();
		} catch (SQLException se2) {
		}
		try {
			if (this.conn != null)
				this.conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	public void closeResultSet() {

		try {
			if (this.rs != null)
				this.rs.close();
		} catch (SQLException se2) {
		}

	}

	public static Map<String, String> getfirstrow(String sqls, ArrayList<String> field) {
		Map<String, String> result = new HashMap<String, String>();
		Db db = instance();
		try {
			db.stmt = db.connect();
			db.rs = db.stmt.executeQuery(sqls);
			db.rs.next();
			for (String ele : field) {
				result.put(ele, db.rs.getString(ele));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			db.closeResultSet();
			db.close();
		}

		return result;

	}

	public static int wirte(String sqls) {

		Db db = instance();
		try {
			db.stmt = db.connect();
			db.rs_wirte = db.stmt.executeUpdate(sqls);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			db.close();
		}

		return db.rs_wirte;

	}

}
