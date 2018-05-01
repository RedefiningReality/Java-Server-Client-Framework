package gui.dialog.progress;

import static framework.logging.MessageLevels.ERROR;
import static framework.logging.MessageLevels.INFO;

import framework.logging.Logger;
import gui.dialog.basic.confirmation.CustomConfirmationDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressDialog extends Application {

	protected static Stage stage;
	protected static ProgressBar progressBar;
	protected static ProgressIndicator progressIndicator;

	protected static Text text;
	protected static Text infoText;

	protected static Button cancelButton;
	protected static Button finishButton;

	protected static double width = -1;
	protected static double height = -1;

	protected static double barPrefWidth = -1;
	protected static double barPrefHeight = -1;
	protected static double barLayoutX = -1;
	protected static double barLayoutY = -1;

	protected static double indicatorPrefWidth = -1;
	protected static double indicatorPrefHeight = -1;
	protected static double indicatorLayoutX = -1;
	protected static double indicatorLayoutY = -1;

	protected static double cancelPrefWidth = -1;
	protected static double cancelPrefHeight = -1;
	protected static double cancelLayoutX = -1;
	protected static double cancelLayoutY = -1;

	protected static double finishPrefWidth = -1;
	protected static double finishPrefHeight = -1;
	protected static double finishLayoutX = -1;
	protected static double finishLayoutY = -1;

	protected static double textPrefWidth = -1;
	protected static double textPrefHeight = -1;
	protected static double textLayoutX = -1;
	protected static double textLayoutY = -1;

	protected static double infoTextPrefWidth = -1;
	protected static double infoTextPrefHeight = -1;
	protected static double infoTextLayoutX = -1;
	protected static double infoTextLayoutY = -1;

	protected static double progressPrefWidth = -1;
	protected static double progressPrefHeight = -1;
	protected static double progressSpacing = -1;

	protected static double buttonPrefWidth = -1;
	protected static double buttonPrefHeight = -1;
	protected static double buttonSpacing = 10;

	protected static String title;

	protected static Task<Void> task;

	private static Logger logger;

	public static void start(Task<Void> task) {
		ProgressDialog.logger = new Logger("ProgressDialog");

		ProgressDialog.task = task;
		ProgressDialog.launch();
	}

	public static void start(Task<Void> task, boolean verbose, int messageLevel) {
		ProgressDialog.logger = new Logger("ProgressDialog", verbose, messageLevel);

		ProgressDialog.task = task;
		ProgressDialog.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = new Stage();
		stage.setTitle(title);
		stage.initStyle(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);

		if (width >= 0)
			stage.setWidth(width);
		if (height >= 0)
			stage.setHeight(height);

		progressBar = new ProgressBar();

		if (barPrefWidth >= 0)
			progressBar.setPrefWidth(barPrefWidth);
		if (barPrefHeight >= 0)
			progressBar.setPrefHeight(barPrefHeight);

		if (barLayoutX >= 0)
			progressBar.setLayoutX(barLayoutX);
		if (barLayoutY >= 0)
			progressBar.setLayoutY(barLayoutY);

		progressIndicator = new ProgressIndicator();

		if (indicatorPrefWidth >= 0)
			progressIndicator.setPrefWidth(indicatorPrefWidth);
		if (indicatorPrefHeight >= 0)
			progressIndicator.setPrefHeight(indicatorPrefHeight);

		if (indicatorLayoutX >= 0)
			progressIndicator.setLayoutX(indicatorLayoutX);
		if (indicatorLayoutY >= 0)
			progressIndicator.setLayoutY(indicatorLayoutY);

		cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			String result = new CustomConfirmationDialog("Are you sure you would like to cancel?",
					new String[] { "Yes", "No" }).start();

			if (result.equals("Yes")) {
				logger.printMessage("Received \"Cancel\" request from user.", INFO);
				logger.printMessage("Cancelling...", INFO);
				stage.close();
			}
		});

		if (cancelPrefWidth >= 0)
			cancelButton.setPrefWidth(cancelPrefWidth);
		if (cancelPrefHeight >= 0)
			cancelButton.setPrefHeight(cancelPrefHeight);

		if (cancelLayoutX >= 0)
			cancelButton.setLayoutX(cancelLayoutX);
		if (cancelLayoutY >= 0)
			cancelButton.setLayoutY(cancelLayoutY);

		finishButton = new Button("Finish");
		finishButton.setOnAction(e -> {
			logger.printMessage("Received \"Finish\" request from user.", INFO);
			logger.printMessage("Exiting...", INFO);
			stage.close();
		});

		if (finishPrefWidth >= 0)
			finishButton.setPrefWidth(finishPrefWidth);
		if (finishPrefHeight >= 0)
			finishButton.setPrefHeight(finishPrefHeight);

		if (finishLayoutX >= 0)
			finishButton.setLayoutX(finishLayoutX);
		if (finishLayoutY >= 0)
			finishButton.setLayoutY(finishLayoutY);

		if (text == null)
			text = new Text();

		if (textPrefWidth >= 0)
			text.prefWidth(textPrefWidth);
		if (textPrefHeight >= 0)
			text.prefHeight(textPrefHeight);

		if (textLayoutX >= 0)
			text.setLayoutX(textLayoutX);
		if (textLayoutY >= 0)
			text.setLayoutY(textLayoutY);

		progressBar.setProgress(-1F);
		progressIndicator.setProgress(-1F);

		final HBox progressBox = new HBox();

		if (progressPrefWidth >= 0)
			progressBox.setPrefWidth(progressPrefWidth);
		if (progressPrefHeight >= 0)
			progressBox.setPrefHeight(progressPrefHeight);

		if (progressSpacing >= 0)
			progressBox.setSpacing(progressSpacing);

		progressBox.setAlignment(Pos.CENTER);
		progressBox.getChildren().addAll(progressBar, progressIndicator);

		if (infoText == null)
			infoText = new Text();

		if (infoTextPrefWidth >= 0)
			infoText.prefWidth(infoTextPrefWidth);
		if (infoTextPrefHeight >= 0)
			infoText.prefHeight(infoTextPrefHeight);

		if (infoTextLayoutX >= 0)
			infoText.setLayoutX(infoTextLayoutX);
		if (infoTextLayoutY >= 0)
			infoText.setLayoutY(infoTextLayoutY);

		final HBox buttonBox = new HBox();

		if (buttonPrefWidth >= 0)
			buttonBox.setPrefWidth(buttonPrefWidth);
		if (buttonPrefHeight >= 0)
			buttonBox.setPrefHeight(buttonPrefHeight);

		if (buttonSpacing >= 0)
			buttonBox.setSpacing(buttonSpacing);

		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(cancelButton, finishButton);

		final VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER_LEFT);
		vBox.getChildren().addAll(text, progressBox, infoText, buttonBox);

		Scene scene = new Scene(vBox);
		stage.setScene(scene);

		try {
			activateProgressBar(task);
		} catch (InterruptedException ex) {
			logger.printMessage(
					"line 80: InterruptedException: Progress Interrupted: " + ex.getMessage() + ": Terminating...",
					ERROR);
		}

		task.setOnSucceeded(event -> {
			stage.setOnCloseRequest(e -> {
				logger.printMessage("Received \"Finish\" request from user.", INFO);
				logger.printMessage("Exiting...", INFO);
			});

			cancelButton.setDisable(true);
			finishButton.setDisable(false);
		});

		cancelButton.setDisable(false);
		finishButton.setDisable(true);

		stage.setOnCloseRequest(e -> {
			String result = new CustomConfirmationDialog("Are you sure you would like to cancel?",
					new String[] { "Yes", "No" }).start();

			if (result.equals("Yes")) {
				logger.printMessage("Received \"Cancel\" request from user.", INFO);
				logger.printMessage("Cancelling...", INFO);
				stage.close();
			}

			e.consume();
		});
		stage.show();

		Thread thread = new Thread(task);
		thread.start();
	}

	public void activateProgressBar(final Task<Void> task) throws InterruptedException {
		progressBar.progressProperty().bind(task.progressProperty());
		progressIndicator.progressProperty().bind(task.progressProperty());
		stage.show();
	}

	public static void setText(String text) {
		Platform.runLater(() -> {
			ProgressDialog.text.setText(text);
		});
	}

	public static Text getText() {
		return text;
	}

	public static String getTextString() {
		return text.getText();
	}

	public static void setInfoText(String infoText) {
		Platform.runLater(() -> {
			ProgressDialog.infoText.setText(infoText);
		});
	}

	public static Text getInfoText() {
		return infoText;
	}

	public static String getInfoTextString() {
		return infoText.getText();
	}

	public static Button getCancelButton() {
		return cancelButton;
	}

	public static Button getFinishButton() {
		return finishButton;
	}

	public static void setTitle(String title) {
		ProgressDialog.title = title;
	}

	public static String getTitle() {
		return title;
	}

	public static void setWidth(double width) {
		ProgressDialog.width = width;
	}

	public static double getWidth() {
		return width;
	}

	public static void setHeight(double height) {
		ProgressDialog.height = height;
	}

	public static double getHeight() {
		return height;
	}

	public static void setBarPrefWidth(double barPrefWidth) {
		ProgressDialog.barPrefWidth = barPrefWidth;
	}

	public static double getBarPrefWidth() {
		return barPrefWidth;
	}

	public static void setBarPrefHeight(double barPrefHeight) {
		ProgressDialog.barPrefHeight = barPrefHeight;
	}

	public static double getBarPrefHeight() {
		return barPrefHeight;
	}

	public static void setBarLayoutX(double barLayoutX) {
		ProgressDialog.barLayoutX = barLayoutX;
	}

	public static double getBarLayoutX() {
		return barLayoutX;
	}

	public static void setBarLayoutY(double barLayoutY) {
		ProgressDialog.barLayoutY = barLayoutY;
	}

	public static double getBarLayoutY() {
		return barLayoutY;
	}

	public static void setIndicatorPrefWidth(double indicatorPrefWidth) {
		ProgressDialog.indicatorPrefWidth = indicatorPrefWidth;
	}

	public static double getIndicatorPrefWidth() {
		return indicatorPrefWidth;
	}

	public static void setIndicatorPrefHeight(double indicatorPrefHeight) {
		ProgressDialog.indicatorPrefHeight = indicatorPrefHeight;
	}

	public static double getIndicatorPrefHeight() {
		return indicatorPrefHeight;
	}

	public static void setIndicatorLayoutX(double indicatorLayoutX) {
		ProgressDialog.indicatorLayoutX = indicatorLayoutX;
	}

	public static double getIndicatorLayoutX() {
		return indicatorLayoutX;
	}

	public static void setIndicatorLayoutY(double indicatorLayoutY) {
		ProgressDialog.indicatorLayoutY = indicatorLayoutY;
	}

	public static double getIndicatorLayoutY() {
		return indicatorLayoutY;
	}

	public static void setCancelPrefWidth(double cancelPrefWidth) {
		ProgressDialog.cancelPrefWidth = cancelPrefWidth;
	}

	public static double getCancelPrefWidth() {
		return cancelPrefWidth;
	}

	public static void setCancelPrefHeight(double cancelPrefHeight) {
		ProgressDialog.cancelPrefHeight = cancelPrefHeight;
	}

	public static double getCancelPrefHeight() {
		return cancelPrefHeight;
	}

	public static void setCancelLayoutX(double cancelLayoutX) {
		ProgressDialog.cancelLayoutX = cancelLayoutX;
	}

	public static double getCancelLayoutX() {
		return cancelLayoutX;
	}

	public static void setCancelLayoutY(double cancelLayoutY) {
		ProgressDialog.cancelLayoutY = cancelLayoutY;
	}

	public static double getCancelLayoutY() {
		return cancelLayoutY;
	}

	public static void setFinishPrefWidth(double finishPrefWidth) {
		ProgressDialog.finishPrefWidth = finishPrefWidth;
	}

	public static double getFinishPrefWidth() {
		return finishPrefWidth;
	}

	public static void setFinishPrefHeight(double finishPrefHeight) {
		ProgressDialog.finishPrefHeight = finishPrefHeight;
	}

	public static double getFinishPrefHeight() {
		return finishPrefHeight;
	}

	public static void setFinishLayoutX(double finishLayoutX) {
		ProgressDialog.finishLayoutX = finishLayoutX;
	}

	public static double getFinishLayoutX() {
		return finishLayoutX;
	}

	public static void setFinishLayoutY(double finishLayoutY) {
		ProgressDialog.finishLayoutY = finishLayoutY;
	}

	public static double getFinishLayoutY() {
		return finishLayoutY;
	}

	public static void setTextPrefWidth(double textPrefWidth) {
		ProgressDialog.textPrefWidth = textPrefWidth;
	}

	public static double getTextPrefWidth() {
		return textPrefWidth;
	}

	public static void setTextPrefHeight(double textPrefHeight) {
		ProgressDialog.textPrefHeight = textPrefHeight;
	}

	public static double getTextPrefHeight() {
		return textPrefHeight;
	}

	public static void setTextLayoutX(double textLayoutX) {
		ProgressDialog.textLayoutX = textLayoutX;
	}

	public static double getTextLayoutX() {
		return textLayoutX;
	}

	public static void setTextLayoutY(double textLayoutY) {
		ProgressDialog.textLayoutY = textLayoutY;
	}

	public static double getTextLayoutY() {
		return textLayoutY;
	}

	public static void setProgressPrefWidth(double progressPrefWidth) {
		ProgressDialog.progressPrefWidth = progressPrefWidth;
	}

	public static double getProgressPrefWidth() {
		return progressPrefWidth;
	}

	public static void setProgressPrefHeight(double progressPrefHeight) {
		ProgressDialog.progressPrefHeight = progressPrefHeight;
	}

	public static double getProgressPrefHeight() {
		return progressPrefHeight;
	}

	public static void setProgressSpacing(double progressSpacing) {
		ProgressDialog.progressSpacing = progressSpacing;
	}

	public static double getProgressSpacing() {
		return progressSpacing;
	}

	public static void setButtonPrefWidth(double buttonPrefWidth) {
		ProgressDialog.buttonPrefWidth = buttonPrefWidth;
	}

	public static double getButtonPrefWidth() {
		return buttonPrefWidth;
	}

	public static void setButtonPrefHeight(double buttonPrefHeight) {
		ProgressDialog.buttonPrefHeight = buttonPrefHeight;
	}

	public static double getButtonPrefHeight() {
		return buttonPrefHeight;
	}

	public static void setButtonSpacing(double buttonSpacing) {
		ProgressDialog.buttonSpacing = buttonSpacing;
	}

	public static double getButtonSpacing() {
		return buttonSpacing;
	}

}
