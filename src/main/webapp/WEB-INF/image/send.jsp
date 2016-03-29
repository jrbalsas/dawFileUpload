<%-- 
    Document   : send
    Created on : 29-mar-2016, 19:36:34
    Author     : jrbalsas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Upload file</h1>
        <form method="POST" enctype="multipart/form-data">
            <input type="file" name="fileParam">
            <input type="submit" name="Send">
        </form>
        <h2>Available images</h2>

        <c:forEach var="imageName" items="${images}" >
            <p>${imageName}<img alt="${imageName}" src="${imagesUrl}/${imageName}"/></p>
        </c:forEach>
    </body>
</html>
