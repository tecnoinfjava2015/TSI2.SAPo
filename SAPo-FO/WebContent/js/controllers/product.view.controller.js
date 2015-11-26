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
			
			if($scope.product.categories != null) {
				var count = $scope.product.categories.lenght;
				var k = 0;
				for (k = 0; k < count; k++) {
					var category = $scope.product.categories[k];
					$scope.product.chips.push(category.name);
				}
			}
			
			console.log($scope.product);

			$scope.myInterval = 3000;
			
			if ($scope.product.images != null) { //si el producto no tiene imagenes... le cargo una default?
				var i = 0;
				$scope.slides = [];
				var count = $scope.product.images.length;
				for (i = 0; i < count; i++) {
					var slide = [];
					slide.image = $scope.product.images[i];
					slide.active = false;
					$scope.slides.push(slide);
				}
			} else {
				$scope.slides = [];
				var slide = [];
				slide.image = "http://2.bp.blogspot.com/-qgXP4QFYLvY/Uj4q7um1gBI/AAAAAAAAHAE/7VH2vszmAfs/s1600/imagen_no_disponible.gif";
				slide.active = true;
				$scope.slides.push(slide);
			}
			
		});

	}
})();