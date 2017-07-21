<HTML>
<HEAD>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
   <meta http-equiv="Pragma" content="no-cache">
</HEAD>
<BODY BGCOLOR="blue" TEXT="white">
<% 
  try {
    for (int i=1; i < 10; i++) {
       out.print("<h1>"+i+"</h1>");
       out.flush();
      
       try {
            Thread.sleep(3000);
       } catch (InterruptedException e) {
            out.print("<h1>"+e+"</h1>");
       }
     }
   } catch (Exception e) {
       out.print("<h1>"+e+"</h1>");
   }
   out.print("<h1>DONE</h1>");

%>
</BODY>
</HTML>
