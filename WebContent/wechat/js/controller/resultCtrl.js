;
/*
 * 查看评选结果控制器
 * resultCtrl
 * lsg 2017-04-14
 */
app.controller('resultCtrl', ['$scope', '$rootScope', '$http', '$location', 'BASE_DIR', function($scope, $rootScope, $http, $location, BASE_DIR) {
	window.scrollTo(0,0);
	$scope.limit = 5;
	$scope.infoList = [];
	
	$scope.queryList = function() {
		$http({
			method: "post",
			url: BASE_DIR + "getOrder/" + $scope.limit,
			params: {}, 
			cache: false
		}).then(function successCallback(response) {
				// 请求成功执行代码
//				alert("1:" + response.data.phoneNo);
				$scope.infoList = response.data;
				
		}, function errorCallback(response) {
				// 请求失败执行代码
			alert("查询数据请求失败！");
		});
	};
	
	$scope.queryList();
}]);