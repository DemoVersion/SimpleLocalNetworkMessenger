import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class Friends {

	public static final long aPeek=1000*10; 
	HashMap<String, Long> lTCH;
	
	HashMap<String, HashMap<String , Integer > > addRQ;
	HashMap<String, HashSet<String>> Friends;
	public Friends(){
		lTCH= new HashMap<String, Long>();
		Friends= new HashMap<String,  HashSet<String>>();
		addRQ= new HashMap<String, HashMap<String , Integer > > ();
		try {
			FileInputStream fis=new FileInputStream("Friends.txt");
			Scanner scn = new Scanner(fis);
			while(scn.hasNextLine()){
				String line=scn.nextLine();
				String [] arr = line.split("_");
				Friends.put(arr[0], new HashSet<String>());
				for(int i=1;i<arr.length;i++){
					Friends.get(arr[0]).add(arr[i]);
				}
			}
		} catch (Exception e) {
			
		}
		

	}
	public synchronized String getCodeFriends(String name){
		ArrayList<Friend> alf= getFriends(name);
		String out="";
		for(Friend f:alf){
			out+="FRN_"+f.getName()+"_"+(f.getIsOnline()?"ON":"OFF")+"\n";
		}
		return out;
	}
	public synchronized String getCodeNewAdds(String name){
		if(!addRQ.containsKey(name))return "";
		String out="";
		HashMap<String, Integer> myHM=addRQ.get(name);
		for(String fName : myHM.keySet()){
			if(myHM.get(fName)==0){
				out+="ADD_"+fName+"_"+name+"\n";
				myHM.put(fName, 1);
			}
		}
		return out;
	}
	public synchronized void add(String Name1, String Name2){
		if(addRQ.containsKey(Name1)&&addRQ.get(Name1).containsKey(Name2)){
			MakeFriend(Name1, Name2);
		}else{
			if(!addRQ.containsKey(Name2)){
				addRQ.put(Name2, new HashMap<String,Integer>());
			}
			addRQ.get(Name2).put(Name1, 0);
		}
	}
	public synchronized boolean isOnline(String S){
		if(lTCH.containsKey(S)){
			if(System.currentTimeMillis()-lTCH.get(S)>aPeek){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	public synchronized HashSet<String> getFriendsName(String Name){
		if(Friends.containsKey(Name)){
			return Friends.get(Name);
		}else{
			return new HashSet<String>();
		}
	}
	public synchronized ArrayList<Friend> getFriends(String Name){
		lTCH.put(Name, System.currentTimeMillis());
		ArrayList<Friend> alf= new ArrayList<Friend>();
		HashSet<String> alfs= getFriendsName(Name);
		for(String f:alfs){
			if(isOnline(f)){
				alf.add(new Friend(f, true));
			}else{
				alf.add(new Friend(f,false));
			}
		}
		return alf;
	}
	public synchronized void MakeFriend(String Name1,String Name2){
		if(!Friends.containsKey(Name1)){
			Friends.put(Name1, new HashSet<String>());
		}
		if(!Friends.containsKey(Name2)){
			Friends.put(Name2, new HashSet<String>());
		}
		Friends.get(Name1).add(Name2);
		Friends.get(Name2).add(Name1);
		
		try {
			PrintStream ps=new PrintStream("Friends.txt");
			for(String s:Friends.keySet()){
				HashSet<String> hss= Friends.get(s);
				ps.print(s);
				for(String friendName: hss){
					ps.print("_"+friendName);
				}
				ps.println();
			}
		} catch (Exception e) {

		}
		
	}
}
