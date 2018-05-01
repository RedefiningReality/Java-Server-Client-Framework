package gui.dialog.examples;

import static framework.logging.MessageLevels.DATA;

import gui.dialog.progress.ProgressDialog;
import javafx.concurrent.Task;

public class ExampleProgressDialog {

	public static void main(String[] args) {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				ProgressDialog.setText("      Downloading Data...");
				for (int i = 0; i < 10000; i++) {
					ProgressDialog.setInfoText("      " + i + " out of 10000 KB downloaded.\n" + "      " + (10000 - i)
							+ " KB remaining.");
					updateProgress(i, 10000);
					Thread.sleep(1);
				}
				updateProgress(10000, 10000);
				ProgressDialog.setInfoText("      10000 out of 10000 KB downloaded.\n" + "      0 KB remaining.");
				ProgressDialog.setText("    Download Complete.");
				return null;
			}
		};

		ProgressDialog.setTitle("File Transfer");
		ProgressDialog.setBarPrefHeight(30);
		ProgressDialog.setBarPrefWidth(500);
		ProgressDialog.start(task, true, DATA);
	}

}
