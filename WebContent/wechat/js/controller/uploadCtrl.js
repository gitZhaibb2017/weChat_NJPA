;
/*
 * 上传照片控制器
 * uploadCtrl
 * lsg 2017-04-14
 */
var DEL_LOCAL_IDS = [];
app.controller('uploadCtrl', ['$scope', '$http', '$location', '$timeout', '$rootScope', 'BASE_DIR',  
                              function($scope, $http, $location, $timeout, $rootScope, BASE_DIR) {
	window.scrollTo(0,0);
	DEL_LOCAL_IDS = [];//初始化删除数组的模型
	$rootScope.localIds = [];
	$scope.openId = $rootScope.openId;
	$scope.appId = $rootScope.appId || "wx3bab3576f7554012";
	$scope.photoDate = new Date();
//	$scope.maxDate = new Date();
	$scope.phoneNo = "";
	$scope.showName = "";
	$scope.description = "";
	$scope.uploadFlag = true;
//	$scope.signatureInfo = {};
	
	$scope.msg = {
			info: "",
			isShow: false
	};
	//提示信息
	$scope.showMsg = function(_info){
		$scope.msg = {
				info: _info,
				isShow: true
		};
		$timeout(function(){
			$scope.msg.isShow = false
		}, 2000);
	};
	
	$scope.$watch("photoDate", function(newV, oldV, scope){
		console.log(newV);
		if (newV != oldV && newV > new Date()) {
			$scope.showMsg("拍摄时间不能大于当前时间");
			$scope.photoDate = new Date();
		}
	})
	
	$scope.initInfo = function() {
		//获取用户基本信息
		$http({
			method: "post",
			url: BASE_DIR + "getUserInfoByOpenId",
			params: {
				openId: $scope.openId
			}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
				$scope.phoneNo = response.data.phoneNo;
				$scope.showName = response.data.showName;
		}, function errorCallback(response) {
				// 请求失败执行代码
			$scope.showMsg("获取到用户信息失败，请重新加载！");
		});
		
	};
	
	$scope.initInfo();
    
    $scope.localIds = [];//本地勾选的图片地址数组{uri: ""}
    $scope.serverIds = [];//上传的图片id数组
    
    $scope.addImages = function() {
    	var _count = 9 - $scope.localIds.length + DEL_LOCAL_IDS.length;
//    	alert("pic:" + _count + "|| len:" + $scope.localIds.length);
        wx.chooseImage({
            count: _count, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                if (localIds.length > 0) {
                	$scope.uploadFlag = false;
                } else {
                	$scope.uploadFlag = true;
                }
                $.each(localIds, function(){
//                	$("div.u-img #addImages").before('<a><img src="' + this + '"><span class="del" onClick="delImg(this)">x</span></a>');
                	$("div.u-img #addImages").before('<a><img src="' + this + '"><span class="del" data-img="' + this + '">x</span></a>');
                });
                $timeout(function(){
//                	$.each(localIds, function(){
//                		$scope.localIds.push({"uri": this});
//                	})
                	$scope.localIds = $scope.localIds.concat(localIds);
//                	$scope.serverIds = [];
//                	alert("L1:" + $scope.localIds.join("||"));
                	$scope.uploadToWx(localIds);
                	$scope.previewImage();
                }, 0);
            }
        });
    };
    
    $scope.uploadToWx = function(localIds){
    	var localId = localIds.pop();
    		wx.uploadImage({
			    localId: localId,
				// 需要上传的图片的本地ID，由chooseImage接口获得
			    isShowProgressTips: 0, // 默认为1，显示进度提示
			    success: function (res) {
				    var serverId = res.serverId; // 返回图片的服务器端ID
					$scope.serverIds.unshift(serverId);
					if(localIds.length > 0) {
						$scope.uploadToWx(localIds);
					} else {
						$scope.uploadFlag = true;
					}
				}
    		});
    };
    
    //上传照片以及相关信息
    $scope.uploadImg = function() {
    	if (!$scope.uploadFlag) {
    		$scope.showMsg("正在加载图片，请稍后再操作！");
    		return;
    	}
    	var localIds = [];
    	var serverIds = [];
    	for(var i = 0; i < $scope.localIds.length; i++) {
    		var flag = true;
    		$.each(DEL_LOCAL_IDS, function(){
    			if (this == $scope.localIds[i]) {
    				flag = false;
    			}
    		});
    		if (flag) {
    			localIds.push($scope.localIds[i]);
    			serverIds.push($scope.serverIds[i]);
    		}
    	}
    	if ($scope.getBytesCount($scope.description) == 0 || $scope.getBytesCount($scope.description) > 200) {
    		$scope.showMsg("文本框不能为空，且中文字不能超过100，英文以及数字不能超过200！");
    		return;
    	}
    	if ( $scope.photoDate > new Date()) {
    		$scope.showMsg("拍摄时间不能大于当前时间");
			return;
		}
    	if (localIds.length < 2) {
    		$scope.showMsg("上传照片，一次不能少于俩张！");
    		return;
    	}
    	if ($scope.phoneNo == "" || $scope.showName == "") {
    		$scope.showMsg("为了方便联系，请填写手机号码和姓名！");
    		return;
    	}
    	if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($scope.phoneNo))) {
    		$scope.showMsg("请输入正确的手机号码！"); 
    		return;
    	}
//    	var localIds = [];
//    	$.each($scope.localIds, function(){
//    		localIds.push(this.uri);
//    	});
    	$http({
			method: "post",
			url: BASE_DIR + "imgUploadByWeChat/" + $scope.openId,
			params: {
				mediaIds: serverIds.join(","),
				localIds: localIds.join(","),
				showName: $scope.showName,
				phoneNo: $scope.phoneNo,
				photoDate: $scope.photoDate.getFullYear() + "/" + ($scope.photoDate.getMonth() + 1) + "/" + $scope.photoDate.getDate(),
				description: $scope.description
			},
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//			$rootScope.localIds = $scope.localIds;
			history.go(-1);
//			$location.path("/campaign");
		}, function errorCallback(response) {
				// 请求失败执行代码
			$scope.showMsg("上传图片失败，请重新上传！");
		});
    };
    
    /*
	 * 图片预览
	 * lsg 2017-5-7
	 */
	$scope.previewImage = function () {
		$(".upload .u-ai .u-img").off("click").on("click", "img", function(){
			var $this = $(this);
			var uri = $this.attr("src");
			var $imgs = $(".upload .u-ai .u-img").find("img");
			var urls = [];
			if ($this.attr("src") != "wechat/img/add_pic.png") {
				$.each($imgs, function(){
					var imgUrl = $(this).attr("src");
					if(imgUrl != "wechat/img/add_pic.png") {
						urls.push(imgUrl);
					}
				});
				wx.previewImage({
					current: uri, // 当前显示图片的http链接
					urls: urls // 需要预览的图片http链接列表
				});
			}
		});
	};
	
	
	//获取字符串字节数
	$scope.getBytesCount = function(str) { 
		var bytesCount = 0; 
		if (str != null) { 
			for (var i = 0; i < str.length; i++) { 
				var c = str.charAt(i); 
				if (/^[\u0000-\u00ff]$/.test(c)) { 
					bytesCount += 1; 
				} else { 
					bytesCount += 2; 
				} 
			} 
		} 
		return bytesCount; 
	};

	//删除图片
	$("body").off("click").on("click", "div.u-img span.del", function(){
		var $this = $(this);
		DEL_LOCAL_IDS.push($this.attr("data-img"));
		$this.parent().remove();
	});
}]);