define(['app','jquery','step','validate','validate_msg'],function(app, $){
	app.register.directive('myStep',function($timeout, $compile, $http){
		return {
	        restrict: 'AE',
	        link : function(scope, ele, attrs) {
	        	$(ele).steps({
        			bodyTag: "fieldset",
        			onStepChanging : function(event, currentIndex, newIndex){
        				if (currentIndex > newIndex) {
                            return true;
                        }
        				var form = $(this);

                        // Clean up if user went backward before
                        if (currentIndex < newIndex) {
                            // To remove error styles
                            $(".body:eq(" + newIndex + ") label.error", form).remove();
                            $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
                        }
                        return form.valid();
        			},
        			onFinishing: function (event, currentIndex) {
        				var form = $(this);
        				form.validate().settings.ignore = ":disabled";
                        return form.valid();
        			},
        			onFinished: function (event, currentIndex) {
                        var form = $(this);
                        var formData = new FormData(form[0]);
                        //formData.append('file', scope.cutFile);
                        formData.append('BIGFILE', $image.cropper("getCroppedCanvas").toDataURL('image/png'));
                        formData.append('SMALLFILE', $image.cropper("getCroppedCanvas").toDataURL('image/png', 0.5));
                        var promise = $http({
            	            method:"post",
            	            data:formData,
            	            headers: {'Content-Type':undefined},
            	            transformRequest: angular.identity,
            	            url:"registerAction.do?method=regist",
            	            cache: true
                    	});
            			promise.success(function(data){
            				
            			});
                    }
        		}).validate({
                    errorPlacement: function (error, element) {
                        element.after(error);
                    },
                    rules: {
                    	rPassword: {
                    		equalTo: "#password"
                        }
                    }
                });
	        	//加载切图插件
	    		var $image = $(".image-crop > img");
	            $($image).cropper({
	                aspectRatio: 8/9,
	                autoCropArea : 0.8,
	                resizable : true,
	                zoomable : false,
	                preview: ".img-preview",
	                done: function (data) {
	                	
	                }
	            });
	            //切换图片
	            var $inputImage = $("#inputImage");
	            if (window.FileReader) {
	                $inputImage.change(function () {
	                    var fileReader = new FileReader(),
	                        files = this.files,
	                        file;

	                    if (!files.length) {
	                        return;
	                    }

	                    file = files[0];

	                    if (/^image\/\w+$/.test(file.type)) {
	                        fileReader.readAsDataURL(file);
	                        fileReader.onload = function () {
	                            $inputImage.val("");
	                            $image.cropper("reset", true).cropper("replace", this.result);
	                        };
	                    } else {
	                        showMessage("请选择图片文件");
	                    }
	                });
	            } else {
	                $inputImage.addClass("hide");
	            }
	            
	        	scope.$watch(function () {return scope.$eval(attrs.ngBindHtml);},
		          function(html) {
		            ele.html(html);
		            $compile(ele.contents())(scope);
		         });
	        },
	        templateUrl: 'templates/registerContent.html',
	        scope : false,
	        transclude: true
	    };
	});
})