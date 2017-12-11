<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="com.edu.Dic"%>
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
<%--		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />--%>
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
	
	
	//保存
	function save(){
<%--			if($("#ID").val()==""){--%>
<%--			$("#ID").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入id',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#ID").focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if($("#STD_NUM").val()==""){--%>
<%--			$("#STD_NUM").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入学号',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#STD_NUM").focus();--%>
<%--			return false;--%>
<%--		}--%>
		if($("#NAME").val()==""){
			$("#NAME").tips({
				side:3,
	            msg:'请输入姓名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NAME").focus();
			return false;
		}
		if(typeof($("input[name='GENDER']:checked").val())=='undefined'){
			$("#GENDER_1").tips({
				side:3,
	            msg:'请选择性别',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#GENDER").focus();
			return false;
		}
		if($("#BIRTHDAY").val()==""){
			$("#BIRTHDAY").tips({
				side:3,
	            msg:'请输入生日',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BIRTHDAY").focus();
			return false;
		}
<%--		if($("#MINZU").val()==""){--%>
<%--			$("#MINZU").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入民族',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#MINZU").focus();--%>
<%--			return false;--%>
<%--		}--%>
		if($("#SCHOOL").val()==""){
			$("#SCHOOL").tips({
				side:3,
	            msg:'请输入学校ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SCHOOL").focus();
			return false;
		}
		if($("#SCHOOL_YEAR").val()==""){
			$("#SCHOOL_YEAR").tips({
				side:3,
	            msg:'请输入入学时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SCHOOL_YEAR").focus();
			return false;
		}
		if($("#CLASS").val()==""){
			$("#CLASS").tips({
				side:3,
	            msg:'请输入班级号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CLASS").focus();
			return false;
		}
		if($("#ROOM_NUM").val()==""){
			$("#ROOM_NUM").tips({
				side:3,
	            msg:'请输入寝室号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ROOM_NUM").focus();
			return false;
		}
<%--		if($("#JR_CLASS").val()==""){--%>
<%--			$("#JR_CLASS").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入吉软班级',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#JR_CLASS").focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if($("#JR_CLASS_NAME").val()==""){--%>
<%--			$("#JR_CLASS_NAME").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入班级名次',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#JR_CLASS_NAME").focus();--%>
<%--			return false;--%>
<%--		}--%>
		if($("#PHONE").val()==""){
			$("#PHONE").tips({
				side:3,
	            msg:'请输入电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PHONE").focus();
			return false;
		}
		if($("#YIXIANG").val()==""){
			$("#YIXIANG").tips({
				side:3,
	            msg:'请输入意向',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#YIXIANG").focus();
			return false;
		}
		if($("#SPECIALITY").val()==""){
			$("#SPECIALITY").tips({
				side:3,
	            msg:'请输入专业ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SPECIALITY").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			$("#EMAIL").tips({
				side:3,
	            msg:'请输入电邮',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#EMAIL").focus();
			return false;
		}
		if($("#QQ").val()==""){
			$("#QQ").tips({
				side:3,
	            msg:'请输入qq',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#QQ").focus();
			return false;
		}
		
		$("#SCHOOL_NAME").val($("#SCHOOL  option:selected").text());
		$("#ENGLISH_NAME").val($("#ENGLISH  option:selected").text());
		$("#SPECIALITY_NAME").val($("#SPECIALITY  option:selected").text());
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="student/${msg }.do" name="Form" id="Form" method="post" class="form-horizontal" >
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td align="center">
				<img alt="" src="<%=basePath %>images/defaultStuImg.jpg">
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="STD_NUM" id="STD_NUM" value="${pd.STD_NUM}" maxlength="32" placeholder="这里输入学号" title="学号"/>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="NAME"><span style="color:#ff0000">*</span>&nbsp;姓名：</label>
				<input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入姓名" title="姓名"/>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="GENDER"><span style="color:#ff0000">*</span>&nbsp;性别：</label>
				<input name="GENDER" id="GENDER_1" value="1" type="radio" <c:if test='${pd.GENDER==1}'>checked</c:if>><span class="lbl">男</span>&nbsp;&nbsp;
				<input name="GENDER" id="GENDER_2" value="2" type="radio" <c:if test='${pd.GENDER==2}'>checked</c:if>><span class="lbl">女</span>
				</td>
			</tr>
			<tr>
				<td>
				<div class="row-fluid input-append date">
				<label class="control-label" for="BIRTHDAY"><span style="color:#ff0000">*</span>&nbsp;生日：</label>
					<input class="date-picker" id="BIRTHDAY" name="BIRTHDAY" type="text" data-date-format="yyyy-mm-dd" value="${pd.BIRTHDAY}"/>
					<span class="add-on"><i class="icon-calendar"></i></span>
				</div>
<%--				<input type="text" name="BIRTHDAY" id="BIRTHDAY" value="${pd.BIRTHDAY}" maxlength="32" placeholder="这里输入生日" title="生日"/></td>--%>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="MINZU_NAME">民族：</label>
				<input type="text" name="MINZU_NAME" id="MINZU_NAME" value="${pd.MINZU_NAME}" maxlength="32" placeholder="这里输入民族" title="民族"/>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="SFZH">身份证号：</label>
				<input type="text" name="SFZH" id="SFZH" value="${pd.SFZH}" maxlength="32" placeholder="这里输入身份证号" title="身份证号"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="SCHOOL"><span style="color:#ff0000">*</span>&nbsp;学校：</label>${pd.SCHOOL}
				<select class="chzn-select" name="SCHOOL" id="SCHOOL" data-placeholder="选择学校">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_school_list}" >
					  <option value='${data.ID}'>${data.NAME}</option>
					  </c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>
				<div class="row-fluid input-append date">
				<label class="control-label" for="SCHOOL_YEAR"><span style="color:#ff0000">*</span>&nbsp;入学时间：</label>
					<input class="date-picker" id="SCHOOL_YEAR" name="SCHOOL_YEAR" type="text" data-date-format="yyyy-mm-dd" value="${pd.SCHOOL_YEAR}"/>
					<span class="add-on"><i class="icon-calendar"></i></span>
				</div>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="CLASS"><span style="color:#ff0000">*</span>&nbsp;班级号：</label>
				<input type="text" name="CLASS" id="CLASS" value="${pd.CLASS}" maxlength="32" placeholder="这里输入班级号" title="班级号"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="ROOM_NUM"><span style="color:#ff0000">*</span>&nbsp;寝室号：</label>
				<input type="text" name="ROOM_NUM" id="ROOM_NUM" value="${pd.ROOM_NUM}" maxlength="32" placeholder="这里输入寝室号" title="寝室号"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="ENGLISH">英语等级：</label>${pd.ENGLISH_NAME}
				<select class="chzn-select" name="ENGLISH" id="ENGLISH" data-placeholder="选择英语等级">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_english_list}" >
					  <option value='${data.ID}'>${data.NAME}</option>
					  </c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="PHONE"><span style="color:#ff0000">*</span>&nbsp;电话：</label>
				<input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="32" placeholder="这里输入电话" title="电话"/>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="QIANDAOKAHAO">签到卡号：</label>
				<input type="text" name="QIANDAOKAHAO" id="QIANDAOKAHAO" value="${pd.QIANDAOKAHAO}" maxlength="32" placeholder="这里输入签到卡号" title="签到卡号"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="PARENT">家长：</label>
				<input type="text" name="PARENT" id="PARENT" value="${pd.PARENT}" maxlength="32" placeholder="这里输入家长" title="家长"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="PARENT_PHONE">家长联系方式：</label>
				<input type="text" name="PARENT_PHONE" id="PARENT_PHONE" value="${pd.PARENT_PHONE}" maxlength="32" placeholder="这里输入家长联系方式" title="家长联系方式"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="JINJI_LXR">紧急联系人：</label>
				<input type="text" name="JINJI_LXR" id="JINJI_LXR" value="${pd.JINJI_LXR}" maxlength="32" placeholder="这里输入紧急联系人" title="紧急联系人"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="JINJI_DH">紧急联系人电话：</label>
				<input type="text" name="JINJI_DH" id="JINJI_DH" value="${pd.JINJI_DH}" maxlength="32" placeholder="这里输入紧急联系人电话" title="紧急联系人电话"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="DAOYUAN">导员：</label>
				<input type="text" name="DAOYUAN" id="DAOYUAN" value="${pd.DAOYUAN}" maxlength="32" placeholder="这里输入导员" title="导员"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="DAOYUAN_DH">导员电话：</label>
				<input type="text" name="DAOYUAN_DH" id="DAOYUAN_DH" value="${pd.DAOYUAN_DH}" maxlength="32" placeholder="这里输入导员电话" title="导员电话"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="YIXIANG">意向：</label>
				<input type="number" name="YIXIANG" id="YIXIANG" value="${pd.YIXIANG}" maxlength="32" placeholder="这里输入意向" title="意向"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="PIC">头像图片：</label>${pd.PIC}
				<input type="file" id="PIC" name="PIC" title="头像图片"/>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="SPECIALITY"><span style="color:#ff0000">*</span>&nbsp;专业：</label>${pd.SPECIALITY}
				<select class="chzn-select" name="SPECIALITY" id="SPECIALITY" data-placeholder="选择专业">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_speciality_list}" >
					  <option value='${data.ID}'>${data.NAME}</option>
					  </c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="EMAIL"><span style="color:#ff0000">*</span>&nbsp;电邮：</label>
				<input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入电邮" title="电邮"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="QQ"><span style="color:#ff0000">*</span>&nbsp;QQ：</label>
				<input type="text" name="QQ" id="QQ" value="${pd.QQ}" maxlength="32" placeholder="这里输入qq" title="qq"/></td>
			</tr>
			<tr>
				<td>
				<label class="control-label" for="REMARK">备注：</label>
				<input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="hidden" name="STATUS" id="STATUS" value="${pd.STATUS}" />
					<input type="hidden" name="DELETED" id="DELETE" value="0" />
					<input type="hidden" name="SCHOOL_NAME" id="SCHOOL_NAME" value=""  />
					<input type="hidden" name="ENGLISH_NAME" id="ENGLISH_NAME" value="" />
					<input type="hidden" name="SPECIALITY_NAME" id="SPECIALITY_NAME" value="" />
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