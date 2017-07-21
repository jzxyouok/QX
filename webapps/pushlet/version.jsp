<%@ page import="nl.justobjects.pushlet.Version"%>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <meta http-equiv="Pragma" content="no-cache">
   <meta name="Author" content="Just van den Broecke">

   <link href="assets/style.css" rel="stylesheet">
   <title>Pushlet Version Info</title>
</head>
<body>
<h2>Version Info</h2>
This server runs pushlet version
<b><%= Version.SOFTWARE_VERSION %></b>
built on
<b><%= Version.BUILD_DATE %></b>.
</body>
</html>
