<HTML>
<HEAD>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
   <meta http-equiv="Pragma" content="no-cache">
</HEAD>
<BODY BGCOLOR="blue">
<% 
  /** Start a line of JavaScript with a function call to parent frame. */
  String jsFunPre = "<script language=JavaScript >parent.push('";
  
  /** End the line of JavaScript */
  String jsFunPost = "')</script> ";
  
  try {
  
    // Every three seconds a line of JavaScript is pushed to the client
     for (int i=1; i < 50; i++) {
       out.print(jsFunPre+" "+i+jsFunPost);
       out.flush();
     }
   for (int i=1; i < 10; i++) {
    
       // Push a line of JavaScript to the client 
       out.print(jsFunPre+"Page "+i+jsFunPost);
       out.flush();
       
       // Sleep three secs
       try {
            Thread.sleep(3000);
       } catch (InterruptedException e) {
            // Let client display exception 
            out.print(jsFunPre+"InterruptedException: "+e+jsFunPost);
       }
     }
   } catch (Exception e) {
            // Let client display exception 
            out.print(jsFunPre+"Exception: "+e+jsFunPost);
   }
   out.print(jsFunPre+"DONE "+jsFunPost);

%>
</BODY>
</HTML>
