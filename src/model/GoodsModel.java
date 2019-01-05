package model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GoodsModel extends DbModel {

	public final String tablename = "GOODS";

	public Vector<String> fieldname = new Vector<>();
	public Vector<String> fieldcommon = new Vector<>();

	public Map<String, String> field = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public GoodsModel() {
		fieldcommon.add("GNAME");
		fieldcommon.add("GPRICE");
		fieldcommon.add("GNUM");

		fieldname = (Vector<String>) fieldcommon.clone();
		fieldname.add("GID");

		field.put("GNAME", "");
		field.put("GPRICE", "");
		field.put("GNUM", "");
	}

	public void add(String GNAME, String GPRICE, String GNUM) {
		field.put("GNAME", GNAME);
		field.put("GPRICE", GPRICE);
		field.put("GNUM", GNUM);

		DbModel.insert(tablename, field);

	}

	public Vector<Map<String, String>> getByCond(Vector<String> field, Map<String, String> cond)
			throws IllegalArgumentException, SQLException {
		return this.getByCond(tablename, field, cond);

	}

	@SuppressWarnings("static-access")
	public void update(Map<String, String> cond, Map<String, String> param) {

		this.update(tablename, cond, param);
	}

	@SuppressWarnings("static-access")
	public void del(Map<String, String> cond) {
		this.del(tablename, cond);
	}

}
