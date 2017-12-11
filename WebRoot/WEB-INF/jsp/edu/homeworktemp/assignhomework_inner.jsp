<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	</head>
<body>
	 <form action="homeworkTemplet/goAssignHomework.do" name="Form2" id="Form2" method="post">
	 	<select name="TYPE" id="TYPE" onchange="changeForm()"
	 	 data-placeholder="请选择分类" style="vertical-align:center;width:300px;margin:0px;padding:0px;">
	 	 <option  value='' <c:if test='${null== pd.TYPE && 0==pd.TYPE}'>selected</c:if>>全部</option>
	 	<c:forEach var="data" items="${dic_homeworkTemplet_type_list}" >
			  <option value='${data.ID}' <c:if test='${data.ID==pd.TYPE}'>selected</c:if>>${data.NAME}</option>
		</c:forEach>
	  	</select>
   </form>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>题目</th>
						<th>答案</th>
						<th>类型</th>
						<th>操作</th>
					</tr>
				</thead>	
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.ID}" onchange="changeIDS(this)"/><span class="lbl"></span></label>
								</td>
								<td style="width: 20px;">${vs.index+1}</td>
								<td style="width: 200px;">${fn:substring(var.TITLE,0,20)}<c:if test="${fn:length(var.TITLE)>20}">...</c:if></td>
								<td style="width: 200px;">${fn:substring(var.ANSWER,0,20)}<c:if test="${fn:length(var.ANSWER)>20}">...</c:if></td>
								<td style="width: 30px;">${var.TYPE_NAME}</td>
								<td style="width: 70px;" class="center">
									<c:if test="${var.FILE_PATH!=null && var.FILE_PATH!=''}">
										<button title="附件" class="btn btn-mini btn-info" data-toggle="dropdown" onclick="location.href='<%=basePath %>homework/download?ID=${var.ID}&OPERATOR=FILE'">附件</button>												</c:if>	
									<c:if test="${var.ANSWER_PATH!=null && var.ANSWER_PATH!=''}">
										<button title="答案" class="btn btn-mini btn-info" data-toggle="dropdown" onclick="location.href='<%=basePath %>homework/download?ID=${var.ID}&OPERATOR=ANSWER'">答案</button>
									</c:if>
									<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
									</c:if>
								</td>
							</tr>
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		var idsArr=parent.document.getElementById("HOMEWORK_IDS").value.split(",");
		for(var i=0;i < document.getElementsByName("ids").length;i++){
			for(var j=0;j < idsArr.length;j++){
				if(document.getElementsByName("ids")[i].value==idsArr[j]){
					document.getElementsByName("ids")[i].checked=true;
				}
			}
		}
		$(top.hangge());
		function changeForm(){
			 $("#Form2").submit();
		}
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			//日期框
			$('.date-picker').datepicker();
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					if(this.checked != that.checked){
						this.click();	
					}
					$(this).closest('tr').toggleClass('selected');
				});	
			});
		});
		function changeIDS(me){
			var idsStr=parent.document.getElementById("HOMEWORK_IDS").value;
			if(me.checked){
				if(idsStr==""){
					idsStr=idsStr+me.value;
				}else{
					idsStr=idsStr+","+me.value;
				}
				parent.document.getElementById('HOMEWORK_IDS').value=idsStr;
			}else{
				var array=idsStr.split(",");
				for(i=0;i<array.length;i++){
					if(array[i]==me.value){
						array.splice(i, 1);
					}
				}
				parent.document.getElementById('HOMEWORK_IDS').value=array.toString();
			}
		}
		</script>
		
	</body>
</html>

