define(['app','jquery', 'cropper' ,'directive/stepDirective'],function(app, $){
	app.register.controller('registerController', function($scope, $rootScope, $state, $http){
		
		$scope.register = function(){

			//表单正常提交
            if($scope.registerForm.$valid){
            	alert(33)
//            	var promise = $http({
//		            method:"post",
//		            params:{
//		            	username : $scope.username,
//		            	password : $scope.password
//		            },
//		            url:"loginAction.do?method=userLogin",
//		            cache: true
//            	});
//            	//登录成功
//            	promise.success(function(data){
//            		if(data.success){
//            			PL._init();
//            			PL.joinListen('sendMessage', data.data.ID);
////            		 	onData = function(event) {
////            		 		alert(event.get("eventType"));
////            		 	};
//            		 	//设置全局变量
//            		 	$rootScope.userId = data.data.ID;
//            		 	$rootScope.name = data.data.NAME;
//            		 	$rootScope.username = $scope.username;
//            			//跳转聊天主页面
//            			$state.go('chat');
//            		}else{
//                		alert('登录失败，用户或密码错误');
//            		}
//            	});
            } else {
                $scope.submitted = true;
            }
		}
	});
    
})