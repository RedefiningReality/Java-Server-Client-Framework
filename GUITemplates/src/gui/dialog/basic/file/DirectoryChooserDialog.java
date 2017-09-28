package gui.dialog.basic.file;

import java.io.File;

import javafx.stage.DirectoryChooser;

public class DirectoryChooserDialog extends FileDialog {
	
	public DirectoryChooserDialog() {
		super();
	}
	
	public DirectoryChooserDialog(String title) {
		super(title);
	}
	
	public DirectoryChooserDialog(String title, String defaultPath) {
		super(title, defaultPath);
	}
	
	@Override
	public String start() {
        String stringResult = "";
        boolean resultIsValid;
        
        File selectedFile;
        
        do {
	        DirectoryChooser directoryChooser = new DirectoryChooser();
	        directoryChooser.setTitle(title);
	        
	        directoryChooser.setInitialDirectory(
	                new File(defaultPath.substring(0, defaultPath.lastIndexOf("\\")))
	        );
	        
	        selectedFile = directoryChooser.showDialog(null);
	        try {
	            stringResult = selectedFile.getAbsolutePath();
	            resultIsValid = true;
	        } catch (NullPointerException ex) {
	            resultIsValid = false;
	        }
	        
        } while (!resultIsValid);
        
        return stringResult;
	}
	
}
