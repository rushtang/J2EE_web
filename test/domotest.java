import java.io.IOException;
import java.sql.SQLException;

import view.GoodsCmd;;

public class domotest {

	public static void main(String[] args) throws IllegalArgumentException, SQLException, IOException {

		try {
			@SuppressWarnings("unused")
			Process process = Runtime.getRuntime().exec("/usr/bin/open -a Terminal");
			Runtime.getRuntime().exec("ls");
			System.out.println();
			new GoodsCmd().querygoods();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("over~");

	}

}
