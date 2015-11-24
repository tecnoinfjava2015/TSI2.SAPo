(function() {
	'use strict';
	angular.module('sapo').controller('ViewProductController',
			ViewProductController);
	ViewProductController.$inject = [ 'ProductsResource', '$scope' ];
	/* @ngInject */
	function ViewProductController(ProductsResource, $scope) {
		$scope.title = 'Producto';
		var resource = new ProductsResource();

		ProductsResource.get({
			tenantId : 279,
			barcode : 'test'
		}).$promise.then(function(result) {
			$scope.product = result;
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