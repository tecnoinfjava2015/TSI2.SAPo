(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ViewProductController', ViewProductController);
    ViewProductController.$inject = ['ProductsResource', '$scope'];
    /* @ngInject */
    function ViewProductController(ProductsResource, $scope) {    	
    	$scope.title = 'Producto';
    	var resource = new ProductsResource();
    	
    	ProductsResource.get({
            tenantId: 290,
            barcode : 'test'
    	}).$promise.then(function(result) {
            $scope.product = result;
            console.log($scope.product);
        });
    	
    }
})();