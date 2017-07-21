<!--
  Ping OK logger.

  $Id: pingok.jsp,v 1.7 2005/02/14 16:19:54 justb Exp $
  author: Just van den Broecke

-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PingOK</title>
    <style type="text/css">
      body {
	    color: white;
	    background-color: #003366;
	    font-family: Verdana, Geneva, Arial, sans-serif;
        font-size: 12px;
      }
  </style>
</head>
<body>
<%
String mode = request.getParameter("mode");
System.out.println("V2PING OK [date="+(new java.util.Date().toString())+" addr="+request.getRemoteAddr() + " mode=" + mode + " agent="+request.getHeader("User-Agent") + "]");
%>
<h2>It Works !!!</h2>
<p>
Hi there from <%=request.getRemoteAddr() %>.  <br/>
Pushlets work in your browser too !!
</p>
<p>
The event transfer mode for your browser/connection is <b><%= mode %></b>
</p>
<p>
Your browser type is:
</p>
<p>
<b> <%=request.getHeader("User-Agent")%> </b>
</p>
<p>
Thanks for trying Pushlets.
</p>
</body>
</html>
