<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/base.css" rel="stylesheet">
<title>Username in Use</title>
</head>
<body>

<div id="top">
<h1>
<%
String username = (String) request.getAttribute("username");
out.println("The username \"" + username + "\" is already in use.");
%>
</h1>
</div>

<div id="middle">
<p>Please enter another name and password.<p>
<form action="CreateAccountServlet" method="post">
User Name: <input type="text" name="username"><br>
Password: <input type="password" name="password">
<input type="submit" value="Create Account">
</form>
</div>

</body>
</html>