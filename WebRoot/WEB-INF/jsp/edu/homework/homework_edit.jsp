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
		<base href="<%=basePath%>">
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
		
		<script type="text/javascript" src="static/js/bootbox.min.js"></script>
		<!-- 确认窗口 -->
		
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.raty.js"></script>
		
<script type="text/javascript">
	
	//保存
	function save(){
			if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
	            msg:'请输入标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TITLE").focus();
			return false;
		}
	 		if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请选择分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
				return false;
			} 
/*  		if($("#TYPE_NAME").val()==""){
			$("#TYPE_NAME").tips({
				side:3,
	            msg:'请输入分类名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE_NAME").focus();
			return false;
		}  */
		/* if($("#FILE_PATH").val()==""){
			$("#FILE_PATH").tips({
				side:3,
	            msg:'请输入上传文件路径',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FILE_PATH").focus();
			return false;
		} */
		/* if($("#ANSWER").val()==""){
			$("#ANSWER").tips({
				side:3,
	            msg:'请输入答案',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FILE_PATH").focus();
			return false;
		} */
		/* if($("#ANSWER_PATH").val()==""){
			$("#ANSWER_PATH").tips({
				side:3,
	            msg:'请输入答案文件路径',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FILE_PATH").focus();
			return false;
		} */
/* 		if($("#CREATE_TIME").val()==""){
			$("#CREATE_TIME").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TIME").focus();
			return false;
		} */
/* 		if($("#CREATOR").val()==""){
			$("#CREATOR").tips({
				side:3,
	            msg:'请输入创建人ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATOR").focus();
			return false;
		} */
/* 		if($("#CREATOR_NAME").val()==""){
			$("#CREATOR_NAME").tips({
				side:3,
	            msg:'请输入创建人姓名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATOR_NAME").focus();
			return false;
		} */
		$("#TYPE_NAME").val($("#TYPE option:selected").text());
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	function deleteFile(){
		bootbox.confirm("确定要删除吗?", function(result) {
			if(result) 
				{
					document.getElementById("div_file_operator").style.display="";
					document.getElementById("div_file").style.display="none";
					document.getElementById("FILE_NAME").value="";
					document.getElementById("FILE_PATH").value="";
					$.ajax(
							{
								type: "POST",
								url: '<%=basePath%>homework/deleteFileEdit.do?tm='+new Date().getTime(),
								data: {ID:'${pd.ID}',OPERATOR:"FILE"},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									$.each(data.list, function(i, list)
										{
											nextPage(${page.currentPage});
										}
									);
								}
						}
					);
				}
			}
		);
	}
	
	function deleteAnswer(){
		bootbox.confirm("确定要删除吗?", function(result) {
			if(result) 
				{
					document.getElementById("div_answer_operator").style.display="";
					document.getElementById("div_answer").style.display="none";
					document.getElementById("ANSWER_NAME").value="";
					document.getElementById("ANSWER_PATH").value="";
					$.ajax(
							{
								type: "POST",
								url: '<%=basePath%>homework/deleteFileEdit.do?tm='+new Date().getTime(),
								data: {ID:'${pd.ID}',OPERATOR:"ANSWER"},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									$.each(data.list, function(i, list)
										{
											nextPage(${page.currentPage});
										}
									);
								}
						}
					);
				}
			}
		);					
	}
</script>
<style type="text/css">
.textarea{
	width: 500px;
}
</style>
	</head>
