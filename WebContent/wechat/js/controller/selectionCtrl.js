;
/*
 * 参加评选控制器
 * selectionCtrl
 * lsg 2017-04-14
 */
app.controller('selectionCtrl', ['$scope', '$rootScope', '$http', '$location', 'BASE_DIR', function($scope, $rootScope, $http, $location, BASE_DIR) {
	window.scrollTo(0,0);
	
	$scope.openId = $rootScope.openId;
	$scope.infoList = [];
	$scope.leftList = [];
	$scope.rightList = [];
	$scope.imgId = "";
	$scope.listType = "leftList";
	$scope.pageNum = 1;
	$scope.pageSize = 6;
	$scope.isShow = false;
	
	$scope.queryList = function() {
		$http({
			method: "post",
			url: BASE_DIR + "imgList/" + $scope.openId,
			params: {
				pageSize: $scope.pageSize,
				pageNum: $scope.pageNum
			}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
				var infoList = response.data || [];
				for(var i = 0; i < infoList.length; i++) {
					infoList[i].imgUri = BASE_DIR + "img/" + infoList[i].dbid;
					infoList[i].countAgree = infoList[i].countAgree || 0;
					infoList[i].countMy = infoList[i].countMy || 0;
					if (i % 2 == 0) {
						$scope.leftList.push(infoList[i]);
					} else {
						$scope.rightList.push(infoList[i]);
					}
				}
				if (infoList.length < $scope.pageSize){
					$scope.isShow = false;
				} else {
					$scope.isShow = true;
				}
				$scope.infoList = $scope.infoList.concat(infoList);
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("查询数据请求失败！");
		});
	};
	
	//获取更多
	$scope.getMore = function() {
		$scope.pageNum++;
		$scope.queryList();
	};
	
	$scope.likeImage = function(imgId, _type) {
		$scope.imgId = imgId;
		$scope.listType = _type;
		$http({
			method: "post",
			url: BASE_DIR + "updateAgree",
			params: {
				openId: $scope.openId,
				imgId: imgId
			}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
			$.each($scope[$scope.listType], function(){
				if(this.dbid == $scope.imgId && this.countMy == '0') {
					this.countMy = '1';
					this.countAgree++;
				} else if (this.dbid == $scope.imgId && this.countMy == '1') {
					this.countMy = '0';
					this.countAgree--;
				}
			});
				
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("查询数据请求失败！");
		});
	};
	
	/*
	 * 图片预览
	 * lsg 2017-5-7
	 */
	$scope.previewImage = function (uri) {
		wx.previewImage({
		    current: uri, // 当前显示图片的http链接
		    urls: [uri] // 需要预览的图片http链接列表
		});
	};
	
	
	$scope.queryList();
}]);