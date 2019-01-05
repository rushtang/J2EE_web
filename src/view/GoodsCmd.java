package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import model.GoodsModel;

public class GoodsCmd extends Cmd {

	@SuppressWarnings("unused")
	public void addgoods() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		print("添加商品名称\n");

		String GNAME = br.readLine();
		print("\n添加商品价格");

		String GPRICE = br.readLine();

		print("\n添加商品数量");
		String GNUM = br.readLine();
		new GoodsModel().add(GNAME, GPRICE, GNUM);

	}

	public void updategoods() throws IOException, IllegalArgumentException, SQLException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		print("执行更改商品的操作\n\n", "请输入商品的名称\n\n");

		String chooseGNAME = br.readLine();
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("GNAME", chooseGNAME);

		GoodsModel goods = new GoodsModel();

		Vector<Map<String, String>> goodsinfos = goods.getByCond(goods.fieldcommon, cond);

		print("商品名称    " + "商品价格     " + "商品数量     ");
		for (Map<String, String> goodsinfo : goodsinfos) {
			print(String.format("%s    %s     %s   ", goodsinfo.get("GNAME"), goodsinfo.get("GPRICE"),
					goodsinfo.get("GNUM")));
		}

		print("选择你要更改的内容");
		print("1、更改商品名称 ", "2、更改商品价格 ", "3、更改商品数量 ");
		String choose = br.readLine();

		Map<String, String> param = new HashMap<String, String>();
		String param_key = "";

		switch (choose) {
		case "1":
			param_key = "GNAME";
			break;
		case "2":
			param_key = "GPRICE";
			break;
		case "3":
			param_key = "GNUM";
			break;
		default:
			param_key = "GNAME";

		}
		print("请输入更改的内容");
		String param_value = br.readLine();
		param.put(param_key, param_value);

		goods.update(cond, param);

	}

	public void del() throws IOException, IllegalArgumentException, SQLException {

		GoodsModel goods = new GoodsModel();

		print("执行删除商品操作/n");

		print("输出删除的商品名称");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String delGNAME = br.readLine();
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("GNAME", delGNAME);

		Vector<Map<String, String>> goodsinfos = goods.getByCond(goods.fieldcommon, cond);

		print("商品名称    " + "商品价格     " + "商品数量     ");
		for (Map<String, String> goodsinfo : goodsinfos) {
			print(String.format("%s    %s     %s   ", goodsinfo.get("GNAME"), goodsinfo.get("GPRICE"),
					goodsinfo.get("GNUM")));
		}
		print("是否确认要删除？ y/n");

		String is_del = br.readLine();
		print(is_del);
		if (is_del.equals("y")) {
			goods.del(cond);
		} else {
			print("已取消删除~");
		}

	}

	public void getall() throws IllegalArgumentException, SQLException {

		print("显示所有商品");

		GoodsModel goods = new GoodsModel();
		Vector<Map<String, String>> goodsinfos = goods.getQuery().select(goods.fieldcommon).from(goods.tablename).all();

		print("商品名称    " + "商品价格     " + "商品数量       " + "备注     ");
		for (Map<String, String> goodsinfo : goodsinfos) {
			String GNUM = goodsinfo.get("GNUM");
			String remark = "";
			if (Integer.parseInt(GNUM) < 10) {
				remark = "*该商品已不足10件！";
			} else {

			}

			print(String.format("%s       %s          %s      %s", goodsinfo.get("GNAME"), goodsinfo.get("GPRICE"),
					GNUM, remark));
		}

	}

	public void querygoods() throws IOException, IllegalArgumentException, SQLException {

		print("执行查询商品的操作!");
		print("1、按商品数量升序查询", "2、按商品价格升序查询", "3、输入关键字查询");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		GoodsModel goods = new GoodsModel();
		String choose = br.readLine();
		Vector<Map<String, String>> goodsinfos = new Vector<Map<String, String>>();
		switch (choose) {
		case "1":
			Vector<String> order = new Vector<String>();
			order.add("GNUM");
			goodsinfos = goods.getQuery().select(goods.fieldcommon).from(goods.tablename).orderby(order, false).all();
			break;
		case "2":
			Vector<String> order2 = new Vector<String>();
			order2.add("GPRICE");
			goodsinfos = goods.getQuery().select(goods.fieldcommon).from(goods.tablename).orderby(order2, false).all();

			break;
		case "3":
			print("请输入要查询的关键字");
			String key = br.readLine();
			String sqls = String.format("select GNAME,GPRICE,GNUM from GOODS where GNAME like \"%s%s%s\";", "%", key,
					"%");
			goodsinfos = goods.get(sqls, goods.fieldcommon);
			break;
		default:
			print("输入错误，已退出");
			break;
		}
		print("商品名称    " + "商品价格     " + "商品数量       " + "备注     ");
		for (Map<String, String> goodsinfo : goodsinfos) {
			String GNUM = goodsinfo.get("GNUM");
			String remark = "";
			if (Integer.parseInt(GNUM) < 10) {
				remark = "*该商品已不足10件！";
			} else {

			}

			print(String.format("%s       %s          %s      %s", goodsinfo.get("GNAME"), goodsinfo.get("GPRICE"),
					GNUM, remark));
		}

	}

}
