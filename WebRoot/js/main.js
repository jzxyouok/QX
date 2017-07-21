require.config({
	baseUrl: 'js',
	paths: {
		'app': 'app',
		'angular': 'angular.min',
		'router': 'angular-ui-router',
		'domReady': 'domReady',
		'jquery': 'jquery-1.12.3.min',
		'bootstrap': 'bootstrap.min',
		'validate' : 'plugins/validate/jquery.validate.min',
		'validate_msg' : 'plugins/validate/messages_zh.min',
		'slimscroll': 'plugins/slimscroll/jquery.slimscroll.min',//滚动条
		//'cropper' : 'plugins/cropper/cropper.min',
		'cropper' : 'plugins/cropper/cropper',
		'step' : 'plugins/staps/jquery.steps.min',
		'pushlet' : 'ajax-pushlet-client'
	},
	shim: {
		'angular': {
			exports: 'angular'
		},
		'jquery': {
			exports: 'jquery'
		},
		'bootstrap': {
			deps: ['jquery']
		},
		'slimscroll': {
			deps: ['jquery']
		},
		'validate': {
			deps: ['jquery'],
			exports: 'validate'
		},
		'validate_msg': {
			deps: ['jquery','validate']
		},
		'cropper' : {
			deps: ['jquery']
		},
		'step' : {
			deps: ['jquery']
		},
		'router': {
			deps: ['angular']
		},
		'pushlet' : {
			exports: 'pushlet'
		},
		'app': {
			deps: ['router']
		}
	}
});
define(['require', 'angular', 'app', 'router'], function(require, angular){
	
	// 初始化mainModule模块
	require(['domReady!'], function(document) {
		angular.bootstrap(document, ['qxMainApp']);
	})
})