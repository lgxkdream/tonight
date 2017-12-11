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
	    <script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
	</head>
	<script> 
		function save(){
			var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
	    $("#DATA_TID").val(str);
	    $("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	</script>
<body>

	<form action="class/setTea.do" name="Form" id="Form" method="post">
	   <input type="hidden" name="DATA_TID" id="DATA_TID" />
	   <input type="hidden" name="DATA_CLASSID" id="DATA_CLASSID" value="${param_class}"/>
	    <div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						  <label><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>姓名</th>
						<th>性别</th>
						<th>教课名称</th>
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
										<label>
											<input type="checkbox" name="ids" value="${var.ID}" 
												<c:forEach items="${teaList}" var="var1" varStatus="vs1">
													<c:if test="${var1.teacher_id==var.ID}">
														checked="checked"
													</c:if>
												</c:forEach>
										/><span class="lbl"></span></label>
									</td>
									
									
								        <td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.NAME}</td>
										<td>${var.GENDER}</td>  
										<td>${var.SUBJECT_NAME}</td>   
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
				<%--<table>
					<tr>
						--%><a id="save_a" class="btn btn-mini btn-primary" onclick="save('您确定要执行操作吗？');">保存</a>
						<a class="btn btn-mini btn-danger"  onclick="top.Dialog.close();">取消</a>
					<%--<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
	--%><div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
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
		$(top.hangge());
			$(function() {
				
				//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
		});
		</script>
</body>
</html>