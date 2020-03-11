<%-- 
    Document   : send
    Author     : jrbalsas@ujaen.es
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
        <h1>Upload file Servlet</h1>

        <form method="POST" enctype="multipart/form-data">
            <input type="file" name="fileParam">
            <input type="submit" name="Send">
        </form>
        <h2>Available images in Folder ${imagesPath}</h2>

        <c:forEach var="imageName" items="${images}" >
            <p>Url: <c:url value='${imagesUrl}/${imageName}'/></p>
            <img alt="${imageName}" src="<c:url value='${imagesUrl}/${imageName}'/>">
        </c:forEach>
        <footer>
            <hr/>
            <p>Made with <span style="color: red;">&#9829;</span> in DAW</p>
        </footer>        

    </body>
</html>
