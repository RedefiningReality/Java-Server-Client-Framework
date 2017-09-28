

public abstract class Dialog {
	
	protected final String title;
	
	public Dialog() {
		this.title = "";
	}
	
	public Dialog(String title) {
		this.title = title;
	}
	
	public abstract String start();
	
}
