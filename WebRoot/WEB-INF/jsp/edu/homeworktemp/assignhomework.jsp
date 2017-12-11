<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 确认窗口 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<body>
	<form action="homeworkTemplet/edit.do" method="post" name="Form" id="Form">
		<input type="hidden" id="HOMEWORK_IDS" name="HOMEWORK_IDS" value="${pd.HOMEWORK_IDS}">
		<input type="hidden" id="ID" name="ID" value="${pd.ID}">
		<div id="zhongxin">
		<iframe src="homeworkTemplet/goAssignHomework.do"  width="100%" height="570" name="iframe" scrolling="no" frameborder="no"></iframe>
		<br>
		<a class="btn btn-mini btn-primary" onclick="edit();">分配题目</a>
		<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		</div>
		<div id="zhongxin2" class="center" style="display:none">
			<img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>
	</form>
	
	<!-- 返回顶部  -->
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());
		//修改
		function edit(){
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	</script>
</body>
</html>

