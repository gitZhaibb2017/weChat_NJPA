;
/*
 * 参加活动控制器
 * campaignCtrl
 * lsg 2017-04-14
 */
app.controller('campaignCtrl', ['$scope', '$rootScope', '$http', '$location', 'BASE_DIR', function($scope, $rootScope, $http, $location, BASE_DIR) {
	window.scrollTo(0,0);
	$scope.openId = $rootScope.openId;
	$scope.infoList = [];
	$scope.uploadId = "";
	$scope.isShow = true;
	
	$scope.initInfoByTime = function() {
		//获取用户基本信息
		$http({
			method: "post",
			url: BASE_DIR + "getMyImg/" + $scope.openId + "/M",
			params: {}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
				$scope.infoList = response.data;
				$.each($scope.infoList, function() {
					$.each(this.imageBases, function() {
						if (this.imgUri == "Y") {
							this.imgUri = "http://file.api.weixin.qq.com/cgi-bin/media/get?" + this.localId;
						} else {
							this.imgUri = BASE_DIR + "img/" + this.dbid;
						}
					});
				});
				if ($scope.infoList.length > 0) {
					$scope.isShow = true;
				} else {
					$scope.isShow = false;
				}
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("查询时间轴数据请求失败！");
		});
	};
	
	$scope.delInfo = function(uploadId){
		$scope.uploadId = uploadId;
		$http({
			method: "post",
			url: BASE_DIR + "delUpload/" + $scope.openId + "/" + uploadId,
			params: {}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
//				$scope.infoList = response.data;
			for(var i = 0; i < $scope.infoList.length; i++) {
				if ($scope.infoList[i].dbid == $scope.uploadId) {
					$scope.infoList.splice(i, 1);
				}
			}
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("查询时间轴数据请求失败！");
		});
	};
	 /*
	 * 图片预览
	 * lsg 2017-5-7
	 */
	$scope.previewImage = function (uri, imageBases) {
		var urls = [];
		$.each(imageBases, function() {
			urls.push(this.imgUri);
		});
		wx.previewImage({
		    current: uri, // 当前显示图片的http链接
		    urls: urls // 需要预览的图片http链接列表
		});
	};
	
	$scope.initInfoByTime();
}]);