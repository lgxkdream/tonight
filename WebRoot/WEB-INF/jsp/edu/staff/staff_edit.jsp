<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String dateStr=sdf.format(new Date());
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
		if($("input[name='GENDER']:checked").val()==undefined){
			$("#GENDER_1").tips({
				side:3,
	            msg:'请输入性别',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#GENDER_1").focus();
			return false;
		}
		if($("#JOB").val()==""){
			$("#JOB").tips({
				side:3,
	            msg:'请选择职务',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#JOB").focus();
			return false;
		}
		/*
		if($("#JOB_NAME").val()==""){
			$("#JOB_NAME").tips({
				side:3,
	            msg:'请输入职务名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#JOB_NAME").focus();
			return false;
		}
		*/
		$("#JOB_NAME").val($("#JOB  option:selected").text());
		
		$("#EDU_NAME").val($("#EDU  option:selected").text());
		$("#SUBJECT_NAME").val($("#SUBJECT  option:selected").text());
		$("#ZZMM_NAME").val($("#ZZMM  option:selected").text());
		$("#MINZU_NAME").val($("#MINZU  option:selected").text());
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
        url:'<%=basePath%>staff/upload',
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
	<%-- function rat(star,result,m){
		var score=<c:choose ><c:when test="${pd.YIXIANG!=null}">${pd.YIXIANG}</c:when><c:otherwise ><%=yixiangInit%></c:otherwise></c:choose>
		
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
	 --%>
	
	
	
	
	
	
	
	
	
	
	
	
</script>
	</head>
<body>
	<form action="staff/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
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
					<%--<input type="text" name="JOB" id="JOB" value="${pd.JOB}" maxlength="32" placeholder="这里输入职务ID" title="职务ID"/>
					--%><select class="chzn-select" name="JOB" id="JOB" data-placeholder="这里选择职务">
							<option value=""></option>
							<c:forEach var="data" items="${dic_job_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.JOB}'>selected</c:if>>${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			
			<!-- ---------------------------------------- -->
			<tr>
				<td>
					<input type="text" name="SFZH" id="SFZH" value="${pd.SFZH}" maxlength="32" placeholder="这里输入身份证号" title="身份证号"/>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="PHONE" id="SFZH" value="${pd.PHONE}" maxlength="32" placeholder="这里输入电话号码" title="电话号码"/>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td><input class="span10 date-picker" name="BIRTHDAY"
									id="BIRTHDAY" value="${pd.BIRTHDAY}" type="text"
									data-date-format="yyyy-mm-dd" readonly="readonly"
									style="width:88px;" placeholder="生日" />
				</td>
			</tr>
			<tr>
				<td>
					<select class="chzn-select" name="ZZMM" id="ZZMM" data-placeholder="这里选择政治面貌">
						<option value=""></option>
							<c:forEach var="data" items="${dic_zzmm_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.ZZMM}'>selected</c:if>>${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;
				</td>
			</tr>
			
			<tr>
				<td>
					<select class="chzn-select" name="MINZU" id="MINZU" data-placeholder="这里选择民族">
						<option value=""></option>
							<c:forEach var="data" items="${dic_minzu_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.MINZU}'>selected</c:if> >${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;
				</td>
			</tr>
			<!-- 家庭住址 -->
			<tr>
				<td>
					<input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="32" placeholder="这里输入家庭住址" title="家庭住址"/>
					&nbsp;
				</td>
			</tr>
			<!-- 课程名 -->
				<tr>
				<td>
					<select class="chzn-select" name="SUBJECT" id="SUBJECT" data-placeholder="这里选择课程名">
						<option value=""></option>
							<c:forEach var="data" items="${dic_subject_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.SUBJECT}'>selected</c:if> >${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;
				</td>
			</tr>
			<!-- 入职时间 -->
			<tr>
				<td>
				<c:choose >
<%--		ID为空，表示添加，取当前时间			--%>
				   <c:when test='${pd.ID==null}'>
							<input class="span10 date-picker" name="ENTRY_TIME"
									id="ENTRY_TIME" value="<%=dateStr %>" type="text"
									data-date-format="yyyy-mm-dd" readonly="readonly"
									style="width:88px;" placeholder="入职时间" />
				   </c:when>
				   <c:otherwise >
							<input class="span10 date-picker" name="ENTRY_TIME"
									id="ENTRY_TIME" value="${pd.ENTRY_TIME}" type="text"
									data-date-format="yyyy-mm-dd" readonly="readonly"
									style="width:88px;" placeholder="入职时间" />
					</c:otherwise>
				 </c:choose>
				</td>
			</tr>
			<!-- 员工状态 -->
			<tr>
				<td>
					<select class="chzn-select" name="STATUS" id="STATUS" data-placeholder="这里选择状态">
							<c:forEach var="data" items="${dic_stff_status_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.STATUS}'>selected</c:if>>${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;
				</td>
			</tr>
			<!-- 荣誉 -->
			<tr>
				<td>
					<textarea name="HONOR" id="HONOR" placeholder="这里输入荣誉" title="荣誉" rows="8" cols="500">${pd.HONOR}</textarea>
				</td>
			</tr>
			<!-- 工龄 -->
			<%-- <tr>
				<td>
					<input type="text" name="WORK_AGE" id="WORK_AGE" value="${pd.WORK_AGE}" maxlength="32" placeholder="这里输入工龄" title="工龄"/>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>	 --%>	
			<!-- 月薪 -->
			<tr>
				<td>
					<input type="text" name="WAGE" id="WAGE" value="${pd.WAGE}" maxlength="32" placeholder="这里输入月薪" title="月薪"/>
					&nbsp;
				</td>
			</tr>
			<!-- 学历-->	
			<tr>
				<td>
					<select class="chzn-select" name="EDU" id="EDU" data-placeholder="这里选择学历">
						<option value=""></option>
							<c:forEach var="data" items="${dic_edu_list}" >
								<option value='${data.ID}' <c:if test='${data.ID==pd.EDU}'>selected</c:if>>${data.NAME}</option>
							</c:forEach>
					</select>
					&nbsp;
				</td>
			</tr>
			<!-- 图片-->
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
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			<!-- ---------------------------------------- -->
			
			
			
			
			
			
			
			
			
			
			
			<%--<tr>
				<td>
					<div class="row-fluid input-append date">
						<input class="date-picker" id="CREATE_TIME" name="CREATE_TIME" type="text" data-date-format="yyyy-mm-dd" value="${pd.CREATE_TIME}" placeholder="创建时间" title="创建时间"/>
						<span class="add-on"><i class="icon-calendar"></i></span>
						&nbsp;<span style="color:#ff0000">*</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="CREATOR" id="CREATOR" value="${pd.CREATOR}" maxlength="32" placeholder="这里输入创建人ID" title="创建人ID"/>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="CREATOR_NAME" id="CREATOR_NAME" value="${pd.CREATOR_NAME}" maxlength="32" placeholder="这里输入创建人姓名" title="创建人姓名"/>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>--%>
			
			<!-- <tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr> -->
			
			<tr>
				<td>
					<input type="hidden" name="DELETED" id="DELETED" value="0" />
					<input type="hidden" name="JOB_NAME" id="JOB_NAME" value=""  />
					
					<input type="hidden" name="STATUS_NAME" id="STATUS_NAME" value=""  />
					<input type="hidden" name="EDU_NAME" id="EDU_NAME" value=""  />
					<input type="hidden" name="SUBJECT_NAME" id="SUBJECT_NAME" value=""  />
					<input type="hidden" name="ZZMM_NAME" id="ZZMM_NAME" value=""  />
					<input type="hidden" name="MINZU_NAME" id="MINZU_NAME" value=""  />
					<input type="hidden" name="PIC" id="pic" value="${pd.PIC}" />
		
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
		
		/* rat('star','result',1);
		 */
		</script>
</body>
</html>