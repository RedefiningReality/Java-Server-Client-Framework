package gui.dialog.basic.file;

import gui.dialog.basic.BasicDialog;

public abstract class FileDialog extends BasicDialog {
	
	protected String defaultPath;
	
	public FileDialog() {
		super();
		this.defaultPath = "";
	}
	
	public FileDialog(String title) {
		super(title);
		this.defaultPath = "";
	}
	
	public FileDialog(String title, String defaultPath) {
		super(title);
		this.defaultPath = defaultPath;
	}
	
	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}
	
	public String getDefaultPath() {
		return defaultPath;
	}
	
}
