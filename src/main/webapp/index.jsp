<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    // 重定向到新地址
    //   若是welcome/getWelcomePage，自动添加项目根路径 http://localhost:8080/SSH/   正确
    //   若是/welcome/getWelcomePage， 自动添加路径 http://localhost:8080  错误
    //   若是./welcome/getWelcomePage，补全后的地址为 http://localhost:8080/SSH/welcome/getWelcomePage 正确
    //   若是../welcome/getWelcomePage，补全后的地址为 http://localhost:8080/welcome/getWelcomePage 错误
    //   若是http://localhost:8080/SSH/welcome/getWelcomePage，绝对路径   正确
    String site = new String("getWelcomePage");

    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", site);
%>
</body>
</html>
