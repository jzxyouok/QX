// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.


package nl.justobjects.pushlet.j2me;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import java.io.*;
import java.util.Hashtable;

/**
 * Full Pushlet client for Java J2ME clients.
 * <p>
 * Use this class within J2ME MIDlets. Should work at least
 * for MIDP2, and possibly MIDP1.
 *
 * @version $Id: PushletClient.java,v 1.3 2005/07/22 16:28:36 justb Exp $
 * @author Just van den Broecke - Just Objects &copy;
 *
 * @see nl.justobjects.pushlet.j2me.PushletClientListener
 */
public class PushletClient implements Protocol {
	/** Pushlet URL. */
	private String pushletURL;

	/** Debug flag for verbose output. */
	private boolean debug;

	/** Id gotten on join ack */
	private String id;

	/** Internal listener for data events pushed by server. */
	private DataEventListener dataEventListener;

	/** Default constructor. */
	public PushletClient(String aPushletURL) {
		pushletURL = aPushletURL;
	}

	/** Constructor with host and port using default URI. */
	public PushletClient(String aHost, int aPort) {
		this("http://" + aHost + ":" + aPort + DEFAULT_SERVLET_URI);
	}


	/** Join server, starts session. */
	public void join() throws PushletException {
		Event event = new Event(E_JOIN);
		event.setField(P_FORMAT, FORMAT_XML);
		Event response = postEvent(event);
		throwOnNack(response);

		// Join Ack received
		id = response.getField(P_ID);
	}

	/** Leave server, stops session. */
	public void leave() throws PushletException {
		stopListen();
		throwOnInvalidSession();
		Event event = new Event(E_LEAVE);
		event.setField(P_ID, id);
		Event response = postEvent(event);

		throwOnNack(response);
		id = null;
	}

	/** Open data channel. */
	public void listen(PushletClientListener aListener) throws PushletException {
		listen(aListener, MODE_STREAM);
	}

	/** Open data channel in stream or push mode. */
	public void listen(PushletClientListener aListener, String aMode) throws PushletException {
		listen(aListener, aMode, null);
	}

	/** Open data channel in stream or push mode with a subject. */
	public void listen(PushletClientListener aListener, String aMode, String aSubject) throws PushletException {
		throwOnInvalidSession();
		stopListen();

		String listenURL = pushletURL
				+ "?" + P_EVENT + "=" + E_LISTEN
				+ "&" + P_ID + "=" + id
				+ "&" + P_MODE + "=" + aMode
				;
		if (aSubject != null) {
			listenURL = listenURL + "&" + P_SUBJECT + "=" + aSubject;
		}

		dataEventListener = new DataEventListener(aListener, listenURL);
		dataEventListener.start();
	}

	/** Immediate listener. */
	public void joinListen(PushletClientListener aListener, String aMode, String aSubject) throws PushletException {
		stopListen();

		String listenURL = pushletURL
				+ "?" + P_EVENT + "=" + E_JOIN_LISTEN
				+ "&" + P_FORMAT + "=" + FORMAT_XML
				+ "&" + P_MODE + "=" + aMode
				+ "&" + P_SUBJECT + "=" + aSubject
				;

		dataEventListener = new DataEventListener(aListener, listenURL);
		dataEventListener.start();
	}

	/** Publish an event through server. */
	public void publish(String aSubject, Hashtable theAttributes) throws PushletException {
		throwOnInvalidSession();
		Event event = new Event(E_PUBLISH, theAttributes);
		event.setField(P_SUBJECT, aSubject);
		event.setField(P_ID, id);
		Event response = postEvent(event);
		throwOnNack(response);
	}

	/** Subscribes, returning subscription id. */
	public String subscribe(String aSubject, String aLabel) throws PushletException {
		throwOnInvalidSession();
		Event event = new Event(E_SUBSCRIBE);
		event.setField(P_ID, id);
		event.setField(P_SUBJECT, aSubject);

		// Optional label, is returned in data events
		if (aLabel != null) {
			event.setField(P_SUBSCRIPTION_LABEL, aLabel);
		}

		// Send request
		Event response = postEvent(event);
		throwOnNack(response);

		return response.getField(P_SUBSCRIPTION_ID);
	}

