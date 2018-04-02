$(document).ready(function() {
	var flag=0;
	$("#main-header").click(function(){
		if(flag==0){
			$(".main-sidebar").css("left","-134px");
			flag=1;
		}
		else{
			$(".main-sidebar").css("left","0");
			flag=0;
		}		
	});
});
	