<body>
	<form action="homework/${msg }.do" name="Form" id="Form" method="post" enctype ="multipart/form-data" >
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td>
					<textarea rows="3" cols="13" name="TITLE" id="TITLE" placeholder="这里输入标题" class="textarea">${pd.TITLE}</textarea> 
					<span style="color: #ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
					<textarea rows="3" cols="13" name="ANSWER" id="ANSWER" placeholder="这里输入答案" class="textarea">${pd.ANSWER}</textarea> 
				</td>
			</tr>
			<tr>
				<td>
					<%--<input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入政治面貌ID" title="政治面貌ID"/>--%> 
					<select class="chzn-select" name="TYPE" id="TYPE" data-placeholder="这里选择分类">
						<option value=""></option>
						<c:forEach var="data" items="${dic_homework_type_list}">
							<option value='${data.ID}' 
								<c:if test='${data.ID==pd.TYPE}'>selected</c:if>>${data.NAME}
							</option>
						</c:forEach>
					</select> &nbsp;
					<span style="color: #ff0000">*</span>
				</td>
			</tr>
			<%-- <tr>
				<td>
					<input type="text" name="FILE_NAME" id="FILE_NAME" value="${pd.FILE_NAME}" maxlength="32" placeholder="这里输入上传文件名称" title="上传文件名称"/>
				</td>
			</tr> --%>		
			<tr>
				<td>
					<div id="div_file_operator" <c:if test='${pd.FILE_PATH!=null && pd.FILE_PATH!=""}'>style="display: none"</c:if>>
						这里上传题目附件：	
						<input type="file" id="file" name="file" title="题目附件" placeholder="这里上传题目附件" />	
					</div>
					<c:if test='${pd.FILE_PATH!=null && pd.FILE_PATH!=""}'>
						<div id="div_file">题目附件：
							<a href="<%=basePath %>homework/download?ID=${pd.ID}&OPERATOR=FILE">${pd.FILE_NAME}</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" onclick="deleteFile()">删除</a>
						</div>
					</c:if>							
				</td>
			</tr>
			<%-- <tr>
				<td>
					<input type="text" name="ANSWER_NAME" id="ANSWER_NAME" value="${pd.ANSWER_NAME}" maxlength="32" placeholder="这里输入答案文件名称" title="答案文件名称"/>
				</td>
			</tr> --%>
			<tr>
				<td>
					<div id="div_answer_operator" <c:if test='${pd.ANSWER_PATH!=null && pd.ANSWER_PATH!=""}'>style="display: none"</c:if>>
						这里上传答案附件：	
						<input type="file" id="answer" name="answer" title="答案附件" placeholder="这里上传答案附件" />	
					</div>
					<c:if test='${pd.ANSWER_PATH!=null && pd.ANSWER_PATH!=""}'>
						<div id="div_answer">答案附件：
							<a href="<%=basePath %>homework/download?ID=${pd.ID}&OPERATOR=ANSWER">${pd.ANSWER_NAME}</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" onclick="deleteAnswer()">删除</a>
						</div>
					</c:if>							
				</td>
			</tr>
<%-- 			<tr>
				<td>
					<input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入创建时间" title="创建时间"/>
				</td>
			</tr> --%>
<%-- 			<tr>
				<td>
					<input type="text" name="CREATOR" id="CREATOR" value="${pd.CREATOR}" maxlength="32" placeholder="这里输入创建人ID" title="创建人ID"/>
				</td>
			</tr> --%>
<%-- 			<tr>
				<td>
					<input type="text" name="CREATOR_NAME" id="CREATOR_NAME" value="${pd.CREATOR_NAME}" maxlength="32" placeholder="这里输入创建人姓名" title="创建人姓名"/>
				</td>
			</tr> --%>
			<tr>
				<td style="text-align: center;">
					<br><br><br>
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
			<tr>
				<td>			
					<input type="hidden" name="DELETED" id="DELETED" value="0" />
					<input type="hidden" name="TYPE_NAME" id="TYPE_NAME" value="" />					
					<input type="hidden" name="FILE_NAME" id="FILE_NAME" value="${pd.FILE_NAME }"/>
					<input type="hidden" name="FILE_PATH" id="FILE_PATH" value="${pd.FILE_PATH }"/>
					<input type="hidden" name="ANSWER_NAME" id="ANSWER_NAME" value="${pd.ANSWER_NAME }"/>
					<input type="hidden" name="ANSWER_PATH" id="ANSWER_PATH" value="${pd.ANSWER_PATH }"/>
					 
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