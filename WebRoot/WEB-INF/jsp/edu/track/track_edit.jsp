<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	int yixiangInit=2;
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
		<script type="text/javascript" src="<%=basePath %>js/jquery.raty.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#YIXIANG").val()==""){
			$("#YIXIANG").tips({
				side:3,
	            msg:'请输入意向',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#YIXIANG").focus();
			return false;
		}
		if($("#REMARK").val()==""){
			$("#REMARK").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#REMARK").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	function rat(star,result,m){
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
</script>
	</head>
<body>
	<form action="track/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td>
				意向：
					<div id="star"></div>
					<div id="result"></div>
				</td>
			</tr>
			<tr>
				<td>
					<textarea name="REMARK" id="REMARK" placeholder="这里输入备注" title="备注" rows="8" cols="500" >${pd.REMARK}</textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="hidden" name="YIXIANG" id="YIXIANG" value="<c:choose ><c:when test="${pd.YIXIANG!=null}">${pd.YIXIANG}</c:when><c:otherwise ><%=yixiangInit%></c:otherwise></c:choose>" />
					<input type="hidden" name="STD_ID" value="${pd.STD_ID}"/>
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
		rat('star','result',1);
		</script>
</body>
</html>