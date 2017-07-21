define(['app','jquery'],function(app, $){
	app.register.controller('recentListController', function($scope, $rootScope, $state, $http){

		//请求最近联系人列表
		$http({
            method:"get",
            params:{
            	userId : $rootScope.userId
            },
            url:"chatAction.do?method=getRecentUserList",
            cache: true
    	}).success(function(data){
    		$scope.userRecentList = data;
    	});
	});
})