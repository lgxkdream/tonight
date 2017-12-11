<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="com.edu.Dic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	int yixiangInit=2;
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
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.raty.js"></script>
		
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
<%--		if($("#BIRTHDAY").val()==""){--%>
<%--			$("#BIRTHDAY").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入生日',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#BIRTHDAY").focus();--%>
<%--			return false;--%>
<%--		}--%>
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
	            msg:'请选择学校',
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
<%--		if($("#CLASS").val()==""){--%>
<%--			$("#CLASS").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入班级号',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#CLASS").focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if($("#ROOM_NUM").val()==""){--%>
<%--			$("#ROOM_NUM").tips({--%>
<%--				side:3,--%>
<%--	            msg:'请输入寝室号',--%>
<%--	            bg:'#AE81FF',--%>
<%--	            time:2--%>
<%--	        });--%>
<%--			$("#ROOM_NUM").focus();--%>
<%--			return false;--%>
<%--		}--%>
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
		$("#STATUS_NAME").val($("#STATUS  option:selected").text());
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	function ajaxFileUpload(){
		$("#save_a").attr({"disabled":"disabled"});
		var elementIds=["flag"]; //flag为id、name属性名
<%--		$.ajaxFileUpload({--%>
<%--            url: '<%=basePath%>student/upload', --%>
<%--            type: 'post',--%>
<%--            secureuri: false, //一般设置为false--%>
<%--            fileElementId: 'picFile', // 上传文件的id、name属性名--%>
<%--            dataType: 'text', //返回值类型，一般设置为json、application/json--%>
<%--            elementIds: elementIds, //传递参数到服务器--%>
<%--            success: function(data, status){  --%>
<%--                alert(data);--%>
<%--            },--%>
<%--            error: function(data, status, e){ --%>
<%--                alert(e);--%>
<%--            }--%>
<%--        });--%>

		//开始上传文件时显示一个图片,文件上传完成将图片隐藏
    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
    //执行上传文件操作的函数
    $.ajaxFileUpload({
        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
        url:'<%=basePath%>student/upload',
        secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:'picFile',           //文件选择框的id属性
        dataType:'json',                       //服务器返回的格式,可以是json或xml等
        success:function(data, status){        //服务器响应成功时的处理函数
        	if(data.RESULT==1){
        		var path='<%=basePath%>'+data.PATH;
        		$("#picImg").attr("src", path); 
        		$("#pic").val(data.PATH); 
        	}else{
        		alert(data.MSG);
        	}
       		$("#save_a").removeAttr("disabled");
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            alert('图片上传失败，请重试！！');
        	$("#save_a").removeAttr("disabled");
        }
    });
	}
	function rat(star,result,m){
		var score=<c:choose ><c:when test='${pd.ID!=null && pd.ID!=""}'>${pd.YIXIANG}</c:when><c:otherwise ><%=yixiangInit%></c:otherwise></c:choose>
		
		star= '#' + star;
		result= '#' + result;
		$(result).hide();//将结果DIV隐藏
		$(star).raty({
			hints: ['1','2', '3', '4', '5'],
			path: "images",
			starOff: 'star-off-big.png',
			starOn: 'star-on-big.png',
			size: 24,
			start: 4,
			number: 5,
			score: score,
			showHalf: true,
			target: result,
			targetKeep : true,//targetKeep 属性设置为true，用户的选择值才会被保持在目标DIV中，否则只是鼠标悬停时有值，而鼠标离开后这个值就会消失
			click: function (score, evt) {
				//第一种方式：直接取值
				//alert('你的评分是'+score*m+'分');
				$("#YIXIANG").val(score*m);
			}
		});
	}
	
	
</script>
	</head>
