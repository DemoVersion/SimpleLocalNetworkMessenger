
public class Message {
	private String Senter;
	private String Reciver;
	private String MSG;
	
	public Message(String senter, String reciver, String mSG) {
		Senter = senter;
		Reciver = reciver;
		MSG = mSG;
	}
	public Message(String Code){
		String Arr[] = Code.split("_");
		Senter = Arr[1];
		Reciver = Arr[2];
		MSG = Arr[3];
	}
	public String getCode(){
		return "MSG"+"_"+Senter+"_"+Reciver+"_"+MSG;
	}
	public String getSenter() {
		return Senter;
	}
	public void setSenter(String senter) {
		Senter = senter;
	}
	public String getReciver() {
		return Reciver;
	}
	public void setReciver(String reciver) {
		Reciver = reciver;
	}
	public String getMSG() {
		return MSG;
	}
	public void setMSG(String mSG) {
		MSG = mSG;
	}
	
}
