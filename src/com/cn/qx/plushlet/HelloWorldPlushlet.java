package com.cn.qx.plushlet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

public class HelloWorldPlushlet extends EventPullSource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected long getSleepTime() {
		return 10000;
	}

	@Override
	protected Event pullEvent() {
		Event event =Event.createDataEvent("laoduliu"); 
		Event event1 = Event.createDataEvent("fuckyou"); 
		try {
			event.setField("hw",new String("大幅度发给".getBytes("UTF-8"), "ISO-8859-1"));
			event1.setField("hw",new String("爱的方式大幅度上升到".getBytes("UTF-8"), "ISO-8859-1"));
			Dispatcher.getInstance().broadcast(event);
			Dispatcher.getInstance().broadcast(event1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}
}
