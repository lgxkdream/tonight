<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.raty.js"></script>
		
		
<script type="text/javascript">
	//保存
	function save(){
/*
 * 创建时间
 */
		if($("#END_TIME").val()==""){
			$("#END_TIME").tips({
				side:3,
	            msg:'请输入截止时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#END_TIME").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="studentHomework/saveHomework.do" name="Form" id="Form" method="post">
<%--		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>--%>
		<input type="hidden" name="CLASS_ID" id="CLASS_ID" value="${pd.ID}">
		<div id="zhongxin" align="center" style="margin-top: 25px">
		<table>
			<tr>
				<td>
					<table height="100%" width="100%">
							<tr align="center">
								<td align="right">
									<span style="font-size: 20px" >班&nbsp;&nbsp;级&nbsp;&nbsp;名：</span>
								</td>
								<td align="left">
									<input align="left" type="text" name="CLASS_NAME" readonly id="CLASS_NAME" value="${classPd.NAME}" maxlength="32"  title="班级"/>
									&nbsp;<span style="color:#ff0000">*</span>
								</td>
							</tr>
							<tr align="center">
								<td align="right">
									<span style="font-size: 20px" >作业标题：</span>
								</td>
								<td align="left">
									<input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" placeholder="这里输入标题" title="标题"/>
								</td>
							</tr>
							<tr align="center">
								<td align="right">
									<span style="font-size: 20px" >截止日期：</span>
								</td>
								<td align="left">
								<div class="row-fluid input-append date">
									<input class="date-picker" id="END_TIME" name="END_TIME" type="text" data-date-format="yyyy-mm-dd" value="${pd.END_TIME}" placeholder="截止日期" title="截止日期"/>
									<span class="add-on"><i class="icon-calendar"></i></span>
									&nbsp;<span style="color:#ff0000">*</span>
								</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			<tr>
				<td style="text-align: center;">
				
					<iframe src="<%=basePath%>studentHomework/HomeworkTemplates" width="800px" height="300px"></iframe>
					<input type="hidden" id="homework_ids" name="homework_ids" align="center">
					
					<a class="btn btn-mini btn-primary" onclick="save();">布置作业</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
<%--		function changPage(){--%>
<%--		$("#Form1").submit();--%>
<%--		}--%>
		
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
		});
			$(document).ready(function(){
		  $("#picFile").change(function(){
		    ajaxFileUpload();
		  });
		});
		rat('star','result',1);
		</script>
</body>
</html>