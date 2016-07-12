<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Login</title>
</head>


	<c:if test="${logout == true}">
		<b class="logout">You have been logged out.</b>
	</c:if>
	<c:url value="/login" var="loginUrl"/>
	<form action="${loginUrl}" method="post" modelAttribute="user">
		Username: <input type="text" id="username" name="username" placeholder=""><br>
		Password: <input type="password" id="password" name="password" placeholder=""><br>
		
		<input type="hidden"
		name="${_csrf.parameterName}"
		value="${_csrf.token}"/>
		<button type="submit">Login</button>
	        <c:if test="${error == true}">
				&nbsp;&nbsp;&nbsp; <font color="red">Invalid username or password</font>
			</c:if>
	</form>
</body>
</html>