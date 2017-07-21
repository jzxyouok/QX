// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.


package nl.justobjects.pushlet.j2me;

/**
 * Interface for listener of the PushletClient object.
 *
 * @version $Id: PushletClientListener.java,v 1.2 2005/05/19 11:17:42 justb Exp $
 * @author Just van den Broecke - Just Objects &copy;
 **/
public interface PushletClientListener extends Protocol {
	/** Abort event from server. */
	public void onAbort(Event theEvent);

	/** Data event from server. */
	public void onData(Event theEvent);

	/** Heartbeat event from server. */
	public void onHeartbeat(Event theEvent);

	/** Error occurred. */
	public void onError(String message);
}

/*
* $Log: PushletClientListener.java,v $
* Revision 1.2  2005/05/19 11:17:42  justb
* comments added
*
* Revision 1.1  2005/05/19 11:15:35  justb
* first version for j2me
*/