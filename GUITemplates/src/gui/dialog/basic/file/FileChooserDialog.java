package gui.dialog.basic.file;

import java.io.File;

import javafx.stage.FileChooser;

public class FileChooserDialog extends FileDialog {

	public FileChooserDialog() {
		super();
	}

	public FileChooserDialog(String title) {
		super(title);
	}

	public FileChooserDialog(String title, String defaultPath) {
		super(title, defaultPath);
	}

	@Override
	public String start() {
		String stringResult = "";
		boolean resultIsValid;

		File selectedFile;

		do {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(title);

			fileChooser.setInitialDirectory(new File(defaultPath.substring(0, defaultPath.lastIndexOf("\\"))));

			selectedFile = fileChooser.showOpenDialog(null);
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
