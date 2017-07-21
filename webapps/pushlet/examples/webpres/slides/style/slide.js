// 
// Slide navigation a la PowerPoint: captures keystrokes and changes pages
//
// Only works with NS4 and IE4.
//
//  $Author: justb $
//  $Id: slide.js,v 1.2 2006/05/25 10:47:04 justb Exp $
//  $Log: slide.js,v $
//  Revision 1.2  2006/05/25 10:47:04  justb
//  replaced JSP js-pushlet-client.jsp with JS js-pushlet-client.js
//
//  Revision 1.1  2003/04/30 22:14:31  justb
//  ok
//
//  Revision 1.1  2001/08/01 13:10:13  kstroke
//  moving to new sholycow structure
//
//  Revision 1.5  2000/10/19 13:43:46  jacco
//  added support for moz5
//
//  Revision 1.4  2000/03/31 21:49:19  just
//  resolved bug in text note toggle
//
//  Revision 1.3  2000/03/31 11:10:20  just
//  added text note toggle
//
//  Revision 1.2  2000/03/01 01:27:53  just
//  added JS for note popup in slides
//
//  Revision 1.1  2000/02/17 14:28:21  gerald
//  moved to this server
//
//  Revision 1.2  1999/10/22 12:26:31  just
//  no real changes
//
//  Revision 1.1  1999/07/28 15:07:12  just
//  new
//
//  Revision 1.4  1999/06/08 13:58:11  just
//  misc small cosmetics
//
//  Revision 1.3  1999/05/31 22:41:31  just
//  added navigation  for IE4
//
//

// Global variables
var isNav, isIE, isMoz
var coll = ""
var styleObj = ""

// Which browser do we have this time ?
if (parseInt(navigator.appVersion) >= 4)
{
	if (navigator.appName.match(/Netscape/))
	{
		isNav = true
		if(parseInt(navigator.appVersion) > 4)
		{
			isMoz = true;
		}
 		document.captureEvents(Event.KEYPRESS)
		document.onkeypress = nsEventDispatcher
	}
	else
	{
		isIE = true
		coll = "all."
		styleObj = ".style"
	}
}



// Switch the page
function gotoPage(newPage) {
    if (isIE) {
        window.location = newPage;
    } else {
        document.location.href = newPage
     }
}

var notesOn=false;

// Switch the page
function toggleNotes() {
	var notes = getObject("notes");
	if (!notes) {
		alert('no notes in this slide')
		return;
	}
	if (!notesOn) {
		center(notes)
		//notes.style.position = 'relative'
		//notes.style.width = '300'
		//alert(notes.style.top)
		show(notes)
		notesOn = true;
	} else {
		hide(notes)
		notesOn = false;
	}
}

// Callback when KEYPRESS event captured by NS browser
function nsEventDispatcher(evt) {
  pageChanger(evt.which)
}

// Callback when KEYPRESS event captured by IE browser
function eventDispatcher() {
	if(!isNav)
	{
		pageChanger(window.event.keyCode)
	}
}

// Generic page switcher.
// Cursor images at the top right of slide determine the page to go to.
function pageChanger(keyCode) {

    switch (keyCode) {
    // next page
    case 32: // space
    case 110: // N
         gotoPage(document.links[2].href)	
	 break;

    // prev page
    case 98: // key B
    case 112: // key P
	gotoPage(document.links[0].href)
	break;

    // level up (key i=index)
    case 105: // I
	gotoPage(document.links[1].href)
	break;

    case 116: // t (textnotes)
	toggleNotes()
	break;
	
    // help or any other key
    case 104: // H
	alert("SLIDE NAVIGATION\nuse the following keys:\n\nnext-slide: space or n\nprev-slide: p or b\nlevel-up: i\ntext notes toggle: t")	
	break;
	
    default:
	return false;
    }
    return true;
}

/************************* INCLUDE OF API.JS BY JUST *********************************/

/****************************************************************
 *  Copyright (c) 1999 Just Objects B.V. <just@justobjects.nl>      *
 *  All rights reserved. See the LICENSE for usage conditions.  *
 ***************************************************************/

// DHTMLapi.js custom API for cross-platform
// Adapted by Just from Original version by Danny Goodman (http://www.dannyg.com)


// Convert object name string or object reference
// into a valid object reference
function getObject(obj)
{
	var theObj
	if (typeof obj == "string")
	{
		if(isMoz)
		{
			theObj = document.getElementById(obj)
		}
		else
		{
			theObj = eval("document." + coll + obj + styleObj)
		}
	}
	else
	{
		theObj = obj
	}
	return theObj
}



