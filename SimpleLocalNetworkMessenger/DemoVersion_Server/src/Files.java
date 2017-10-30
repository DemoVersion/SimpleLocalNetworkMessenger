import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;


public class Files {
	
	HashMap<String, HashMap<String, DVFile>> hmsf;
	public Files(){
		hmsf=new HashMap<String, HashMap<String, DVFile>>();
	}
	public DVFile getFile(String Reciver,String Name){
		if(!hmsf.containsKey(Reciver) || !hmsf.get(Reciver).containsKey(Name)){
			return null;
		}
		return hmsf.get(Reciver).get(Name);
	}
	public void storeFile(DVFile df){
		JOptionPane.showMessageDialog(null, "Server Recived"+df.getName(), "Server", JOptionPane.OK_OPTION);

		if(!hmsf.containsKey(df.getReciver())){
			hmsf.put(df.getReciver(), new HashMap<String, DVFile>());
		}
		hmsf.get(df.getReciver()).put(df.getName(), df);
		
	}
	public String getFilesCode(String name){
		if(!hmsf.containsKey(name))return "";
		String out="";
		HashMap<String, DVFile> myHM=hmsf.get(name);
		for(String fName : myHM.keySet()){
			if(!myHM.get(fName).getVisit()){
				out+="FIL_"+myHM.get(fName).getSenter()+"_"+myHM.get(fName).getReciver()+"_"+myHM.get(fName).getName()+"_"+myHM.get(fName).getData().length+"\n";
				myHM.get(fName).setVisit(true);
			}
		}
		return out;
	}
}
