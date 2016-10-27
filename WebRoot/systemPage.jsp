<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>main</title>
<link rel="stylesheet" href="css/demo.css">
<link rel="stylesheet" href="css/jquery.flipster.css">
<link rel="stylesheet" href="css/flipsternavtabs.css">
</head>
<body>

<!-- 代码部分begin -->
<div class="flipster">
	<ul>
		<li>
			<a href="#" class="Button Block" style="background-image:url(image/login.jpg); background-repeat:no-repeat;background-attachment: fixed;background-size:100% 100%;">
				<h1>All Features</h1>
				<p>这是一段描述</p>
			</a>
		</li>
		<li>
			<a href="#" class="Button Block">
				<h1>Basic Setup</h1>
				<p>The bare minimum code needed to implement Flipster</p>
			</a>
		</li>
		<li>
			<a href="#" class="Button Block">
				<h1>Carousel</h1>
				<p>Roundabout carousel style!</p>
			</a>
		</li>
		<li>
			<a href="#" class="Button Block">
				<h1>Coverflow with Tab Navigation</h1>
				<p>Example of tab navigation using the <code>enableNav</code> option</p>
			</a>
		</li>
		<li>
			<a href="#" class="Button Block">
				<h1>Carousel with Tab Navigation</h1>
				<p>Example of tab navigation using the <code>enableNav</code> option</p>
			</a>
		</li>
  	</ul>
</div>
<img src="image/shumiao.png" style="">
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jquery.flipster.js"></script>
<script>
$(function(){ 
	$(".flipster").flipster({ 
		style: 'carousel', 
		start: 0 }); 
	});
</script>
<!-- 代码部分end -->

</body>
</html>