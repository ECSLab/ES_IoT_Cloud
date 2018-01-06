$(document).ready(function(){
    $(".group-single input").iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
	   
	$("#name").focus();
	var phone=/^1[34578]\d{9}$/;
	var email=/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	$("#name").blur(function() {
		var $name=$(this).val();
		if($name!=''){
			// if((phone.test($name))||(email.test($name))){
				$(this).attr('class','parsley-success');
				$("#require_1").css("display","none");
			// }
			// else{
			// 	$(this).attr('class','parsley-error');
			// 	$("#require_1 li span").html("格式不正确");
			// 	$("#require_1").css("display","inline-block");
			// }
			
		}
		else if ($name==''){
			$(this).attr('class','parsley-error');
			$("#require_1 li span").html("必须填");
			$("#require_1").css("display","inline-block");
			
		}
	});
	$("#pass").blur(function() {
		var $pass=$(this).val();
		if($pass!=''){
			$(this).attr('class','parsley-success');
			$("#require_2").css("display","none");
		}
		else if ($pass==''){
			$(this).attr('class','parsley-error');
			$("#require_2").css("display","inline-block");
			
		}
	});
	$("#submit").click(function(){
		var username=$("#name").val();
		var password=$("#pass").val();
		$.ajax({
			type:"post",
			async:false,
			url: "https://wusteslab.tech/iot/api/user/doLogin",
	        dataType:"json",
	        data:{username:username,password:password},
	        success: function (res) {
	        	alert(res.message);
	            // if(res.code==0)
	            //     alert("ok");
	        },
	        erro:function(res){
	        	alert(res.message);
	        	// alert(res.message);
	        	// if (res.code!=0){
	        	// 	alert("false");
	        	// }
	        }
	    });
	})
});