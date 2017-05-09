;
/*
 * 主页控制器
 * mainCtrl
 * lsg 2017-04-14
 */
app.controller('mainCtrl', ['$scope', '$http', '$location', 'getCookie', '$rootScope', 'getQueryString', 'BASE_DIR', 
                            function($scope, $http, $location, getCookie, $rootScope, getQueryString, BASE_DIR) {
	
//	$rootScope.appId = "wx4c5352b79d98400d";
	$rootScope.appId = "wx3bab3576f7554012";//平安科技
//	$rootScope.appsecret = "c043b6fdcf0ecfe128ba456972307f3c";
	$rootScope.appsecret = "13559ac5875dd465814cb9350460da5e";//平安科技
//	$rootScope.redirect_uri = encodeURIComponent("http://tonghc.cn/weixin/getOpenId.html");
	$rootScope.redirect_uri = encodeURIComponent("http://pingan.tonghc.cn/getpacode.php?appid=" + $rootScope.appId + "&secret=" + $rootScope.appsecret);
//	$rootScope.openId = getQueryString("openid");
	$rootScope.openId = getCookie("pa_openid");
	$rootScope.getOpenIdUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + $rootScope.appId + "&redirect_uri=" + $rootScope.redirect_uri + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	
	//初始化当前js变量
	$scope.appId = $rootScope.appId;
	$scope.signatureInfo = {};
	
	if(!$rootScope.openId) {
		window.location.href = $rootScope.getOpenIdUrl;
	} else {
//		$rootScope.openId = openId;
//		alert("lsg-openid:" + $rootScope.openId);
		
		//获取公众号签名信息
		$http({
			method: "post",
			url: BASE_DIR + "getSignature",
			params: {
				appid: $scope.appId,
				url: "http://pingan.tonghc.cn/index.html"
			}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("2:" + response.data.nonceStr);
				$scope.signatureInfo = response.data;
				$scope.initWxConfig();
				
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("获取签名信息失败，请重新加载！");
		});
		
	}
	
	$scope.initWxConfig = function() {
    	wx.config({
    		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    		appId: $scope.appId,//'wx3bab3576f7554012', // 必填，公众号的唯一标识
    		timestamp: $scope.signatureInfo.timestamps,//'1493705605', // 必填，生成签名的时间戳
    		nonceStr: $scope.signatureInfo.nonceStr,//'e34ca711193a4058', // 必填，生成签名的随机串
    		signature: $scope.signatureInfo.signature,//'173a21c0d62fd4a6294bc462c40d6649eea4482b',// 必填，签名，见附录1
    		jsApiList: ['chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'onMenuShareTimeline', 'onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    	});
    	wx.ready(function(){
    		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    		$scope.initShareInfo();
    	});
    	wx.error(function(res){
    		// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    	});
    };
    
    $scope.initShareInfo = function() {
    	wx.onMenuShareTimeline({
    	    title: '平安母亲节活动', // 分享标题
    	    link: window.location.href, // 分享链接
    	    imgUrl: BASE_DIR + "img/264", // 分享图标
    	    success: function () { 
    	        // 用户确认分享后执行的回调函数
    	    },
    	    cancel: function () { 
    	        // 用户取消分享后执行的回调函数
    	    }
    	});
    	
    	wx.onMenuShareAppMessage({
    	    title: '平安母亲节活动', // 分享标题
    	    desc: '平安科技祝广大母亲，母亲节快乐！', // 分享描述
    	    link: window.location.href, // 分享链接
    	    imgUrl: BASE_DIR + "img/264", // 分享图标
    	    type: 'link', // 分享类型,music、video或link，不填默认为link
    	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
    	    success: function () { 
    	        // 用户确认分享后执行的回调函数
    	    },
    	    cancel: function () { 
    	        // 用户取消分享后执行的回调函数
    	    }
    	});
    };
	
	
}]);