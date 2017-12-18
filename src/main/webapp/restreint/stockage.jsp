<%@ page import="com.chat.modele.Salon" %>
<%@ page import="com.chat.util.DataException" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page import="org.springframework.web.context.support.SpringBeanAutowiringSupport"%>
<%@ page import="com.chat.service.ChatMessageService" %>
<%!
	public void jspInit()
	{
		ServletConfig config = getServletConfig();

		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	@Autowired
	private ChatMessageService gestion;
%>

<!DOCTYPE html5">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stockage</title>
</head>
<body>

<%

out.println("<HTML>\n<BODY>---Liste des salons: <br><br>"); 

for (Salon salon : gestion.getMap().keySet() ) {
	/* TODO voir comment prend le salon du session ou un truck comme ça*/
	 out.println("--"+salon.getName()+" :<br>");
	try {
		for (int i=0; i<gestion.nombreMessage(salon.getName()) ;i++ ){

               out.println("  <LI>"+ gestion.getMessages(salon.getName()).get(i).getUser()+": "
                   + gestion.getMessages(salon.getName()).get(i).getContenu() + "\n" +
                       "</UL>" );
               }
	} catch (DataException e) {
		/* TODO ameliorer ça*/
		e.printStackTrace();
	}
	out.println("</BODY><br></HTML>");
	 } 
 
%>

</body>
</html>