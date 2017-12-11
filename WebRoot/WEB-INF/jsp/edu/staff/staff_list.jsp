<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">

				<div class="row-fluid">

					<!-- 检索  -->
					<form action="staff/list.do" method="post" name="Form" id="Form">
						<table>
							<tr>
								<td><span class="input-icon"> 
							<!-- 	autocomplete="off" -->
								<input id="NAME" type="text" name="NAME" value="${pd.NAME}" placeholder="这里姓名关键字" /> 
								<i id="nav-search-icon" class="icon-search"></i> </span></td>
							
								<td><input class="span10 date-picker" name="lastLoginStart"
									id="lastLoginStart" value="${pd.CREATE_TIME}" type="text"
									data-date-format="yyyy-mm-dd" readonly="readonly"
									style="width:150px;" placeholder="创将时间-开始日期" />
								</td>
								
								<td><input class="span10 date-picker" name="lastLoginEnd"
									id="lastLoginEnd" value="${pd.CREATE_TIME}" type="text"
									data-date-format="yyyy-mm-dd" readonly="readonly"
									style="width:150px;" placeholder="创建时间-结束日期" />
								</td>
								
								<td style="vertical-align:top;">
								<select class="chzn-select" name="JOB" id="JOB" data-placeholder="这里选择职务" style="vertical-align:top;width: 150px;">
								<option value="">全部</option>
								<c:forEach var="data" items="${dic_job_list}" >
									<option value='${data.ID}' <c:if test='${data.ID==pd.JOB}'>selected</c:if>>${data.NAME}</option>
								</c:forEach>
								</select>
								</td>
								
								
								
								
								
								<td style="vertical-align:top;">
								<input id="STATUS" type="hidden" name="STATUS" value="10" /> 
								<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>
						
								
								<c:if test="${QX.cha == 1 }">
									<td style="vertical-align:top;"><a
										class="btn btn-mini btn-light" onclick="toExcel();"
										title="导出到EXCEL"><i id="nav-search-icon"
											class="icon-download-alt"></i>
									</a>
									</td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->


						<table id="table_report"
							class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center"><label><input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span>
									</label></th>
									<th>序号</th>
									<th>姓名</th>
									<th>性别</th>
									<!-- <th>职务ID</th> -->
									<th>职务名</th>


									<th>身份证号</th>
									<th>电话号码</th>
<%--									<th>生日</th>--%>
<%--									<th>政治面貌</th>--%>
<%--									<th>民族</th>--%>
<%--									<th>家庭住址</th>--%>
									<th>课程名</th>
<%--									<th>入职时间</th>--%>
									
<%--									<th>员工状态</th>--%>
<%--									<th>图片</th>--%>
<%--									<th>荣誉</th>--%>
<%--									<th>工龄</th>--%>
<%--									<th>月薪</th>--%>
<%--									<th>学历</th>--%>




<%--									<th>创建时间</th>--%>
									<!-- <th>创建人ID</th> -->
<%--									<th>创建人姓名</th>--%>
									<!-- <th>是否删除</th> -->
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<c:if test="${QX.cha == 1 }">
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;"><label><input
															type='checkbox' name='ids' value="${var.ID}" /><span
															class="lbl"></span>
													</label></td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td>${var.NAME}</td>
													<td><c:if test='${var.GENDER==1}'>男</c:if>
														<c:if test='${var.GENDER==2}'>女</c:if>
													</td>
													<!-- <td>${var.JOB}</td> -->
													<td>${var.JOB_NAME}</td>
													
													
													
													<td>${var.SFZH}</td>
													<td>${var.PHONE}</td>
<%--													<td>${var.BIRTHDAY}</td>--%>
<%--													<td>${var.ZZMM_NAME}</td>--%>
<%--													<td>${var.MINZU_NAME}</td>--%>
<%--													<td>${var.ADDRESS}</td>--%>
													<td>${var.SUBJECT_NAME }</td>
<%--													<td>${var.ENTRY_TIME}</td>--%>
<%--													<td>${var.STATUS_NAME}</td>--%>
<%--													<td>图片</td>--%>
<%--													<td>${var.HONOR}</td>--%>
<%--													<td>${var.WORK_AGE}</td>--%>
<%--													<td>${var.WAGE}</td>--%>
<%--													<td>${var.EDU_NAME}</td>--%>
													
													
													
													
													
													
													
<%--													<td>${var.CREATE_TIME}</td>--%>
													<!-- <td>${var.CREATOR}</td> -->
<%--													<td>${var.CREATOR_NAME}</td>--%>
													<!-- <td>${var.DELETED}</td> -->
													<td style="width: 30px;" class="center">
														<div class='hidden-phone visible-desktop btn-group'>

															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="icon-lock" title="无权限"></i>
																</span>
															</c:if>
															<div class="inline position-relative">
																<button class="btn btn-mini btn-info"
																	data-toggle="dropdown">
																	<i class="icon-cog icon-only"></i>
																</button>
																<ul
																	class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
																	<c:if test="${QX.edit == 1 }">
																		<li><a style="cursor:pointer;" title="编辑"
																			onclick="edit('${var.ID}');" class="tooltip-success"
																			data-rel="tooltip" title="" data-placement="left"><span
																				class="green"><i class="icon-edit"></i>
																			</span>
																		</a>
																		</li>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																		<li><a style="cursor:pointer;" title="删除"
																			onclick="del('${var.ID}');" class="tooltip-error"
																			data-rel="tooltip" title="" data-placement="left"><span
																				class="red"><i class="icon-trash"></i>
																			</span> </a>
																		</li>
																	</c:if>
																</ul>
															</div>
														</div></td>
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
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>


							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;"><c:if
											test="${QX.add == 1 }">
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
										</c:if> <c:if test="${QX.del == 1 }">
											<a class="btn btn-small btn-danger"
												onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
												class='icon-trash'></i>
											</a>
										</c:if></td>
									<td style="vertical-align:top;"><div class="pagination"
											style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>




				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i> </a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
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
			 diag.URL = '<%=basePath%>staff/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
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
					var url = "<%=basePath%>staff/delete.do?ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>staff/goEdit.do?ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
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
								url: '<%=basePath%>staff/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
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
			var action=Form.action;
			Form.action="<%=basePath%>staff/excel.do";
			Form.submit();
			Form.action=action;
			<%-- window.location.href='<%=basePath%>staff/excel.do'; --%>
		}
		</script>

</body>
</html>

