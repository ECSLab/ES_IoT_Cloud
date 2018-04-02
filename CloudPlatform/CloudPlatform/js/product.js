$(document).ready(function() {
	$("#main-header,.main-sidebar").hover(function(){
		$("#main-header").addClass('on');
		$(".main-sidebar").css("transform","translateX(0)");
	},function(){
		$("#main-header").removeClass('on');
		$(".main-sidebar").css("transform","translateX(-100%)");
	});
	
	// var analysis = echarts.init($("#dTAnalysis"))
	// 基于准备好的dom，初始化echarts实例
	var Chart1 = echarts.init(document.getElementById('dTAnalysis'));
	var Chart2 = echarts.init(document.getElementById('sparAnalysis'));

	// 指定图表的配置项和数据
	var option1 = {
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data:['设备数量']
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['周一','周二','周三','周四','周五','周六','周日']
		},
		yAxis: {
			type: 'value'
		},
		series: [
			{
				itemStyle : {  
					normal : {  
						color:'#30e5a5',  
						lineStyle:{  
							color:'#30e5a5'  
						}  
					}
				},
				name:'设备数量',
				type:'line',
				stack: '总量',
				data:[820, 932, 901, 934, 1290, 1330, 1320]
			}
		]
	};
	var option2 = {
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data:['触发信息数']
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['周一','周二','周三','周四','周五','周六','周日']
		},
		yAxis: {
			type: 'value'
		},
		series: [
			{
				itemStyle : {  
					normal : {  
						color:'rgb(25, 187, 255)',  
						lineStyle:{  
							color:'rgb(25, 187, 255)'  
						}  
					}
				},
				name:'触发信息数',
				type:'line',
				stack: '总量',
				data:[820, 932, 901, 934, 1290, 1330, 1320]
			}
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	Chart1.setOption(option1);
	Chart2.setOption(option2);
});