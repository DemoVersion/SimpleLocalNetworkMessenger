
public class DVFile {
	private String senter;
	private String reciver;
	private String name;
	private Boolean visit;
	private byte [] data;
	public DVFile(String senter, String reciver, String name, byte[] data) {
		this.senter = senter;
		this.reciver = reciver;
		this.name = name;
		this.data = data;
		visit=false;
	}
	
	
	public Boolean getVisit() {
		return visit;
	}


	public void setVisit(Boolean visit) {
		this.visit = visit;
	}


	public String getInfoCode(){
		return "FIL_"+senter+"_"+reciver+"_"+name+"_"+data.length;
	}


	public String getSenter() {
		return senter;
	}


	public void setSenter(String senter) {
		this.senter = senter;
	}


	public String getReciver() {
		return reciver;
	}


	public void setReciver(String reciver) {
		this.reciver = reciver;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}

	
}
