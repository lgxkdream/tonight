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
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="student/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="NAME" type="text" name="NAME" value="${pd.NAME}" placeholder="这里输入姓名" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="CREATE_TIME_START" id="lastLoginStart" value="${pd.CREATE_TIME_START}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="CREATE_TIME_END" id="lastLoginEnd" value="${pd.CREATE_TIME_END}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
					<td style="vertical-align:top;">
					 	<select name="SCHOOL" id="SCHOOL" data-placeholder="请选择学校" style="vertical-align:top;width: 120px;">
							<option value=""> 全部学校</option>
							<c:forEach var="data" items="${dic_school_list}" >
						    <option value='${data.ID}' <c:if test='${data.ID==pd.SCHOOL}'>selected</c:if>>${data.NAME}</option>
						    </c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;">
					 	<select name="SPECIALITY" id="SCHOOL" data-placeholder="请选择专业" style="vertical-align:top;width: 120px;">
							<option value="">全部专业</option>
							<c:forEach var="data" items="${dic_speciality_list}" >
						    <option value='${data.ID}' <c:if test='${data.ID==pd.SPECIALITY}'>selected</c:if>>${data.NAME}</option>
						    </c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;">
					<input type="hidden" name="STATUS" value="${pd.STATUS}">
					<input type="hidden" name="JR_CLASS" value="${pd.JR_CLASS}">
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
						<th>学号</th>
						<th>姓名</th>
						<th>性别</th>
						<th>身份证号</th>
						<th>学校</th>
						<th>入学时间</th>
						<th>寝室号</th>
						<th>电话</th>
						<th>意向</th>
						<th>专业</th>
						<th>吉软班级</th>
						<th class="center" width="100px">操作</th>
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
										<td>${var.STD_NUM}</td>
										<td>${var.NAME}</td>
										<td><c:if test='${var.GENDER==1}'>男</c:if><c:if test='${var.GENDER==2}'>女</c:if></td>
										<td>${var.SFZH}</td>
										<td>${var.SCHOOL_NAME}</td>
										<td>${var.SCHOOL_YEAR}</td>
										<td>${var.ROOM_NUM}</td>
										<td>${var.PHONE}</td>
										<td>${var.YIXIANG}</td>
										<td>${var.SPECIALITY_NAME}</td>
										<td>${var.JR_CLASS_NAME}</td>
								<td style="width: 30px;" class="center">
									<div class="hidden-phone visible-desktop btn-group">
										<button title="编辑" class="btn btn-mini btn-info" data-toggle="dropdown" onclick="edit('${var.ID}');">编辑</button>
										<button title="缴费管理" class="btn btn-mini btn-success" data-toggle="dropdown" onclick="top.siMenu('${var.ID}','${var.ID}','${var.NAME}-缴费管理','paycash/listByStdId.do?STD_ID=${var.ID}')">缴费管理</button>
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
<%--					<c:if test="${QX.add == 1 }">--%>
<%--					<a class="btn btn-small btn-success" onclick="add();">新增</a>--%>
<%--					</c:if>--%>
					<a class="btn btn-small btn-success" onclick="setClass();">分配班级</a>
<%--					<c:if test="${QX.del == 1 }">--%>
<%--					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>--%>
<%--					</c:if>--%>
						<a class="btn btn-small btn-danger" onclick="oprationAll('确定要将选中的学生从班级删除吗?');" title="批量删除" >从班级删除</a>
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>student/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 655;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>student/delete.do?ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>student/goEdit.do?ID='+Id;
			 diag.Width = 450;
			 diag.Height = 655;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
		<script type="text/javascript">
		
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
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>student/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
						    		if(data.result==1){
						    			 alert('删除成功！');
										 nextPage(${page.currentPage});
									}else{
										 alert('删除失败！');
										 top.hangge();
									}
								}
							});
						}
					}
				}
			});
		}
		
		//导出excel
		function toExcel(){
			var form=document.getElementById("Form");
			form.action="student/excel.do";
			form.submit();
			form.action="student/list.do";
		}
		function setClass(){
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
						top.jzts();
						 var diag = new top.Dialog();
						 diag.Drag=true;
						 diag.Title ="分配班级";
						 diag.URL = '<%=basePath%>student/showAllClass?studentIds='+str;
						 diag.Width = 450;
						 diag.Height = 655;
						 diag.CancelEvent = function(){ //关闭事件
							 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
								 if('${page.currentPage}' == '0'){
									 top.jzts();
									 setTimeout("self.location.reload()",100);
								 }else{
									 nextPage(${page.currentPage});
								 }
							}
							diag.close();
						 };
						 diag.show();
<%--						if(msg == '确定要删除选中的数据吗?'){--%>
<%--							top.jzts();--%>
<%--							$.ajax({--%>
<%--								type: "POST",--%>
<%--								url: '<%=basePath%>student/deleteAll.do?tm='+new Date().getTime(),--%>
<%--						    	data: {DATA_IDS:str},--%>
<%--								dataType:'json',--%>
<%--								//beforeSend: validateData,--%>
<%--								cache: false,--%>
<%--								success: function(data){--%>
<%--									 $.each(data.list, function(i, list){--%>
<%--											nextPage(${page.currentPage});--%>
<%--									 });--%>
<%--								}--%>
<%--							});--%>
<%--						}--%>
					}
		}
		
		//批量操作
		function oprationAll(msg){
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
						$.ajax({
							type: "POST",
							url: '<%=basePath%>class/updateAll.do?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
					    		if(data.result==1){
					    			 alert('删除成功！');
									 nextPage(${page.currentPage});
								}else{
									 alert('删除失败！');
									 top.hangge();
								}
							}
						});
					}
				}
			});
		}
		</script>
		
	</body>
</html>

