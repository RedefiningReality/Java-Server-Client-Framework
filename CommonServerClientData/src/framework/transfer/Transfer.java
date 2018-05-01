package framework.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import gui.dialog.userinput.UserInputDialog;

public abstract class Transfer {

	protected List<UserInputDialog> dialogs;

	public Transfer() {
		this.dialogs = new ArrayList<UserInputDialog>();
	}

	public Transfer(List<UserInputDialog> dialogs) {
		this.setDialogsCopy(dialogs);
	}

	protected int addDialog(UserInputDialog dialog) {
		dialogs.add(dialog);
		return dialogs.size() - 1;
	}

	protected UserInputDialog setDialog(int index, UserInputDialog dialog) {
		return dialogs.set(index, dialog);
	}

	protected void setDialogs(List<UserInputDialog> dialogs) {
		this.dialogs = dialogs;
	}

	protected void setDialogsCopy(List<UserInputDialog> dialogs) {
		this.dialogs = new ArrayList<UserInputDialog>();

		for (UserInputDialog dialog : dialogs)
			this.dialogs.add(dialog);
	}

	public UserInputDialog getDialog(int index) {
		return dialogs.get(index);
	}

	public List<UserInputDialog> getDialogs() {
		return dialogs;
	}

	public List<UserInputDialog> getDialogsCopy() {
		List<UserInputDialog> result = new ArrayList<UserInputDialog>();

		for (UserInputDialog dialog : dialogs)
			result.add(dialog);

		return result;
	}

	public abstract void start(final InputStream in, final OutputStream out) throws IOException;
}
