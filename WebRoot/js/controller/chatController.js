define(['app','jquery','slimscroll','bootstrap'],function(app, $){
	app.register.controller('chatController', function($scope, $rootScope, $state, $stateParams, $http, $filter){
		//请求常用联系人列表
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
		//常用联系人列表点击
		$scope.recentSelected = true;
		$scope.newsSelected = false;
		$scope.directorySelected = false;
		//左侧toolbar切换
		$scope.toolbarSelect = function(index){
			if(index==0){
				$scope.recentSelected = true;
				$scope.newsSelected = false;
				$scope.directorySelected = false;
			}else if(index==1){
				$scope.recentSelected = false;
				$scope.newsSelected = true;
				$scope.directorySelected = false;
			}else{
				$scope.recentSelected = false;
				$scope.newsSelected = false;
				$scope.directorySelected = true;
			}
		}
		//列表人员点击切换
		$scope.clickUser = null;
		//左侧人员列表点击
		$scope.changeChatObject = function(user){
			//右侧显示聊天对话框
			$scope.showChat = true;
			user.userSelecedClass = {
				'background-color': '#293846'
		    };
			if($scope.clickUser!=null && $scope.clickUser != user){
				$scope.clickUser.userSelecedClass = {
					'background-color': 'transparent'
			    };
			}
			$scope.clickUser = user;
		}
		
		//发送消息
		$scope.sendMessage = function(){
			if($scope.clickUser == null){
				return false;
			}
			$scope.clickUser.messageList.push({
				NAME : $rootScope.name,//消息发送人姓名
				SENDTIME : $filter('date')(new Date(), "hh:mm:ss"),
				MESSAGE : $scope.userMessage,
				OWN : true //自己
			});
			var promise = $http({
	            method:"post",
	            params:{
	            	userId : $rootScope.userId,
	            	name : $rootScope.name,
	            	receiveId : $scope.clickUser.ID,
	            	receiveType : 0,
	            	message : $scope.userMessage
	            },
	            url:"chatAction.do?method=sendMessage",
	            cache: true
        	});
			promise.success(function(data){
				
			});
			//清空发送消息
			$scope.userMessage = "";
		};
		//外部暴露，全局消息接收
		onData = function(event) {
			var userArray = $filter('filter')($scope.userRecentList, {'USERNAME': event.get("username")});
			if(userArray!=null && userArray.length > 0){
				userArray[0].messageList.push({
					NAME : decodeURIComponent(event.get("name")),//消息发送人姓名
					SENDTIME : event.get("sendTime"),//发送时间
					MESSAGE : decodeURIComponent(event.get("message")),//发送消息
					OWN : false//对方
				});
			}else{
				$scope.userRecentList.push({
					ID : event.get("userId"), //发送人ID
					USERNAME : event.get("username"),//发送人用户名
					NAME : decodeURIComponent(event.get("name")),//发送人姓名
					SEX : event.get("sex"),//发送人性别
					BZ : event.get("bz"),//发送人备注
					REJECT_FLAG : event.get("sendFlag"),//相对于接收人发送人的消息是否屏蔽
					messageList : [{
						NAME : decodeURIComponent(event.get("name")),//消息发送人姓名
						SENDTIME : event.get("sendTime"),//发送时间
						MESSAGE : decodeURIComponent(event.get("message")),//发送消息
						OWN : false//对方
					}]
				});
			}
			$scope.$apply();//需要手动刷新
	 	};
		$state.go("chat.children");
	});
})