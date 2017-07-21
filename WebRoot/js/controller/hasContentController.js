define(['app','jquery','slimscroll'],function(app, $){
	app.register.controller('hasContentController', function($scope, $rootScope, $state, $http){
		$('#content-chart-scroll').slimScroll({
	        railOpacity: 0.9,
	        alwaysVisible: false,
	        height : '100%'
	    });
	});
})