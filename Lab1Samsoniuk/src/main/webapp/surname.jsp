<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Surname</title>
</head>
<body>
<div>
    <h1>Surname page</h1>
    <%String surname = (String) request.getAttribute("surname");%>
    <h2>My surname: <%= surname %></h2>
</div>

<div>
    <a href="computer-info">Go to Computer Information page</a>
</div>
</body>
</html>