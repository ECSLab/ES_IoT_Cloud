$(document).ready(function() {
	$("#main-header,.main-sidebar").hover(function(){
		$("#main-header").addClass('on');
		$(".main-sidebar").css("transform","translateX(0)");
	},function(){
		$("#main-header").removeClass('on');
		$(".main-sidebar").css("transform","translateX(-100%)");
	});
});
	
