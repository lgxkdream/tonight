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
		
<script type="text/javascript">
	
	var hasFile=false;
	<c:if test='${pd.FILE!=null && pd.FILE!=""}'>
		hasFile=true;
	</c:if>
	//保存
	function save(){
		if($("#VERIFY_MONEY").val()==""){
			$("#VERIFY_MONEY").tips({
				side:3,
	            msg:'请输入手续费',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VERIFY_MONEY").focus();
			return false;
		}
		if($("#LOAN_MONEY").val()==""){
			$("#LOAN_MONEY").tips({
				side:3,
	            msg:'请输入贷款金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOAN_MONEY").focus();
			return false;
		}
		//document.getElementById("uploadfile").value 
		if(!hasFile && $("#fujian").val()==""){
			$("#fujian").tips({
				side:3,
	            msg:'请输入附件',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#fujian").focus();
			return false;
		}
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	function deleteFujain(){
		document.getElementById("fujian").style.display="";
		document.getElementById("div_fujian").style.display="none";
	}
</script>
	</head>
<body>
	<form action="payloan/${msg }.do" name="Form" id="Form" method="post"  enctype ="multipart/form-data">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="number" name="VERIFY_MONEY" id="VERIFY_MONEY" value="${pd.VERIFY_MONEY}" maxlength="32" placeholder="这里输入手续费" title="手续费"/></td>
			</tr>
			<tr>
				<td><input type="number" name="LOAN_MONEY" id="LOAN_MONEY" value="${pd.LOAN_MONEY}" maxlength="32" placeholder="这里输入贷款金额" title="贷款金额"/></td>
			</tr>
			<tr>
				<td>
					
					
						<input type="file" id="fujian" name="fujian" title="附件" placeholder="这里选择附件" <c:if test='${pd.FILE!=null && pd.FILE!=""}'>style="display: none;"</c:if>/>	
					<c:if test='${pd.FILE!=null && pd.FILE!=""}'>
						<div id="div_fujian">附件：<a href="<%=basePath %>payloan/download?ID=${pd.ID}">${pd.FILENAME}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deleteFujain()">删除</a>
						</div>
					</c:if>

							
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="hidden" name="STD_ID" value="${pd.STD_ID}">
					<input type="hidden" name="STD_NAME" value="${pd.STD_NAME}">
					<input type="hidden" name="STD_NUM" value="${pd.STD_NUM}">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			
			
		});
		
		</script>
</body>
</html>