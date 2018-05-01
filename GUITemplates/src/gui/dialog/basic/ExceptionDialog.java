package gui.dialog.basic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionDialog extends BasicDialog {

	protected Exception exception;

	public ExceptionDialog(Exception exception) {
		super(AlertType.ERROR);
		this.exception = exception;
	}

	public ExceptionDialog(String contentText, Exception exception) {
		super(contentText, AlertType.ERROR);
		this.exception = exception;
	}

	public ExceptionDialog(String title, String contentText, Exception exception) {
		super(title, contentText, AlertType.ERROR);
		this.exception = exception;
	}

	public ExceptionDialog(String title, String headerText, String contentText, Exception exception) {
		super(title, headerText, contentText, AlertType.ERROR);
		this.exception = exception;
	}

	@Override
	public String start() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Exception Stacktrace:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() ? result.get().getText() : null;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}
