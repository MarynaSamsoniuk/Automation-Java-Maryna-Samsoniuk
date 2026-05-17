<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Computer Information</title>
</head>
<body>
<div>
    <h1>Computer Information page</h1>
    <%HashMap<String, String> computerInformation = (HashMap<String, String>) request.getAttribute("computerInformation");%>
    <ul>
        <li>Max Memory: <%= computerInformation.get("maxMemory") %></li>
        <li>Total Memory: <%= computerInformation.get("totalMemory") %></li>
        <li>Free Memory: <%= computerInformation.get("freeMemory") %></li>
        <li>OS name: <%= computerInformation.get("osName") %></li>
        <li>Architecture: <%= computerInformation.get("architecture") %></li>
        <li>Processors: <%= computerInformation.get("processors") %></li>
    </ul>
</div>

<div>
    <a href="surname">Go to Surname page</a>
</div>
</body>
</html>
