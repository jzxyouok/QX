// Copyright (c) 2004 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.


package nl.justobjects.pushlet.j2me;

/**
 * Constants for Pushlet protocols.
 *
 * @version $Id: Protocol.java,v 1.2 2005/05/19 11:17:42 justb Exp $
 * @author Just van den Broecke - Just Objects &copy;
 **/
public interface Protocol {
	/** Default URI . */
	public static final String DEFAULT_SERVLET_URI = "/pushlet/pushlet.srv";

	//
	// Common protocol header/parameter names
	//

	/** Event type (join, leave, data, subscribe etc) . */
	public static final String P_EVENT = "p_event";

	/** Time in seconds since 1970 */
	public static final String P_TIME = "p_time";

	/** Event sequence number, numbers per-client. */
	public static final String P_SEQ = "p_seq";

	/** Subject (topic) of data event. */
	public static final String P_SUBJECT = "p_subject";

	/** Originator of Event. */
	public static final String P_FROM = "p_from";

	/** Addressee of Event, subject or client p_id. */
	public static final String P_TO = "p_to";

	/** Identifier for client instance within server. */
	public static final String P_ID = "p_id";

	/** Subscription id, identifies single subscription. */
	public static final String P_SUBSCRIPTION_ID = "p_sid";

	/** Format to receive events */
	public static final String P_FORMAT = "p_format";

	/** Protocol mode. */
	public static final String P_MODE = "p_mode";

	/** Reason for errors. */
	public static final String P_REASON = "p_reason";

	/** URL attribute. */
	public static final String P_URL = "p_url";

	/** Wait attribute. */
	public static final String P_WAIT = "p_wait";

	/**
	 * Subscription label, may be used to return user-specific
	 * token with a data event, e.g. the name of a function for a callback.
	 */
	public static final String P_SUBSCRIPTION_LABEL = "p_label";

	//
	// Event values with direction for P_EVENT (C=client, S=server)
	//

	/** C-->S Request to join server. */
	public static final String E_JOIN = "join";

	/** S-->C Acknowledgement of join. */
	public static final String E_JOIN_ACK = "join-ack";

	/** C-->S Request to join server. */
	public static final String E_JOIN_LISTEN = "join-listen";

	/** S-->C Acknowledgement of join. */
	public static final String E_JOIN_LISTEN_ACK = "join-listen-ack";

	/** C-->S Client starts listening. */
	public static final String E_LISTEN = "listen";

	/** S-->C Ack of listen. */
	public static final String E_LISTEN_ACK = "listen-ack";

	/** C-->S Client leaves server. */
	public static final String E_LEAVE = "leave";

	/** S-->C Ack of leave. */
	public static final String E_LEAVE_ACK = "leave-ack";

	/** C-->S Publish to subject. */
	public static final String E_PUBLISH = "publish";

	/** S-->C Publish to subject acknowledge. */
	public static final String E_PUBLISH_ACK = "publish-ack";

	/** C-->S Subscribe to subject request. */
	public static final String E_SUBSCRIBE = "subscribe";

	/** S-->C Subscribe to subject acknowledge. */
	public static final String E_SUBSCRIBE_ACK = "subscribe-ack";

	/** C-->S Unsubscribe from subject request. */
	public static final String E_UNSUBSCRIBE = "unsubscribe";

	/** S--C Unsubscribe from subject acknowledge. */
	public static final String E_UNSUBSCRIBE_ACK = "unsubscribe-ack";

	/** S-->C Client error response, transitional error. */
	public static final String E_NACK = "nack";

	/** S-->C Client should abort, permanent error. */
	public static final String E_ABORT = "abort";

	/** S-->C Data. */
	public static final String E_DATA = "data";

	/** S-->C or C-->S Heartbeat. */
	public static final String E_HEARTBEAT = "hb";

	/** S-->C S-->C or C-->S Heartbeat confirmed. */
	public static final String E_HEARTBEAT_ACK = "hb-ack";

	/** S-->C or C-->S client refresh of data channel. */
	public static final String E_REFRESH = "refresh";

	/** S-->C client should refresh data channel. */
	public static final String E_REFRESH_ACK = "refresh-ack";

	//
	// Values for P_FORMAT parameter
	//
	public static String FORMAT_JAVASCRIPT = "js";
	public static String FORMAT_SERIALIZED_JAVA_OBJECT = "ser";
	public static String FORMAT_XML = "xml";

	//
	// Values for P_MODE parameter
	//
	public static final String MODE_STREAM = "stream";
	public static final String MODE_PULL = "pull";
	public static final String MODE_POLL = "poll";

}

/*
  * $Log: Protocol.java,v $
  * Revision 1.2  2005/05/19 11:17:42  justb
  * comments added
  *
  * Revision 1.1  2005/05/19 11:15:35  justb
  * first version for j2me
  *
  *
  */
