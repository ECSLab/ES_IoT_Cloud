$(document).ready(function(){
	$.ajax({
		type:"get",
		url:"http://47.92.48.100:8099/iot/api/projectIndustry/list",
		dataType:"json",
		data:"",
		async:false,
		success:function(res){
			if(res.code==0){
				var html='';
				for(var i=0;i<res.data.length;i++){
					html+='<option value="'+res.data[i].id+'">'+res.data[i].name+'</option>';
				}
				$("#industry").append(html);
			}
			
		}
	});

	$("#product-name").blur(function() {
		nameMustFill();
	});
	$("#product-profile").blur(function() {
		profileMustFill();
	});

	function nameMustFill(){
		var name=$("#product-name");
		if(name.val()==''){
			name.attr('class','parsley-error');
			$("#require_1").css("display","inline-block");
			return false;
		}else{
			name.attr('class','parsley-success');
			$("#require_1").css("display","none");
			return true;
		}	

	}

	function profileMustFill(){
		var profile=$("#product-profile");
		if(profile.val()==''){
			profile.attr('class','parsley-error');
			$("#require_2").css("display","inline-block");
			return false;
		}else{
			profile.attr('class','parsley-success');
			$("#require_2").css("display","none");
			return true;
		}

	}

	$("#makeSure").click(function(){
		if(nameMustFill()&&profileMustFill()){
			var name=$("#product-name").val(),
				industryId=$("#industry").val(),
				profile=$("#product-profile").val(),
				userId=1;
			$.ajax({
				type:"post",
				async:false,
				url: "http://47.92.48.100:8099/iot/api/project/create",
		        dataType:"json",
		        data:{"userId":parseInt('1'),"name":name,"industryId":parseInt(industryId),"profile":profile},
		        success: function (res) {
		        	alert(res.message);	            
		        },
		        error:function(res){
		        	alert("error");
		        }
	    	});
		}
	});
});