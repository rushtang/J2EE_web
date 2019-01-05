package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DbModel {

	public static Db db = Db.instance();

	public static void insert(String tablename, Map<String, String> field) throws IllegalArgumentException {
		if (field.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			String keys = "";
			String values = "";

			for (Map.Entry<String, String> entry : field.entrySet()) {
				String key = String.format("`%s`", entry.getKey());
				String value = String.format("\"%s\"", entry.getValue());
				keys += key + " ,";
				values += value + " ,";
			}

			keys = keys.substring(0, keys.length() - 1);
			values = values.substring(0, values.length() - 1);

			String sqls = String.format("insert into %s (%s) values ( %s);", tablename, keys, values);
			System.out.println(sqls);

			db.wirte(sqls);

		}

	}

	public static void update(String tablename, Map<String, String> cond, Map<String, String> param)
			throws IllegalArgumentException {
		if (cond.isEmpty() || param.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			String conds = "";
			String params = "";

			for (Map.Entry<String, String> entry : cond.entrySet()) {
				String key = String.format("`%s`", entry.getKey());
				String value = String.format("\"%s\"", entry.getValue());

				conds += key + "=" + value + ",";
			}
			for (Map.Entry<String, String> entry : param.entrySet()) {
				String key = String.format("`%s`", entry.getKey());
				String value = String.format("\"%s\"", entry.getValue());

				params += key + "=" + value + ",";
			}

			conds = conds.substring(0, conds.length() - 1);
			params = params.substring(0, params.length() - 1);

			String sqls = String.format("update %s set %s where %s ;", tablename, params, conds);
			System.out.println(sqls);

			db.wirte(sqls);

		}

	}

	public static void del(String tablename, Map<String, String> cond) throws IllegalArgumentException {
		if (cond.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			String conds = "";

			for (Map.Entry<String, String> entry : cond.entrySet()) {
				String key = String.format("`%s`", entry.getKey());
				String value = String.format("\"%s\"", entry.getValue());
				conds += key + "=" + value + ",";
			}

			conds = conds.substring(0, conds.length() - 1);

			String sqls = String.format("DELETE from %s where %s;", tablename, conds);
			System.out.println(sqls);

			db.wirte(sqls);

		}

	}

	public class Query {
		String sqls = "";
		Vector<String> field = new Vector<String>();

		public Query select(Vector<String> field) throws IllegalArgumentException {
			this.field = field;
			if (field.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
				String fields = "";

				for (String ele : field) {
					String item = String.format("`%s`", ele);

					fields += item + " ,";
				}

				fields = fields.substring(0, fields.length() - 1);

				sqls = String.format("select  %s ", fields);

			}
			return this;
		}

		public Query from(String tablename) throws IllegalArgumentException {

			if (tablename.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
				sqls += String.format(" from  %s ", tablename);

			}
			return this;
		}

		public Query where(Map<String, String> cond) throws IllegalArgumentException {

			if (cond.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
				String conds = "";

				for (Map.Entry<String, String> entry : cond.entrySet()) {
					String key = String.format("`%s`", entry.getKey());
					String value = String.format("\"%s\"", entry.getValue());

					conds += key + "=" + value + ",";
				}
				conds = conds.substring(0, conds.length() - 1);

				sqls += String.format(" where %s ", conds);

			}
			return this;
		}

		public Query orderby(Vector<String> order, Boolean is_desc) {
			if (order.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
				String orders = "";

				for (String ele : order) {
					String value = String.format("`%s`", ele);

					orders += value + ",";
				}
				orders = orders.substring(0, orders.length() - 1);

				if (is_desc) {
					sqls += String.format(" ORDER BY %s  desc", orders);
				} else {
					sqls += String.format(" ORDER BY %s", orders);
				}

			}
			return this;
		}

		@SuppressWarnings("null")
		public Vector<Map<String, String>> all() throws SQLException {

			System.out.println(sqls);

			ResultSet re = db.get(sqls);

			Vector<Map<String, String>> data = new Vector<Map<String, String>>();
			while (re.next()) {
				Map<String, String> tmpmap = new HashMap<String, String>();
				for (String ele : field) {
					tmpmap.put(ele, re.getString(ele));
				}
				data.add(tmpmap);
			}

			return data;

		}

	}

	public Query getQuery() {
		return new Query();
	}

	public Vector<Map<String, String>> get(String sqls, Vector<String> field) throws SQLException {

		Query query = getQuery();
		query.sqls = sqls;
		query.field = field;
		return query.all();

	}

	@SuppressWarnings("null")
	public Vector<Map<String, String>> getByCond(String tablename, Vector<String> field, Map<String, String> cond)
			throws IllegalArgumentException, SQLException {

		return new Query().select(field).from(tablename).where(cond).all();

	}

}