	/** Subscribes, returning subscription id. */
	public String subscribe(String aSubject) throws PushletException {
		return subscribe(aSubject, null);
	}

	/** Unsubscribes with subscription id. */
	public void unsubscribe(String aSubscriptionId) throws PushletException {
		throwOnInvalidSession();
		Event event = new Event(E_UNSUBSCRIBE);
		event.setField(P_ID, id);

		// Optional subscription id
		if (aSubscriptionId != null) {
			event.setField(P_SUBSCRIPTION_ID, aSubscriptionId);
		}

		Event response = postEvent(event);
		throwOnNack(response);
	}

	/** Unsubscribes from all subjects. */
	public void unsubscribe() throws PushletException {
		unsubscribe(null);
	}

	/** Stop the listener. */
	public void stopListen() throws PushletException {
		if (dataEventListener != null) {
			unsubscribe();
			dataEventListener.stop();
			dataEventListener = null;
		}
	}

	public void setDebug(boolean b) {
		debug = b;
	}

	private void throwOnNack(Event anEvent) throws PushletException {
		if (anEvent.getEventType().equals(E_NACK)) {
			throw new PushletException("Negative response: reason=" + anEvent.getField(P_REASON));
		}
	}

	private void throwOnInvalidSession() throws PushletException {
		if (id == null) {
			throw new PushletException("Invalid pushlet session");
		}
	}


	private Event postEvent(Event anEvent) throws PushletException {
		HttpConnection hc = null;
		InputStreamReader dis = null;
		DataOutputStream dos = null;

		Event replyEvent = null;
		try {
			// Open up a http connection with the Web server
			// for both send and receive operations
			hc = (HttpConnection) Connector.open(pushletURL, Connector.READ_WRITE);

			// Set the request method to POST
			hc.setRequestMethod(HttpConnection.POST);

			// Send the XML
			dos = hc.openDataOutputStream();
			byte[] requestBody = anEvent.toXML().getBytes();
			dos.write(requestBody);
			dos.flush();
			dos.close();
			dos = null;

			// Retrieve the response back from the servlet
			dis = new InputStreamReader(hc.openInputStream());

			replyEvent = EventParser.parse(dis);
			dis.close();
			dis = null;
		} catch (Throwable t) {
			throw new PushletException("Error in postEvent: " + t);
		} finally {
			// Free up i/o streams and http connection
			try {
				if (hc != null) hc.close();
			} catch (IOException ignored) {
			}
			try {
				if (dis != null) dis.close();
			} catch (IOException ignored) {
			}
			try {
				if (dos != null) dos.close();
			} catch (IOException ignored) {
			}

			return replyEvent;
		}
	}


	/** Util: print. */
	private void p(String s) {
		if (debug) {
			System.out.println("[PushletClient] " + s);
		}
	}

	/** Util: warn. */
	private void warn(String s) {
		warn(s, null);
	}

	/** Util: warn with exception. */
	private void warn(String s, Throwable t) {
		System.err.println("[PushletClient] - WARN - " + s + " ex=" + t);

		if (t != null) {
			t.printStackTrace();
		}
	}

	/** Internal listener for the Pushlet data channel. */
	private class DataEventListener implements Runnable {
		/** Client's listener that gets called back on events. */
		private PushletClientListener listener;

		/** Receiver receiveThread. */
		private Thread receiveThread = null;
		private Reader reader;
		private String refreshURL;
		private String listenURL;

		public DataEventListener(PushletClientListener aListener, String aListenURL) {
			listener = aListener;
			listenURL = aListenURL;
		}

		public void start() {
			// All ok: start a receiver receiveThread
			receiveThread = new Thread(this);
			receiveThread.start();

		}

		/** Stop listening; may restart later with start(). */
		public void stop() {
			p("In stop()");
			bailout();
		}

