<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 style="text-align:center;">Run Command</h1>
<form:form action="executeCommand" method="post" modelAttribute="commander">
<h3>Select the Command</h3>
<form:select path="command">
<form:options items="${commandMap}"/>
</form:select>
<br/><br/>
<form:textarea path="textArea"/>
<br/><br/>
<input type="submit" value="execute"/>
<br><br>
command is: ${commander.command}
<br><br>
output is: ${commander.textArea}
<br></br>

</form:form>
</body>
</html>