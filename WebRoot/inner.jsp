<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
子页面<br/>
<select>
	<option>模板1</option>
	<option>模板2</option>
	<option>模板3</option>
</select><br/>
<input type="checkbox" value="1" onclick="cb(this)"> 1.题<br/>
<input type="checkbox" value="2" onclick="cb(this)"> 2.题<br/>
<input type="checkbox" value="3" onclick="cb(this)"> 3.题<br/>
<input type="checkbox" value="4" onclick="cb(this)"> 4.题<br/>

<script type="text/javascript">

function cb(e){
	var homewordIds=parent.window.document.getElementById("homewordIds");
	var oldIds=homewordIds.value;
	var oldIdsArray=oldIds.split(",");
	
	if(e.checked){
		homewordIds.value=homewordIds.value+","+e.value;
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
</body>
</html>