package view;



public class Cmd {
	
	public static void print(String ...args){
		for (String arg : args){
			System.out.println(arg);
		}
		
	}

	public static void print(String arg) {
		System.out.println(arg);
		
	}

}
