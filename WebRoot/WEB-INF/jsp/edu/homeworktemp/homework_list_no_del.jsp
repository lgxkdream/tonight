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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="homeworkTemplet/listFromTemplet.do" method="post" name="Form" id="Form">
			<input type="hidden" id="TEMPLET_ID" name="TEMPLET_ID" value="${pd.TEMPLET_ID}">
			<input type="hidden" id="HOMEWORK_IDS" name="HOMEWORK_IDS" value="${pd.HOMEWORK_IDS}">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="TITLE" type="text" name="TITLE" value="${pd.TITLE}" placeholder="这里输入题目内容" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="CREATE_TIME_START" id="lastLoginStart" value="${pd.CREATE_TIME_START}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="CREATE_TIME_END" id="lastLoginEnd" value="${pd.CREATE_TIME_END}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
					<td style="vertical-align:top;">
					 	<select name="TYPE" id="TYPE" data-placeholder="请选择分类" style="vertical-align:top;width: 120px;">
							<option value="">全部分类</option>
							<c:forEach var="data" items="${dic_homework_type_list}" >
						    <option value='${data.ID}' <c:if test='${data.ID==pd.TYPE}'>selected</c:if>>${data.NAME}</option>
						    </c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;">		
					<button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table>
			<!-- 检索  -->
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
									<label><input type='checkbox' name='ids' value="${var.ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td style="width: 200px;">${fn:substring(var.TITLE,0,20)}<c:if test="${fn:length(var.TITLE)>20}">...</c:if></td>
								<td style="width: 200px;">${fn:substring(var.ANSWER,0,20)}<c:if test="${fn:length(var.ANSWER)>20}">...</c:if></td>
								<td style="width: 30px;">${var.TYPE_NAME}</td>
								<td style="width: 50px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									<c:if test="${var.FILE_PATH!=null && var.FILE_PATH!=''}">
										<button title="附件" class="btn btn-mini btn-info" data-toggle="dropdown" onclick="location.href='<%=basePath %>homework/download?ID=${var.ID}&OPERATOR=FILE'">附件</button>												</c:if>	
									<c:if test="${var.ANSWER_PATH!=null && var.ANSWER_PATH!=''}">
										<button title="答案" class="btn btn-mini btn-info" data-toggle="dropdown" onclick="location.href='<%=basePath %>homework/download?ID=${var.ID}&OPERATOR=ANSWER'">答案</button>
									</c:if>
									<c:if test="${QX.del == 1 }">
										<button title="移除" class="btn btn-mini btn-danger" data-toggle="dropdown" onclick="del('${var.ID}');"><i class="icon-trash"></i></button>
									</c:if>
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
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.del == 1 }">
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要移除选中的题目吗?');" title="批量移除" >批量移除</a>
					</c:if>
				</td>
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
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		//删除
		function del(Id){
			bootbox.confirm("确定要移除这道题目吗?", function(result) {
				var arraystr=document.getElementById("HOMEWORK_IDS").value;
				var array=arraystr.split(",");
				for(i=0;i<array.length;i++){
					if(array[i]==Id){
						array.splice(i, 1);
					}
				}
				if(result) {
					top.jzts();
					arraystr=array.toString();
					document.getElementById('HOMEWORK_IDS').value=arraystr;
					$.ajax({
						type: "POST",
						url: "<%=basePath%>homeworkTemplet/editHomeWorkIds.do?ID="+"${pd.TEMPLET_ID}",
				    	data: {HOMEWORK_IDS:arraystr},
						dataType:'json',
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
									nextPage('${page.currentPage}');
							 });
						}
					});
				}
			});
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
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});	
			});
			
		});
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var count = 0;
					var arraystr=document.getElementById("HOMEWORK_IDS").value;
					var array=arraystr.split(",");
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
							count=count+1;
						  	for(var j=0;j<array.length;j++){
								if(array[j]==document.getElementsByName('ids')[i].value){
									array.splice(j, 1);
								}
							}
						  }
					}
					if(count==0){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									}
								}
							 ]
						);
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要移除选中的题目吗?'){
							top.jzts();
							arraystr=array.toString();
							document.getElementById("HOMEWORK_IDS").value=arraystr;
							$.ajax({
								type: "POST",
								url: "<%=basePath%>homeworkTemplet/editHomeWorkIds.do?ID="+"${pd.TEMPLET_ID}",
						    	data: {HOMEWORK_IDS:arraystr},
								dataType:'json',
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage('${page.currentPage}');
									 });
								}
							});
						}
					}
				}
			});
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>homework/excel.do';
		}
		</script>
	</body>
</html>