// Setting the visibility of an object to visible
function show(obj)
{
	var theObj = getObject(obj)
	if(isMoz)
	{
		theObj.style.visibility = "visible"
	}
	else
	{
		theObj.visibility = "visible"
	}
}

// Setting the visibility of an object to hidden
function hide(obj)
{
	var theObj = getObject(obj)
	if(isMoz)
	{
		theObj.style.visibility = "hidden"
	}
	else
	{
		theObj.visibility = "hidden"
	}
}

// center an object
function center(obj)
{
  var theObj = getObject(obj)
  var x = Math.round((getInsideWindowWidth()/2) - (getObjWidth(theObj)/2))
  var y = Math.round((getInsideWindowHeight()/2) - (getObjHeight(theObj)/2))
  shiftTo(theObj, x, y)
}

// Utility function returns height of object in pixels
function getObjHeight(obj) {
	if (isMoz)
	{
		return obj.style.height
	}
	else if(isNav)
	{
		return obj.clip.height
	}
	else
	{
		return obj.clientHeight
	}
}

// Utility function returns width of object in pixels
function getObjWidth(obj)
{
	if(isMoz)
	{
		return obj.style.width
	}
	else if (isNav)
	{
		return obj.clip.width
	}
	else
	{
		return obj.clientWidth
	}
}

// Utility function returns the x coordinate of a positionable object
function getObjLeft(obj)
{
	if (isNav) {
		return obj.left
	} else {
		return obj.pixelLeft
	}
}

// Utility function returns the y coordinate of a positionable object
function getObjTop(obj)  {
	if (isNav) {
		return obj.top
	} else {
		return obj.pixelTop
	}
}
// Utility function returns the available content width space in browser window
function getInsideWindowWidth() {
  if (isNav) {
    //alert("clientWidth="+window.innerWidth)
    return window.innerWidth
  } else {
    //alert("clientWidth="+document.body.clientWidth)
    return document.body.clientWidth
  }
}

// Utility function returns the available content height space in browser window
function getInsideWindowHeight() {
	if (isNav) {
		return window.innerHeight
	} else {
		return document.body.clientHeight
	}
}

// Positioning an object at a specific pixel coordinate
function shiftTo(obj, x, y)
{
	var theObj = getObject(obj)
	if (isMoz)
	{
		theObj.style.left = x
		theObj.style.top = y
	}
	else if(isNav)
	{
		theObj.moveTo(x,y)
	}
	else
	{
		theObj.pixelLeft = x
		theObj.pixelTop = y
	}
}

// Moving an object by x and/or y pixels
function shiftBy(obj, deltaX, deltaY) {
	var theObj = getObject(obj)
	if (isNav) {
		theObj.moveBy(deltaX, deltaY)
	} else {
		theObj.pixelLeft += deltaX
		theObj.pixelTop += deltaY
	}
}

// Setting the z-order of an object
function setZIndex(obj, zOrder) {
	var theObj = getObject(obj)
	theObj.zIndex = zOrder
}

// Setting the background color of an object
function setBGColor(obj, color) {
	var theObj = getObject(obj)
	if (isNav) {
		theObj.bgColor = color
	} else {
		theObj.backgroundColor = color
	}
}

// Setting the foreground color of an object
function setFGColor(obj, color) {
	var theObj = getObject(obj)
	if (isNav) {
		theObj.fgColor = color
	} else {
		theObj.foregroundColor = color
	}
}

// Working with Layers
// This is a bit messy since IE objects refer to the .style object and
// NS to the layer itself.

// Get document object of a layer
function getLayerDocument(layerId) {
	if (isNav) {
		return eval("document."+layerId+".document")
	} else if (isIE) {
		return eval("document.all." + layerId)

	} else {
		alert('layers not supported for your browser')
	}
}

// Write HTML into layer
function setLayerDocumentHTML(layerDocObj, text) {
	var theLayerDocObj;
	
	if (typeof layerDocObj == "string") {
		theLayerDocObj = getLayerDocument(layerDocObj);
	} else {
		theLayerDocObj = layerDocObj
	}

	if (isNav) {
  		theLayerDocObj.writeln(text)
 		theLayerDocObj.close()
	} else if (isIE) {
  		theLayerDocObj.innerHTML = text;
	} else {
		alert('layers not supported for your browser')
	}
}
