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
    	$scope.barcode = res[4];
    	
    	$scope.$emit('menuOption','NULL'); 		
		ProductsResource.get({
			//tenantId : '1',
			tenantId : $scope.virtualStorageId,
			//barcode : '1.1234567890'
			barcode: $scope.barcode
		}).$promise.then(function(result) {
			$scope.product = result;
			
			$scope.product.chips = [];
			$scope.unitAbbreviation = $scope.product.unit.abbreviation;
			
			if($scope.product.categories != null) {
				var count = $scope.product.categories.length;
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

(function() {
	'use strict';
	angular.module('sapo').controller('ViewProductStockController',
			ViewProductStockController);
	ViewProductStockController.$inject = [ 'ProductStockResource', '$scope', '$cookies', '$location' ];
	/* @ngInject */
	function ViewProductStockController(ProductStockResource, $scope, $cookies, $location) {
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	$scope.hasStock = false;
		ProductStockResource.query({
			VSId : $scope.virtualStorageId,
			barcode : $scope.barcode
//			VSId : '1',
//			barcode : '1.1234567890'
		}).$promise.then(function(result) {
			$scope.stockmovements = result;
//			if ($scope.stockmovements != null && $scope.stockmovements.length > 0) {
//				$scope.hasStock = true;
//			}
			if($scope.stockmovements != null && (typeof $scope.stockmovements[0].stock !== 'undefined')) {
				$scope.hasStock = true;
				var count = $scope.stockmovements.length;
				var j;
				var finalStock = $scope.stockmovements[0].stock;
				$scope.stockmovements[0].finalStock = finalStock;
				
				for (j = 1; j < count; j++) {
					finalStock += $scope.stockmovements[j].stock;
					$scope.stockmovements[j].finalStock = finalStock;
				}
			}
			console.log("hasStock");
			console.log($scope.hasStock);
			console.log($scope.stockmovements);
		});
	}
})();

(function() {
	'use strict';
	angular.module('sapo').controller('ViewProductPriceController',
			ViewProductPriceController);
	ViewProductPriceController.$inject = [ 'ProductPriceResource', '$scope', '$cookies', '$location' ];
	/* @ngInject */
	function ViewProductPriceController(ProductPriceResource, $scope, $cookies, $location) {
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		$scope.hasPriceChange = false;
		ProductPriceResource.query({
			VSId : $scope.virtualStorageId,
			barcode : $scope.barcode
//			VSId : '1',
//			barcode : '1.1234567890'
		}).$promise.then(function(result) {
			$scope.pricemovements = result;
			if ($scope.pricemovements != null && $scope.pricemovements.length > 0) {
				$scope.hasPriceChange = true;
			}
			console.log("hasPriceChange");
			console.log($scope.hasPriceChange);
			console.log($scope.pricemovements);
		});
	}
})();