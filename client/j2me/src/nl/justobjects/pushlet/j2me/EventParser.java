// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.


package nl.justobjects.pushlet.j2me;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

/**
 * Parses XML into Event objects.
 *
 * @version $Id: EventParser.java,v 1.2 2005/05/19 11:17:42 justb Exp $
 * @author Just van den Broecke - Just Objects &copy;
 **/
public class EventParser {


	private EventParser() {
	}


	/** Parse Event from input Reader. */
	public static Event parse(Reader aReader) throws IOException {
		StringBuffer preparsedString = new StringBuffer(24);

		// First find the opening tag ('<')
		char nextChar;
		while ((nextChar = (char) aReader.read()) != '<') ;

		// Append '<'
		preparsedString.append(nextChar);

		// Then find end-tag ('>'), appending all chars to preparsed string.
		do {
			nextChar = (char) aReader.read();
			preparsedString.append(nextChar);
		} while (nextChar != '>');

		return parse(preparsedString.toString());
	}

	/** Parse Event from a String. */
	public static Event parse(String aString) throws IOException {
		aString = aString.trim();

		if (!aString.startsWith("<") || !aString.endsWith("/>")) {
			throw new IOException("No start or end tag found while parsing event [" + aString + "]");
		}

		// Create the attributes object.
		Hashtable properties = new Hashtable(3);

		// Remove the start and end (< ... />) from the string
		aString = aString.substring(1, aString.length() - 2).trim();

		int index = 0;

		// Parse the tag
		while (!isWhitespace(aString.charAt(index))
				&& (index < aString.length())) {
			index++;
		}

		// We don't use the tag: remove from string
		aString = aString.substring(index).trim();
		index = 0;

		String attrName;
		String attrValue;

		while (index < aString.length()) {

			// Parse attribute name
			while ((aString.charAt(index) != '=')
					&& (index < aString.length())) {
				index++;
			}

			// Create attr name string
			attrName = aString.substring(0, index).trim();

			// remove the attributeName and the '=' from the string
			aString = aString.substring(index + 1).trim();
			index = 1;    // read past the first wrapping "\""

			// Parse attribute value
			while ((aString.charAt(index) != '\"')
					&& (index < aString.length())) {

				// bypass the special characters '\' and '"' inside the
				// attributevalue itself which are deliniated with a preceding
				// '\'
				if (aString.charAt(index) == '\\') {
					aString = aString.substring(0, index)
							+ aString.substring(index + 1);    // remove the '\'
				}

				index++;
			}

			// create the attribute value; exclude the wrapping quote-characters
			attrValue = aString.substring(1, index);

			// Set the attribute N/V
			properties.put(attrName, attrValue);

			aString = aString.substring(index + 1).trim();
			index = 0;
		}

		return new Event(properties);
	}

	/** Reimplement since Character.isWhitespace() is not in MIDP API. */
	private static boolean isWhitespace(char aChar) {
		return (aChar == ' ' || aChar == '\t' || aChar == '\n' || aChar == '\r');
	}
}

/*
  * $Log: EventParser.java,v $
  * Revision 1.2  2005/05/19 11:17:42  justb
  * comments added
  *
  * Revision 1.1  2005/05/19 11:15:35  justb
  * first version for j2me
  *
  */
