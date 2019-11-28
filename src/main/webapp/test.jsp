<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/27
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<form action="uploadServlet" method="post" enctype="multipart/form-data">


    <input type="file" name="filepath">

    <input type="submit" value="上传">
</form>
</body>
</html>
