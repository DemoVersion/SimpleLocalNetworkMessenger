
public class Friend {
	private String Name;
	private Boolean isOnline;
	public Friend(String name, Boolean isOnline) {
		Name = name;
		this.isOnline = isOnline;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
}
