import java.util.ArrayList;
import java.util.HashMap;


public class Messages {
	
	HashMap<String, ArrayList<Message>> hmSM;
	public Messages(){
		hmSM= new HashMap<String, ArrayList<Message>>();
	}
	public synchronized void SentMessage(Message m){
		if(!hmSM.containsKey(m.getReciver())){
			hmSM.put(m.getReciver(), new ArrayList<Message>());
		
		}
		hmSM.get(m.getReciver()).add(m);
	}
	public synchronized String getCodeMessages(String name){
		ArrayList<Message> alm= getMessages(name);
		String out="";
		for(Message m:alm){
			out+=m.getCode()+"\n";
		}
		return out;
		
	}
	public synchronized ArrayList<Message> getMessages(String Name){
		if(hmSM.containsKey(Name)){
			ArrayList<Message> out = hmSM.get(Name);
			hmSM.remove(Name);
			return out;
			
		}
		return new ArrayList<Message>();
	}
}
