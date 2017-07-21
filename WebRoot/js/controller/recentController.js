define(['app','jquery','slimscroll'],function(app, $){
	app.register.controller('recentController', function($scope, $rootScope, $state, $http){
		$('#nav-list-scroll').slimScroll({
	        railOpacity: 0.9,
	        alwaysVisible: false,
	        height: '100%'
	    });
		
		//$state.go("chat.children");
	});
})