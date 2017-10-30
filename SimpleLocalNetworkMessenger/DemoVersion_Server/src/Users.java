import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;


public class Users {
	HashMap<String, String> hmSS;
	public Users(){
		hmSS=new HashMap<String,String>();
		try {
			FileInputStream fis=new FileInputStream("Users.txt");
			Scanner scn = new Scanner(fis);
			while(scn.hasNextLine()){
				String line=scn.nextLine();
				String [] arr = line.split("_");
				hmSS.put(arr[0], arr[1]);
			}
		} catch (Exception e) {
			
		}
	}
	public synchronized boolean insert(String User,String Pass){
		if(hmSS.containsKey(User))return false;
		hmSS.put(User, Pass);
		try {
			PrintStream ps=new PrintStream("Users.txt");
			for(String s:hmSS.keySet()){
				ps.println(s+"_"+hmSS.get(s));
			}
		} catch (Exception e) {

		}
		
		
		return true;
	}
	public synchronized boolean Exist(String User,String Pass){
		if(!hmSS.containsKey(User))return false;
		return hmSS.get(User).equals( Pass);
	}
}