		/** Receive event objects from server and callback listener. */
		public void run() {
			p("Start run()");
			try {
				while (receiveThread != null && receiveThread.isAlive()) {
					// Connect to server
					reader = openURL(listenURL);

					// Get events while we're alive.
					while (receiveThread != null && receiveThread.isAlive()) {
						Event event = null;
						try {
							// p("Getting event...");
							// Get next event from server
							event = EventParser.parse(reader);
							p("Event received " + event);
						} catch (Throwable t) {

							// Stop and report error.
							// warn("Stop run() on exception", t);
							if (listener != null) {
								listener.onError("exception during receive: " + t);
							}

							bailout();
							return;
						}

						// Handle event by calling listener
						if (event != null && listener != null) {
							// p("received: " + event.toXML());
							String eventType = event.getEventType();
							if (eventType.equals(E_HEARTBEAT)) {
								listener.onHeartbeat(event);
							} else if (eventType.equals(E_DATA)) {
								listener.onData(event);
							} else if (eventType.equals(E_JOIN_LISTEN_ACK)) {
								id = event.getField(P_ID);
							} else if (eventType.equals(E_LISTEN_ACK)) {
								p("Listen ack ok");
							} else if (eventType.equals(E_REFRESH_ACK)) {
								// ignore
							} else if (eventType.equals(E_ABORT)) {
								listener.onAbort(event);
							} else if (eventType.equals(E_REFRESH)) {
								refresh(event);
							} else {
								warn("unsupported event type received: " + eventType);
							}
						}
					}
				}
			} catch (Throwable t) {
				warn("Exception in run() ", t);
				bailout();
			}
		}

		private void disconnect() {
			p("start disconnect()");
			if (reader != null) {
				try {
					// this blocks, find another way
					// reader.close();
					p("Closed reader ok");
				} catch (Exception ignore) {
				} finally {
					reader = null;
				}
			}
			p("end disconnect()");
		}

		private Reader openURL(String aURL) throws PushletException {
			// Open URL connection with server
			try {
				p("Connecting to " + aURL);
				InputStream is = null;
				Throwable err = null;
				try {
					HttpConnection sc = (HttpConnection) Connector.open(aURL);

					is = sc.openInputStream();
				} catch (Throwable th) {
					err = th;
				}

			if (is == null) {
					throw new PushletException("Cannot open or instantiate URL err=" + err);
				}
				return new InputStreamReader(is);

			} catch (Throwable t) {
				warn("openURL() could not open " + aURL, t);
				throw new PushletException(" could not open " + aURL, t);
			}
		}

		/** Stop receiver receiveThread. */
		public void stopThread() {
			p("In stopThread()");

			// Keep a reference such that we can kill it from here.
			Thread targetThread = receiveThread;

			receiveThread = null;

			// This should stop the main loop for this receiveThread.
			// Killing a receiveThread on a blcing read is tricky.
			// See also http://gee.cs.oswego.edu/dl/cpj/cancel.html
			if ((targetThread != null) && targetThread.isAlive()) {

				// targetThread.interrupt();

				try {

					// Wait for it to die
					targetThread.join();
				} catch (InterruptedException ignore) {
				}

				// If current receiveThread refuses to die,
				// take more rigorous methods.
				if (targetThread.isAlive()) {

					// Not preferred but may be needed
					// to stop during a blocking read.
					// targetThread.stop();

					// Wait for it to die
					try {
						targetThread.join();
					} catch (Throwable ignore) {
					}
				}

				p("Stopped receiveThread alive=" + targetThread.isAlive());

			}
		}

		/** Stop listening on stream from server. */
		public void bailout() {
			p("In bailout()");
			stopThread();
			disconnect();
		}

		/** Handle refresh, by pausing. */
		private void refresh(Event aRefreshEvent) throws PushletException {
			try {
				// Wait for specified time.
				Thread.sleep(Long.parseLong(aRefreshEvent.getField(P_WAIT)));
			} catch (Throwable t) {
				warn("abort while refresing");
				refreshURL = null;
				return;
			}

			// If stopped during sleep, don't proceed
			if (receiveThread == null) {
				return;
			}

			// Create url to refresh
			refreshURL = pushletURL
					+ "?" + P_ID + "=" + id
					+ "&" + P_EVENT + "=" + E_REFRESH
					;

			if (reader != null) {
				try {
					reader.close();

				} catch (IOException ignore) {

				}
				reader = null;
			}

			reader = openURL(refreshURL);
		}
	}


}


/*
 * $Log: PushletClient.java,v $
 * Revision 1.3  2005/07/22 16:28:36  justb
 * ok
 *
 * Revision 1.2  2005/05/19 11:17:42  justb
 * comments added
 *
 * Revision 1.1  2005/05/19 11:15:35  justb
 * first version for j2me
 *
 *
 */