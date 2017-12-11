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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
		
	
	</head>
<body>
	<form action="class/goAssign.do" name="Form1" id="Form1" method="post">
		<input type="hidden" name="Str_id" value="${param_class}">
			<select name="Filter" id="Filter" onchange="changPage()" data-placeholder="请选择筛选" style="vertical-align:td;width: 120px;">
				<option value="UnAssigned" <c:if test='${pd.Filter=="UnAssigned"}' >selected</c:if>>未分配学生</option>
				<option value="Assigned" <c:if test='${pd.Filter=="Assigned"}'>selected</c:if>>已分配学生</option>
			</select>
	</form>
   <form action="class/setStu.do" name="Form" id="Form" method="post">
   		
	    <input type="hidden" name="DATA_IDS" id="DATA_IDS" />
	    <input type="hidden" name="DATA_CLASS" id="DATA_CLASS" value="${param_class}"/>
	
	    <div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>姓名</th>
						<th>性别</th>
						<th>学校</th>
						<th>班级</th>
						<th>入学时间</th>
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
										<td>${var.NAME}</td>
										<td>${var.GENDER}</td>
										<td>${var.SCHOOL_NAME}</td>
										<td>${var.JR_CLASS_NAME}</td>
										<td>${var.SCHOOL_YEAR}</td>
							     
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
				    <td style="vertical-align:top;"><a class="btn btn-mini btn-primary" onclick="makeAll('确定要选中的数据操作吗?');">分配学生</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
		  </div>
		
	</form>
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
  
	function changPage(){
		$("#Form1").submit();
	}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
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
						if(msg == '确定要选中的数据操作吗?'){
							$("#DATA_IDS").val(str);
						    $("#Form").submit();
							$("#zhongxin").hide();
						}
					}
				}
			});
		}
		$(top.hangge());
			$(function() {
				
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
		</script>
</body>
</html>