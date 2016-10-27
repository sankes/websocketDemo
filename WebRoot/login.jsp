<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录界面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="imh5/css/bootstrap.css" />
  </head>
  
  <body style="background-image:url(image/login.jpg); background-repeat:no-repeat;background-attachment: fixed;background-size:100% 100%;">
  <div style="opacity:0.6;position:absolute;left:42%;top:25%;border-radius:10vw;background-image:url(image/login_imge.jpg) ;background-size:100% 100%;width:18%;height:41%; box-shadow:0 0 30px 10px rgba(255,255,255,.7) inset;"></div>
  <div id="login_frame" style="border-radius:10px;background-image:url(image/login_imge.jpg);background-repeat:no-repeat;background-size:100% 100%;position:relative;left:30%;top:70%;width:40.5%;height:10%;opacity:0.60; ;box-shadow:20px 20px 10px #444;"></div>
  <input id="account" type="text" style="padding-left:2.3vw;border-radius:0.5vw;position:relative;left:32%;top:62%;width:13%;height:6%;font-size: 1.2vw;"  placeholder="请输入账号">
  <input id="password" type="password" style="padding-left:2.3vw;border-radius:0.5vw;position:relative;left:34%;top:62%;width:13%;height:6%;font-size: 1.2vw;"  placeholder="请输入密码">
  <input onclick="loginCheck()" type="button" value="LOGIN" style="border-radius:0.6vw;position:relative;left:35%;top:62%;width:7%;height:6%;background:url(image/login_imge.jpg);background-size:50% 100%;font-size: 1.2vw; font-weight: 600; border:none;font-family:yahei">
  <img src="image/account.png" style="position:absolute;left:32.5%;top:72%;width:2.3vw;opacity:0.9;">
  <img src="image/password.png" style="position:absolute;left:47.5%;top:72%;width:2.3vw;opacity:0.8;">
  </body>
  <script>
  	var xmlHttp;      
	 function createXmlHttp(){
        if(window.XMLHttpRequest) {
          //针对 Firefox, Chrome, Opera, Safari,IE7,IE8
          xmlHttp = new XMLHttpRequest();
        } else if(window.ActiveXObject) {
          //针对IE6,IE5
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
      }
    function loginCheck(){   
	    //获取xhr对象  
	   	 createXmlHttp();
	    //规定请求类型       
	     xmlHttp.open("post","loginCheck.action",true);  
	    //设置头信息  
	     xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded")  
	 	//发送参数  
	 	var account=document.getElementById("account").value;
	 	var password=document.getElementById("password").value;
	 	xmlHttp.send("account="+account+"&password="+password);  
		//相应事件处理  
	   xmlHttp.onreadystatechange = callBack;
	  //判读是否处理完毕 与判读服务器是否处理成功！  
	}  
	 function callBack() {
        if(xmlHttp.readyState == 4) {
          //判断Http的交互是否成功
          if(xmlHttp.status == 200) {
            var responseText = xmlHttp.responseText;
            responseText = responseText.replace(/\s/g,"");//去除返回值中的空格
            if(responseText=="SUCCESS"){
            	window.location.href = "/websocketDemo/systemPage.jsp";
            }else{
            	alert("用户的账号不存在或用户密码错误");
            }
           
          }
        }
      }
  </script>
</html>
