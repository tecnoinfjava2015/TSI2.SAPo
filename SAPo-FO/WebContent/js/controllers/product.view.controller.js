(function() {
	'use strict';
	angular.module('sapo').controller('ViewProductController',
			ViewProductController);
	ViewProductController.$inject = [ 'ProductsResource', '$scope', '$cookies', '$location' ];
	/* @ngInject */
	function ViewProductController(ProductsResource, $scope, $cookies, $location) {
		$scope.title = 'Producto';
		var resource = new ProductsResource();
		
		var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];
		
		ProductsResource.get({
			tenantId : $scope.virtualStorageId,
			barcode : 'test'
		}).$promise.then(function(result) {
			$scope.product = result;
			
			//hardcodeo para testear lo que va a venir en el product
			$scope.product.chips = [];
			$scope.product.chips.push("Tecnologia");
			$scope.product.chips.push("Celulares");
			$scope.product.chips.push("Android");
			//fin hardcodeo
			
			console.log($scope.product);

			$scope.myInterval = 3000;
			var i = 0;
			$scope.slides = [];
			var count = $scope.product.images.length;
			for (i = 0; i < count; i++) {
				var slide = [];
				slide.image = $scope.product.images[i];
				slide.active = false;
				$scope.slides.push(slide);
			}
			
		});

	}
})();