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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
		<script type="text/javascript" src="<%=basePath %>js/jquery.raty.js"></script>
		<script src="<%=basePath %>static/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>static/js/ace.min.js"></script>
	</head>
<body>

	<script type="text/javascript">
	function cbox(e){
		var homewordIds=parent.window.document.getElementById("homework_ids");
		var oldIds=homewordIds.value;
		var oldIdsArray=oldIds.split(",");
		if(e.checked){
			homewordIds.value=homewordIds.value+e.value+",";
		}else{
			for(i=0,length=oldIdsArray.length;i<length;i++){
				if(oldIdsArray[i]==e.value){
					oldIdsArray.splice(i,1);
				}
			}
			homewordIds.value=oldIdsArray;
		}
		
	}
	</script>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
   		
   		<form action="studentHomework/HomeworkTemplates" name="Form" id="Form" method="post">
		<select name="ID" id="ID" onchange="changeForm()" data-placeholder="筛选" style="vertical-align:td">
			<c:forEach items="${varList}" var="var" varStatus="vs">
							<option value="${var.ID}" <c:if test='${pd.ID==var.ID}' >selected</c:if>>${var.TITLE}</option>
			</c:forEach>
		</select>
			<div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>题目</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList2}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' id='ids' value="${var.ID}" onclick="cbox(this)"/><span class="lbl"></span></label>
								</td>
							    <td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.TITLE}</td>
										
									<div class='hidden-phone visible-desktop btn-group'>
										<div class="inline position-relative">
										 <button class="btn btn-mini btn-info" data-toggle="dropdown" onclick="top.Dialog.close();"><i class="icon-cog icon-only"></i></button>			
										</div>
									</div>
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
			</div>
		 <div class="page-header position-relative">
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
		  </div>
	</form>
		
	</div>
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
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
		
		$(top.hangge());
		
		//检索
		function changeForm(){
			$("#Form").submit();
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
				if(that.checked){
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						if(!this.checked){
							this.click();
						}
						//this.checked = that.checked;
						//$(this).closest('tr').toggleClass('selected');
					});
				}else{
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						if(this.checked){
							this.click();
						}
						//this.checked = that.checked;
						//$(this).closest('tr').toggleClass('selected');
					});
					
				}
				
			});
			
		});
	
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>track/excel.do';
		}
		
		var idsArr=parent.document.getElementById("homework_ids").value.split(",");
		for(var i=0;i < document.getElementsByName("ids").length;i++){
			for(var j=0;j < idsArr.length;j++){
				if(document.getElementsByName("ids")[i].value==idsArr[j]){
					document.getElementsByName("ids")[i].checked=true;
				}
			}
		}
		
		</script>
		
	</body>
</html>

