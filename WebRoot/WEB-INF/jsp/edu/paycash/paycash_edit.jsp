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
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<script type="text/javascript">
	
	
	//保存
	function save(){
		var num=$("#NUM").val();
		
		if($("#NUM").val()==""){
			$("#NUM").tips({
				side:3,
	            msg:'请输入单据号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NUM").focus();
			return false;
		}
		if($("#PAY_TYPE").val()==""){
			$("#PAY_TYPE").tips({
				side:3,
	            msg:'请输入收费类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PAY_TYPE").focus();
			return false;
		}
		if($("#MONEY").val()==""){
			$("#MONEY").tips({
				side:3,
	            msg:'请输入金额',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MONEY").focus();
			return false;
		}
		$("#PAY_NAME").val($("#PAY_TYPE  option:selected").text());
		
		if($("#ID").val()==""){//id为空，说明是添加，需要验证单号是否重复
			$.ajax({   
	           url:'paycash/numIsRepeated',   
	           type:'post',   
	           cache: false,
	           dataType:'json',
			   data: {'NUM':num},
	           success:function(data){   
			  	  //alert(data.message);
	              if(data.count>0){
	            	  bootbox.dialog("单号重复，请重新填写!", 
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
	              }else{
		              $("#Form").submit();
					  $("#zhongxin").hide();
					  $("#zhongxin2").show();
	            	  
	              }
	           },
	           error:function (XMLHttpRequest, textStatus, errorThrown) {
	        	   alert(XMLHttpRequest);
	        	   alert(textStatus);
	        	   alert(errorThrown);
	           }
	       });
		}else{
			 $("#Form").submit();
			 $("#zhongxin").hide();
			 $("#zhongxin2").show();
		}
		
		
	}
	
</script>
	</head>
<body>
	<form action="paycash/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td>
					<input type="text" name="STD_NAME" value="${pd.STD_NAME}" placeholder="学生姓名" title="学生姓名" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="NUM" id="NUM" value="${pd.NUM}" maxlength="32" placeholder="单据号" title="单据号"/>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
					<select name="PAY_TYPE" id="PAY_TYPE" data-placeholder="选择收费类型">
						  <option value='' ></option>
						  <c:forEach var="data" items="${dic_pay_type_list}" >
						  <option value='${data.ID}' <c:if test='${data.ID==pd.PAY_TYPE}'>selected</c:if>>${data.NAME}</option>
						  </c:forEach>
					</select>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td>
					<input type="number" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="32" placeholder="这里输入金额" title="金额"/>
					&nbsp;<span style="color:#ff0000">*</span>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="hidden" name="STD_ID" value="${pd.STD_ID}">
					<input type="hidden" name="STD_NUM" value="${pd.STD_NUM}">
					<input type="hidden" name="PAY_NAME" id="PAY_NAME" value="${pd.PAY_NAME}">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
		
		</script>
</body>
</html>