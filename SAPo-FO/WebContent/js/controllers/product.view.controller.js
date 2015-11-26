(function() {
	'use strict';
	angular.module('sapo').controller('ViewProductController',
			ViewProductController);
	ViewProductController.$inject = [ 'ProductsResource', '$scope', '$rootScope' ];
	/* @ngInject */
	function ViewProductController(ProductsResource, $scope, $rootScope) {
		$scope.title = 'Producto';
		var resource = new ProductsResource();
		
		ProductsResource.get({
			tenantId : $rootScope.virtualStorageId,
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