import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import static framework.logging.MessageLevels.*;

import framework.logging.Logger;
import framework.properties.PropertiesFile;
import framework.transfer.Transfer;

public class FileTransfer extends Transfer {
	private boolean acceptFileTransfer;

	private final File[] transferFiles;
	private final FileInputStream[] fileInputStream;
	private final BufferedInputStream[] bufferedInputStream;

	private final Logger logger;

	public FileTransfer(boolean acceptFileTransfer, boolean verbose, int messageLevel) throws IOException {
		String numberOfFilesString = PropertiesFile.getProperty("numberOfFiles");
		int numberOfFiles = Integer.parseInt(numberOfFilesString);
		this.acceptFileTransfer = acceptFileTransfer;

		this.transferFiles = new File[numberOfFiles];
		this.fileInputStream = new FileInputStream[numberOfFiles];
		this.bufferedInputStream = new BufferedInputStream[numberOfFiles];

		for (int i = 0; i < numberOfFiles; i++) {
			String transferFilePath = PropertiesFile.getProperty("transferFilePath" + (i + 1)); // + (i + 1)
			this.transferFiles[i] = new File(transferFilePath);
			this.fileInputStream[i] = new FileInputStream(transferFiles[i]);
			this.bufferedInputStream[i] = new BufferedInputStream(fileInputStream[i]);
		}

		logger = new Logger("FileTransfer", verbose, messageLevel);
	}

	@Override
	public void start(InputStream inStream, OutputStream outStream) throws IOException {
		PrintWriter outWriter = new PrintWriter(outStream, true);

		sendNumberOfFiles(outWriter);
		sendFileNames(outWriter);
		sendFileSizes(outWriter);

		try {
			transferFiles(outStream);
		} catch (InterruptedException ex) {
			logger.printMessage("line 58: InterruptedException: " + ex.getMessage(), ERROR);
		}
	}

	public void sendNumberOfFiles(PrintWriter out) {
		if (acceptFileTransfer) {
			logger.printMessage("Sending number of files to client...", HIGHEST_PRIORITY);
			String numberOfFiles = String.valueOf(transferFiles.length);
			out.println(numberOfFiles);
			logger.printMessage("Number of files sent successfully.", HIGHEST_PRIORITY);
		} else {
			logger.printMessage(
					"line 100: acceptFileTransfer is set to false: " + "Permission was not given to transfer file.",
					ERROR);
		}
	}

	public void setTransferFilePaths(String[] transferFilePaths) throws FileNotFoundException {
		int numberOfFiles = transferFilePaths.length;

		for (int i = 0; i < numberOfFiles; i++) {
			this.transferFiles[i] = new File(transferFilePaths[i]);
			this.fileInputStream[i] = new FileInputStream(transferFiles[i]);
			this.bufferedInputStream[i] = new BufferedInputStream(fileInputStream[i]);
		}
	}

	public void sendFileNames(PrintWriter out) {
		if (acceptFileTransfer) {
			for (File transferFile : transferFiles) {
				out.println(transferFile.getName());
			}
		} else {
			logger.printMessage(
					"line 202: acceptFileTransfer is set to false: " + "Permission was not given to transfer file.",
					ERROR);
		}
	}

	public void sendFileSizes(PrintWriter out) throws IOException {
		if (acceptFileTransfer) {
			for (File transferFile : transferFiles) {
				String fileSize = String.valueOf(transferFile.length());
				out.println(fileSize);
			}
		} else {
			logger.printMessage(
					"line 214: acceptFileTransfer is set to false: " + "Permission was not given to transfer file.",
					ERROR);
		}
	}

	public void transferFiles(OutputStream out) throws IOException, InterruptedException {
		if (acceptFileTransfer) {
			int numberOfFiles = transferFiles.length;

			logger.printMessage("Sending file to client...", HIGHEST_PRIORITY);
			for (int i = 0; i < numberOfFiles; i++) {
				byte[] byteArray = new byte[(int) transferFiles[i].length()];
				bufferedInputStream[i].read(byteArray, 0, byteArray.length);

				logger.printMessage("Sending file '" + transferFiles[i].getName() + "' to client...", HIGHEST_PRIORITY);

				int byteEnd;
				for (int byteStart = 0; byteStart < byteArray.length; byteStart += byteEnd) {
					byteEnd = 65536;
					if (byteStart + byteEnd > byteArray.length) {
						byteEnd = byteArray.length - byteStart;
					}

					Thread.sleep(1000);
					out.write(byteArray, byteStart, byteEnd);
					out.flush();
				}

				logger.printMessage("File '" + transferFiles[i].getName() + "'sent to client successfully.",
						HIGHEST_PRIORITY);
			}

			logger.printMessage("Files sent to client successfully.", HIGHEST_PRIORITY);
		} else {
			logger.printMessage(
					"line 238: acceptFileTransfer is set to false: " + "Permission was not given to transfer file.",
					ERROR);
		}
	}

	public void setAcceptFileTransfer(boolean acceptFileTransfer) {
		this.acceptFileTransfer = acceptFileTransfer;
	}

	public boolean getAcceptFileTransfer() {
		return acceptFileTransfer;
	}
}
