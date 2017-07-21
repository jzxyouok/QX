define(['app','service/loginService','service/commonService'],function(app){
	app.register.controller('loginController', function($scope, $rootScope, $state, $location, $http){
//		$scope.str = 'home page';
//      $scope.sex = 0;
//      $scope.books = booksService.books;
//      $scope.toTest = function(){
//      	alert(3345)
//      	$location.path('/list');
//      	alert(4)
//      }
//		PL._init();
//		PL.joinListen('laoduliu');
//	 	onData = function(event) {
//	 		alert(event.get("hw"));
//	 	};
		$scope.login = function(){
			//表单正常提交
            if($scope.loginForm.$valid){
            	var promise = $http({
		            method:"post",
		            params:{
		            	username : $scope.username,
		            	password : $scope.password
		            },
		            url:"loginAction.do?method=userLogin",
		            cache: true
            	});
            	//登录成功
            	promise.success(function(data){
            		if(data.success){
            			PL._init();
            			PL.joinListen('sendMessage', data.data.ID);
//            		 	onData = function(event) {
//            		 		alert(event.get("eventType"));
//            		 	};
            		 	//设置全局变量
            		 	$rootScope.userId = data.data.ID;
            		 	$rootScope.name = data.data.NAME;
            		 	$rootScope.sex = data.data.SEX;
            		 	$rootScope.username = $scope.username;
            			//跳转聊天主页面
            			$state.go('chat');
            		}else{
                		alert('登录失败，用户或密码错误');
            		}
            	});
            } else{
                $scope.submitted = true;
            }
		}
	});
    
})