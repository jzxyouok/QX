//自定义js

//公共配置

$(document).ready(function () {
	
    var height = $(window).height() - $("#nav-head-qx").height();
    $('#nav-list-qx').slimScroll({
        height: '100%',
        railOpacity: 0.9,
        alwaysVisible: false
    });
//  height = $(window).height() - $(".content-head-qx").height() - $(".content-chart-input-qx").height();
    $('#content-chart-scroll').slimScroll({
        railOpacity: 0.9,
        alwaysVisible: false,
        height : '100%'
    });
    
})
