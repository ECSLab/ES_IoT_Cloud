// $(document).ready(function(){
	$.ajax({
		type:"get",
		url:"http://47.92.48.100:8099/iot/api/protocolInfo/list",
		async:true,
		success: function(res0){
			for(var i=0;i<res0.data.length;i++){
				$("#protocol").append(
					'<option id="'+res0.data[i].id+'">'+res0.data[i].name+"</option>"
				);
			}
			
		},
		error: function(res){
			console.log(res);
		}
	});
	$.get("http://47.92.48.100:8099/iot/api/dataType/list",function(res1){
		for(var i=0;i<res1.data.length;i++){
			$("#dataType").append(
				'<option id="'+res1.data[i].id+'">'+res1.data[i].name+"</option>"
			);
		}
	})
	
// })

//添加设备
$('#addDevice').click(function(event) {
	//取消事件冒泡  
	event.stopPropagation();
	
	$("#createDevice").fadeIn(500).slideDown(100);
	$("#createDevice").toggleClass("showStyle");
	$("#bg").fadeIn(100);
});

//取消添加
function cancelAdd(){
	$("#createDevice").slideUp(500).fadeOut(500);
	$("#zl_delete").fadeOut(500);
	$("#createDevice").toggleClass("showStyle");
	$("#bg").fadeOut();
}

//点击取消按钮时
$("#closeCD").click(function() {
	cancelAdd();
});
//按esc键时
$(document).keyup(function(even) {
	if(even.keyCode == 27) {
		cancelAdd();
	}
})
//点击空白时
$(document).click(function(event) {
	//设置目标区域
	var _con = $("#createDevice");
	var a = _con.is(event.target);
	var b = _con.has(event.target).length;
	if(!a && b === 0){
		cancelAdd();
	}
});

//判断设备名称输入是否正确
$("#deviceName").bind("input",function(){
	var deviceName = $("#deviceName").val();
	var patt = /^[\u4e00-\u9fa5a-zA-Z0-9]{1,20}$/;
	
	if(deviceName === ""){
		$("#nameError").show();
		$("#nameError").text("必填项");
		
		$("#deviceName").removeClass("zl_right");
		$("#deviceName").addClass("zl_error");
		return false;
	}
	
	
	if(!patt.test(deviceName)){
		if(deviceName.length > 20){
			$("#nameError").show();
			$("#nameError").text("最多20个字符");
		}else{
			$("#nameError").show();
			$("#nameError").text("只能包含中文、字母和数字！");
		}
		
		$("#deviceName").removeClass("zl_right");
		$("#deviceName").addClass("zl_error");
		return false;
	}else{
		$("#nameError").hide();
		
		$("#deviceName").addClass("zl_right");
		$("#deviceName").removeClass("zl_error");
	}

	return true;
});

$("#deviceAuth").bind("input",function(){
	var deviceAuth = $("#deviceAuth").val();
	var patt = /^[a-zA-Z0-9\-]{1,}$/;
	
	if(deviceAuth === ""){
		$("#authError").show();
		$("#authError").text("必填项");
		
		$("#deviceAuth").removeClass("zl_right");
		$("#deviceAuth").addClass("zl_error");
		return false;
	}
	
	
	if(!patt.test(deviceAuth)){
		$("#authError").show();
		$("#authError").text("只能包含字母和数字！");
		
		$("#deviceAuth").removeClass("zl_right");
		$("#deviceAuth").addClass("zl_error");
		return false;
	}else{
		$("#authError").hide();
		//输入框样式
		$("#deviceAuth").removeClass("zl_error");
		$("#deviceAuth").addClass("zl_right");
	}

	return true;
});

$("#submitCD").click(function(){
	var devicename = $("#deviceName").val();
	var deviceAuth = $("#deviceAuth").val();
	var privacy = $('input[name="devicePri"]:checked').val();
	privacy = parseInt(privacy);
	
	var protocol = $("#protocol").find("option:selected").attr("id");
	var dataType = $("#dataType").find("option","selected").attr("id");

	$.ajax({
		type: "POST",
		async: true,
		dataType: "json",
		url: "http://47.92.48.100:8099/iot/api/device/create/device",
		data: {
			"userId": 26,		
			"projectId": 38,		//项目ID
			"protocolId": protocol,	//协议ID
			"dataType": dataType,		//数据类型ID
			"name": devicename,
			"number": deviceAuth,
			"privacy": privacy
		},
		success: function(res){
			console.log(res);
			if(res.code == 0){
				popUp("success","添加成功");
				cancelAdd();
				getList();
			}else{
				popUp("error","添加失败，可能是设备编号已存在");
			}
			
		},
		error: function(res){
			console.log("response with error："+res.message);
			popUp("error","添加失败！");
			cancelAdd();
		}
	})
});

// 点击公开时
$("#DM_open").click(function(){
	popUp("error","此功能还没有开放哦！");
})