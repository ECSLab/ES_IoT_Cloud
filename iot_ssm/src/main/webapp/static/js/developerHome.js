$(document).ready(function(){
	var name,industryName,createTime,deviceCount;
	var html='';
	$.ajax({
		type:"GET",
		async:false,
		url:"http://47.92.48.100:8099/iot/api/project/1/list",
		dataType:"json",
		data:{userId:1},
		success:function(res){
			var data=res.data;
			for(var i=0;i<data.length;i++){
				html='';
				name=data[i].name;
				industryName=data[i].industryName;
				createTime=data[i].createTime;
				deviceCount=data[i].deviceCount;
				html+='<div class="product-list">';
				html+='<div class="product-content">';
					html+='<div class="product-name">';
						html+='<a href="#" class="dev-name small_icon1" title="" style="position: relative; display: inline-block;">';
							html+='<h3>'+name+'</h3>';
							html+='<div class="tooltip tooltip_name">';
								html+='<div class="tooltip_content">'+name+'</div>';
								html+='<div class="tooltip-triangle"></div>';
							html+='</div>';							
						html+='</a>';
						html+='<span>'+industryName+'</span>';		
						html+='<a href="#" class="fr small_icon2" id="delete">';
							html+='<i class="iconfont icon-shanchu2"></i>';
							html+='<div class="tooltip tooltip_del">';
								html+='<div class="tooltip_content">删除</div>';
								html+='<div class="tooltip-triangle"></div>';
							html+='</div>';
						html+='</a>';
						html+='<a href="../html/createItem.html" class="fr small_icon3">';
							html+='<i class="iconfont icon-bianji3"></i>';
							html+='<div class="tooltip tooltip_edit">';
								html+='<div style="background-color:black;color:white;padding:5px;border-radius:5px;white-space: nowrap;font-size: 12px;">编辑';
								html+='</div>';
								html+='<div class="tooltip-triangle" style="width: 0px; height: 0px; border-width: 6px; border-style: solid; border-color: black transparent transparent; position: relative; left: 12px;">';
									
								html+='</div>';
							html+='</div>';
						html+='</a>';
					html+='</div>';
					html+='<p class="time">创建时间：'+createTime+'</p>';
					html+='<div class="number-info">';
						html+='<a href="#">';
							html+='<div class="param">';
								html+='<span class="icon-access access">';
									html+='<i class="iconfont icon-shebei"></i>';
								html+='</span>';
								html+='<p class="access-number"><span>'+deviceCount+'</span>台 </p>';
								html+='<p class="access-equ">接入设备</p>';
							html+='</div>';
						html+='</a>';
						html+='<a href="#">';
							html+='<div class="param">';
								html+='<span class="icon-access access">';
									html+='<i class="iconfont icon-yuechi"></i>';
								html+='</span>';
								html+='<p class="access-number"><span>1</span>个</p>';
								html+='<p class="access-equ">API Key</p>';
							html+='</div>';
						html+='</a>';
					html+='</div>';
				html+='</div>';
			html+='</div>';
			$("#wrap").append(html);
			}
		}
	});
	$("#delete").click(function(){
		$( "#dialog-modal" ).dialog({
			dialogClass: "no-close",
	      	height: 140,
	     	modal: true,
	     	buttons: {
		        "确定": function() {
		          $( this ).dialog( "close" );
		        },
		        取消: function() {
		          $( this ).dialog( "close" );
		        }
		    },
		    open:function(event, ui){
            	$(this).parent().focus();
        	}
	    });
	})
});