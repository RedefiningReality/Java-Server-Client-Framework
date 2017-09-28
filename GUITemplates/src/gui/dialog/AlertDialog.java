package gui.dialog;

public abstract class AlertDialog {
	
	protected String title;
	
	public AlertDialog() {
		this.title = "";
	}
	
	public AlertDialog(String title) {
		this.title = title;
	}
	
	public abstract String start();
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
}
