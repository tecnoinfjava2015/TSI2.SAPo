angular.module("app").controller('productsFormCtrl', function($scope,PostResource,$location){
	$scope.title = "Create Product";
	$scope.savePost = function () {
		PostResource.save({data: $scope.product},function(data){
			console.log(data);
			$location.path("/product/create");
		});
	}
	$scope.reset = function() {
        $scope.product = angular.copy($scope.master);
    };
    $scope.reset();
});