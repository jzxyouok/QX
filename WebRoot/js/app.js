define(['angular','router','pushlet'],function(){
	var app = angular.module("qxMainApp", ['ui.router']);
	app.config(function($controllerProvider,$compileProvider,$filterProvider,$provide){
		app.register = {
			//得到$controllerProvider的引用
			controller : $controllerProvider.register,
			//同样的，这里也可以保存directive／filter／service的引用
			directive: $compileProvider.directive,
			filter: $filterProvider.register,
			service: $provide.service
		};
	})
	.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouterProvider,$locationProvider){
		
		$urlRouterProvider.otherwise('/login');
		$stateProvider.state("login",{
			url:"/login",
			//params: {"homeId" : null},
			controller: 'loginController',
	        templateUrl: "templates/login.html",
	        resolve: {
	            loadCtrl: ["$q", function($q) { 
	            	var deferred = $q.defer();
	            	//异步加载controller／directive/filter/service
	            	require([
	            		'controller/loginController'
	            	], function() { deferred.resolve(); });
	            	return deferred.promise;
	            }]
	        }
		})
		.state("register",{
			url:"/register",
			//params: {"homeId" : null},
			controller: 'registerController',
	        templateUrl: "templates/register.html",
	        resolve: {
	            loadCtrl: ["$q", function($q) { 
	            	var deferred = $q.defer();
	            	//异步加载controller／directive/filter/service
	            	require([
	            		'controller/registerController'
	            	], function() { deferred.resolve(); });
	            	return deferred.promise;
	            }]
	        }
		})
		.state("chat",{
			url:"/chat",
			controller: 'chatController',
			templateUrl: "templates/chat.html",
	        resolve: {
	            loadCtrl: ["$q", function($q) { 
	            	var deferred = $q.defer();
	            	//异步加载controller／directive/filter/service
	            	require([
	            		'controller/chatController'
	            	], function() { deferred.resolve(); });
	            	return deferred.promise;
	            }]
	        }
		})
		.state("chat.children",{
			url:"/children",
			views :{
				'recentList' : {
					controller: 'recentController',
					templateUrl: "templates/recentList.html",
					resolve: {
			            loadCtrl: ["$q", function($q) { 
			            	var deferred = $q.defer();
			            	//异步加载controller／directive/filter/service
			            	require([
			            		'controller/recentController'
			            	], function() { deferred.resolve(); });
			            	return deferred.promise;
			            }]
			        }
				},
				'content' : {
					controller: 'noContentController',
					templateUrl: "templates/noContent.html",
					resolve: {
			            loadCtrl: ["$q", function($q) { 
			            	var deferred = $q.defer();
			            	//异步加载controller／directive/filter/service
			            	require([
			            		'controller/noContentController'
			            	], function() { deferred.resolve(); });
			            	return deferred.promise;
			            }]
			        }
				}
			}
		})
		.state("chat.children.content",{
			url : "/content",
			controller: 'hasContentController',
			views : {
				'content@chat' : {
					controller: 'hasContentController',
					templateUrl: "templates/hasContent.html",
					resolve: {
			            loadCtrl: ["$q", function($q) { 
			            	var deferred = $q.defer();
			            	//异步加载controller／directive/filter/service
			            	require([
			            		'controller/hasContentController'
			            	], function() { deferred.resolve(); });
			            	return deferred.promise;
			            }]
			        }
				}
			}
		});

		
	}]).controller('mainController',function($scope, $state, $location){
		
		
	});
	
	return app;
})