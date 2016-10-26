<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'loginDemo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="background-image:url(image/login_image1.jpg); background-repeat:no-repeat;background-attachment: fixed;background-size:100% 100%;">
  <div id="login_frame" style="background-image:url(image/login_image1.jpg);background-repeat:no-repeat;background-size:100% 100%;position:relative;left:40%;top:50px;width:35%;height:50%;opacity:0.5;box-shadow:20px 20px 10px #444;"></div>
  </body>
</html>
