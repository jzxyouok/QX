// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.

package nl.justobjects.pushlet.j2me;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Represents the event data.
 *
 * @version $Id: Event.java,v 1.2 2005/05/19 11:17:42 justb Exp $
 * @author Just van den Broecke - Just Objects &copy;
 **/
public class Event implements Protocol {
	protected Hashtable attributes = new Hashtable(3);

	public Event(String anEventType) {
		this(anEventType, null);
	}

	public Event(String anEventType, Hashtable theAttributes) {

		if (theAttributes != null) {
			setAttrs(theAttributes);
		}

		// Set required field event type
		setField(P_EVENT, anEventType);

		// Set time in seconds since 1970
		setField(P_TIME, System.currentTimeMillis() / 1000);
	}

	public Event(Hashtable theAttributes) {
		if (!theAttributes.containsKey(P_EVENT)) {
			throw new IllegalArgumentException(P_EVENT + " not found in attributes");
		}
		setAttrs(theAttributes);
	}

	public static Event createDataEvent(String aSubject) {
		return createDataEvent(aSubject, null);
	}

	public static Event createDataEvent(String aSubject, Hashtable theAttributes) {
		Event dataEvent = new Event(E_DATA, theAttributes);
		dataEvent.setField(P_SUBJECT, aSubject);
		return dataEvent;
	}

	public String getEventType() {
		return getField(P_EVENT);
	}

	public String getSubject() {
		return getField(P_SUBJECT);
	}

	public void setField(String name, String value) {
		attributes.put(name, value);
	}

	public void setField(String name, int value) {
		attributes.put(name, value + "");
	}

	public void setField(String name, long value) {
		attributes.put(name, value + "");
	}

	public String getField(String name) {
		return (String) attributes.get(name);
	}

	/** Return field; if null return default. */
	public String getField(String name, String aDefault) {
		String result = getField(name);
		return result == null ? aDefault : result;
	}

	public Enumeration getFieldNames() {
		return attributes.keys();
	}

	public String toString() {
		return attributes.toString();
	}

	/** Convert to HTTP query string. */
	public String toQueryString() {
		String queryString = "";
		String amp = "";
		for (Enumeration iter = getFieldNames(); iter.hasMoreElements();) {
			String nextAttrName = (String) iter.nextElement();
			String nextAttrValue = getField(nextAttrName);
			queryString = queryString + amp + nextAttrName + "=" + nextAttrValue;
			// After first add "&".
			amp = "&";
		}

		return queryString;
	}

	public String toXML() {
		String xmlString = "<event ";
		for (Enumeration iter = getFieldNames(); iter.hasMoreElements();) {
			String nextAttrName = (String) iter.nextElement();
			String nextAttrValue = getField(nextAttrName);
			xmlString = xmlString + nextAttrName + "=\"" + nextAttrValue + "\" ";
		}

		xmlString += "/>";
		return xmlString;
	}

	public Object clone() {
		// Clone the Event by using copy constructor
		return new Event(attributes);
	}

	/** Copy given attributes into event attributes */
	private void setAttrs(Hashtable theAttributes) {
		for (Enumeration iter = theAttributes.keys(); iter.hasMoreElements();) {
			String nextAttrName = (String) iter.nextElement();
			attributes.put(nextAttrName, theAttributes.get(nextAttrName));
		}

	}
}

/*
  * $Log: Event.java,v $
  * Revision 1.2  2005/05/19 11:17:42  justb
  * comments added
  *
  * Revision 1.1  2005/05/19 11:15:35  justb
  * first version for j2me
  *
  */
