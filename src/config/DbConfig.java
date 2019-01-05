package config;

import java.util.HashMap;
import java.util.Map;

public class DbConfig {
	public final static Map<String, String> db = new HashMap<String, String>();

	public final static Map<String, String> getconf() {
		db.put("JDBC_DRIVER", "com.mysql.jdbc.Driver");
		db.put("DB_URL", "jdbc:mysql://localhost:32769/project?autoReconnect=true&useSSL=false");
		db.put("USER", "root");
		db.put("PASS", "tmhrush2233");

		return db;

	}

}
