// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.


package nl.justobjects.pushlet.j2me;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.util.Hashtable;

/**
 * Example Pushlet MIDlet.
 *
 * @author  Just
 * @version $Id: PushletMIDlet.java,v 1.2 2005/05/19 11:17:42 justb Exp $
 */
public class PushletMIDlet extends MIDlet implements CommandListener, PushletClientListener, Runnable {
	/** TextField size constant */
	private static final int TEXTFIELD_SIZE = 256;

	/** Command priority constant */
	private static final int COMMAND_PRIORITY = 1;

	/** Command for exit. */
	public static final Command CMD_EXIT = new Command("Exit", Command.EXIT, COMMAND_PRIORITY);

	/** UI display. */
	private Display display;

	/** Form to display information to the user */
	private Form midletForm;

	/** TextField to show status. */
	private TextField textField;

	private PushletClient pushletClient;
	private int pubCount;

	private static final String PUSHLET_URL = "http://www.pushlets.com/pushlet/pushlet.srv";
	private static final String PUSHLET_SUBJECT = "/j2me/test";

	/**
	 *  Sets up the midlet and the items required for user interaction.
	 */
	public void startApp() {
		if (display == null) {
			display = Display.getDisplay(this);
			midletForm = new Form("Pushlet Midlet");
			textField = new TextField("Status", "", TEXTFIELD_SIZE, TextField.ANY);
			midletForm.append(textField);
			midletForm.addCommand(CMD_EXIT);
			midletForm.setCommandListener(this);
		}
		display.setCurrent(midletForm);

		new Thread(this).start();

	}

	/**
	 *  Must be defined but no implementation required as the midlet only responds
	 *  to user interaction.
	 */
	public void pauseApp() {
	}

	/**
	 *  Must be defined but no implementation required as all the resources
	 *  are released when the thread terminates.
	 */
	public void destroyApp(boolean unconditional) {
	}

	/**
	 *  Handles the commands on the UI.
	 *
	 *  @param cmd the command object identifying the command
	 *  @param source the displayable on which the command has occurred
	 */
	public void commandAction(Command cmd, Displayable source) {
		if (cmd == CMD_EXIT) {
			destroyApp(true);
			notifyDestroyed();
		}
	}

	/**
	 *  Defines the run() method which all Runnable objects must do.
	 *  Creates a SocketConnection to the address appearing in the
	 *  TextField on the screen.
	 */
	public void run() {

		try {
			pushletClient = new PushletClient(PUSHLET_URL);
			showStatus("JOINING ");
			pushletClient.join();
			showStatus("JOINED... ");

			// Start listening on subject
			// We should get callbacks like onData()
			pushletClient.listen(this, MODE_STREAM, PUSHLET_SUBJECT);
			showStatus("LISTENING ");

			// Keep publishing events
			while (true) {
				Hashtable attrs = new Hashtable(3);
				attrs.put("data", "okokokok" + (pubCount++));
				try {
					showStatus("PUBLISHING...");
					pushletClient.publish(PUSHLET_SUBJECT, attrs);
					showStatus("PUBLISHED " + (pubCount));
				} catch (PushletException pe) {
					showStatus("Cannot publish: e=" + pe);
					pushletClient = null;
					break;
				}
				Thread.sleep(5000);
			}
		} catch (PushletException pe) {
			showStatus("Cannot join: e=" + pe);
			pushletClient = null;

		} catch (Throwable t) {
			showStatus("Exception : e=" + t);
			pushletClient = null;
		}
	}

	/** Abort event from server. */
	public void onAbort(Event theEvent) {
		showStatus("ABORT: " + theEvent);
	}


	/** Data event from server. */
	public void onData(Event theEvent) {
		showStatus("DATA: " + theEvent);
	}

	/** Heartbeat event from server. */
	public void onHeartbeat(Event theEvent) {
		showStatus("HEARTBEAT: " + theEvent);
	}

	/** Error occurred. */
	public void onError(String message) {
		showStatus("ERROR: " + message);

	}


	/** Displays an alert with the desired text to the user. */
	private void showStatus(String aText) {
		System.out.println("PushletMIDlet: " + aText);
		synchronized (textField) {
			textField.setString(aText);
		}
	}
}

/*
 * $Log: PushletMIDlet.java,v $
 * Revision 1.2  2005/05/19 11:17:42  justb
 * comments added
 *
 * Revision 1.1  2005/05/19 11:15:35  justb
 * first version for j2me
 *
 */