<body>
	<form action="student/${msg }.do" name="Form" id="Form" method="post" class="form-horizontal" >
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td>
				<input type="text" name="STD_NUM" id="STD_NUM" value="${pd.STD_NUM}" maxlength="32" placeholder="学号" title="学号" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入姓名" title="姓名"/>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<input name="GENDER" id="GENDER_1" value="1" type="radio" <c:if test='${pd.GENDER==1}'>checked</c:if>><span class="lbl">男</span>&nbsp;&nbsp;
				<input name="GENDER" id="GENDER_2" value="2" type="radio" <c:if test='${pd.GENDER==2}'>checked</c:if>><span class="lbl">女</span>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<div class="row-fluid input-append date">
					<input class="date-picker" id="BIRTHDAY" name="BIRTHDAY" type="text" data-date-format="yyyy-mm-dd" value="${pd.BIRTHDAY}" placeholder="出生日期" title="出生日期"/>
					<span class="add-on"><i class="icon-calendar"></i></span>
					&nbsp;<span style="color:#ff0000">*</span>
				</div>
			</tr>
			<tr>
				<td>
				<select class="chzn-select" name="SCHOOL" id="SCHOOL" data-placeholder="选择学校">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_school_list}" >
					  <option value='${data.ID}' <c:if test='${data.ID==pd.SCHOOL}'>selected</c:if>>${data.NAME}</option>
					  </c:forEach>
				</select>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<select class="chzn-select" name="SCHOOL_YEAR" id="SCHOOL_YEAR" data-placeholder="入学年份">
					  <option value=""></option>
					  <c:forEach var="data" items="${schoolYearList}" >
					  <option value='${data}' <c:if test='${data==pd.SCHOOL_YEAR}'>selected</c:if>>${data}</option>
					  </c:forEach>
				</select>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="CLASS" id="CLASS" value="${pd.CLASS}" maxlength="32" placeholder="这里输入班级号" title="班级号"/>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="ROOM_NUM" id="ROOM_NUM" value="${pd.ROOM_NUM}" maxlength="32" placeholder="这里输入寝室号" title="寝室号"/>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="32" placeholder="这里输入电话" title="联系电话"/>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<select class="chzn-select" name="SPECIALITY" id="SPECIALITY" data-placeholder="选择专业">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_speciality_list}" >
					  <option value='${data.ID}' <c:if test='${data.ID==pd.SPECIALITY}'>selected</c:if>>${data.NAME}</option>
					  </c:forEach>
				</select>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入电邮" title="邮箱"/>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
					意向：
					<div id="star"></div>
					<div id="result"></div>
				</div>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="QQ" id="QQ" value="${pd.QQ}" maxlength="32" placeholder="这里输入qq" title="联系QQ"/>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<select class="chzn-select" name="STATUS" id="STATUS" data-placeholder="学员状态">
					  <c:forEach var="data" items="${dic_std_status_list}" >
					  <option value='${data.ID}' <c:if test='${data.ID==pd.STATUS}'>selected</c:if>>${data.NAME}</option>
					  </c:forEach>
				</select>
				&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="MINZU_NAME" id="MINZU_NAME" value="${pd.MINZU_NAME}" maxlength="32" placeholder="这里输入民族" title="民族"/>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="SFZH" id="SFZH" value="${pd.SFZH}" maxlength="32" placeholder="这里输入身份证号" title="身份证号"/>
				</td>
			</tr>
			<tr>
				<td>
				<select class="chzn-select" name="ENGLISH" id="ENGLISH" data-placeholder="选择英语等级">
					  <option value=""></option>
					  <c:forEach var="data" items="${dic_english_list}" >
					  <option value='${data.ID}' <c:if test='${data.ID==pd.ENGLISH}'>selected</c:if>>${data.NAME}</option>
					  </c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>
				<input type="text" name="QIANDAOKAHAO" id="QIANDAOKAHAO" value="${pd.QIANDAOKAHAO}" maxlength="32" placeholder="这里输入签到卡号" title="签到卡号"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="PARENT" id="PARENT" value="${pd.PARENT}" maxlength="32" placeholder="这里输入家长" title="家长"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="PARENT_PHONE" id="PARENT_PHONE" value="${pd.PARENT_PHONE}" maxlength="32" placeholder="这里输入家长联系方式" title="家长联系方式"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="JINJI_LXR" id="JINJI_LXR" value="${pd.JINJI_LXR}" maxlength="32" placeholder="这里输入紧急联系人" title="紧急联系人"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="JINJI_DH" id="JINJI_DH" value="${pd.JINJI_DH}" maxlength="32" placeholder="这里输入紧急联系人电话" title="紧急联系人电话"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="DAOYUAN" id="DAOYUAN" value="${pd.DAOYUAN}" maxlength="32" placeholder="这里输入导员" title="导员"/></td>
			</tr>
			<tr>
				<td>
				<input type="text" name="DAOYUAN_DH" id="DAOYUAN_DH" value="${pd.DAOYUAN_DH}" maxlength="32" placeholder="这里输入导员电话" title="导员电话"/></td>
			</tr>
			<tr>
				<td>
				<textarea type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="32" placeholder="这里输入备注" title="备注"></textarea>
				</td>
			</tr>
			<tr>
				<td>
				<input type="file" id="picFile" name="picFile" title="头像图片" onchange="fileChange()" onclick="fileChange()"/>
				</td>
			</tr>
			<tr>
				<td align="center">
				<c:choose >
				   <c:when test="${pd.PIC!=NULL && pd.PIC!=''}">
				   		<img id="picImg" alt="" src="<%=basePath %>${pd.PIC}" width="151" height="141">
				   </c:when>
				   <c:otherwise >
				   		<img id="picImg" alt="" src="<%=basePath %>images/defaultStuImg.jpg" width="151" height="141">
				   </c:otherwise>
				 </c:choose>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="hidden" name="DELETED" id="DELETE" value="0" />
					<input type="hidden" name="SCHOOL_NAME" id="SCHOOL_NAME" value=""  />
					<input type="hidden" name="ENGLISH_NAME" id="ENGLISH_NAME" value="" />
					<input type="hidden" name="SPECIALITY_NAME" id="SPECIALITY_NAME" value="" />
					<input type="hidden" name="STATUS_NAME" id="STATUS_NAME" value="" />
					<input type="hidden" name="PIC" id="pic" value="${pd.PIC}" />
					<input type="hidden" name="YIXIANG" id="YIXIANG" value="<c:choose ><c:when test='${pd.ID!=null && pd.ID!=""}'>${pd.YIXIANG}</c:when><c:otherwise ><%=yixiangInit%></c:otherwise></c:choose>" />
					<a id="save_a" class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
		
		$(document).ready(function(){
		  $("#picFile").change(function(){
		    ajaxFileUpload();
		  });
		});
		
		
		rat('star','result',1);
		</script>
</body>
</html>