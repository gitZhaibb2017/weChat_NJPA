;
/*
 * 命名空间
 * lsg 2017-04-13
 */
var app = angular.module("app", ['ngRoute', 'ngTouch', 'ngSanitize']);

//获取cookies
app.constant('BASE_DIR', location.protocol + "//" + location.host + "/pawj/");//"http://cephapp.iask.in:7490/pawj/");//location.protocol + "//" + location.host + "/posp/pawj/");//"http://cephapp.iask.in:7490/pawj/");//location.protocol + "//" + location.host + "/pawj/");
//获取cookies
app.constant('getCookie', function(name) {
	var arr,
		reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg)) {
		return unescape(arr[2]);
	} else {
		return null;
	}
});
//获取URL参数
 app.constant('getQueryString', function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) {
		return unescape(r[2]); 
	} else {
		return null; 
	}
 });
//路由
app.config(function ($routeProvider){
	$routeProvider.when('/', {
		templateUrl: 'wechat/view/main.html',
		controller: 'mainCtrl'
	}).when('/campaign', {
		templateUrl: 'wechat/view/campaign.html',
		controller: 'campaignCtrl'
	}).when('/selection', {
		templateUrl: 'wechat/view/selection.html',
		controller: 'selectionCtrl'
	}).when('/result', {
		templateUrl: 'wechat/view/result.html',
		controller: 'resultCtrl'
	}).when('/upload', {
		templateUrl: 'wechat/view/upload.html',
		controller: 'uploadCtrl'
	}).otherwise({
		redirectTo: '/'
	})
});
/*app.service("wxService", function($http, http, $timeout, errorMsg) {
	// 调用相机
	this.chooseImage = function(url, success){
		init(url);
	
		wx.ready(function(){
			wx.chooseImage({
				count: 9, // 默认9
				sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
				sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
				success: function (res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					$timeout(function(){
						for(var i = 0; i < localIds.length; i++){
							wx.uploadImage({
							    localId: localIds[i],
							// 需要上传的图片的本地ID，由chooseImage接口获得
							    isShowProgressTips: 1, // 默认为1，显示进度提示
							    success: function (res) {
								    var serverId = res.serverId; // 返回图片的服务器端ID
								    $http.post(http + "/customer/portrait?fileId=" + serverId).success(function(resp){
								    if(resp.codeText == "OK") {
									    layer.msg("上传成功");
									    success(resp.data);
								    }else{
								    	layer.msg("上传失败");
								    }
								    }).error(function(resp) {
								    	layer.msg(errorMsg);
								    });
							    }
							});
						}
					}, 0);
				}
			});
		});
	}
	// 初始化
	function init(url){
		var index = url.indexOf("#");
		if(index > -1){
			url = url.substring(0, index);
		}
	
		$http.post(http + "/customer/signature?currentUrl=" + url).success(function(resp) {
			if(resp.codeText == "OK"){
				wx.config({
					debug: false, 
					// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: resp.data.appId, 
					// 必填，公众号的唯一标识
					timestamp: resp.data.timestamp, 
					// 必填，生成签名的时间戳
					nonceStr: resp.data.noncestr, 
					// 必填，生成签名的随机串
					signature: resp.data.signature,
					// 必填，签名
					jsApiList: ['chooseImage','uploadImage'] // 必填，需要使用的JS接口列表
				});
				 
				wx.error(function(res){
					console.log("wx.error");
				});
			}
		});
	}
	//初始化
	window.onerror = function(msg, url, line) {
		var idx = url.lastIndexOf("/");
		if (idx > -1) {
			url = url.substring(idx + 1);
		}
		alert("ERROR in " + url + " (line #" + line + "): " + msg);
		return false;
	};
});*/

