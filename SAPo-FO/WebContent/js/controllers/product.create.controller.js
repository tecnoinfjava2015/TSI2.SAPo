(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CreateProductController', CreateProductController);
    CreateProductController.$inject = ['CreateProductsResource',  '$scope'];
    /* @ngInject */
    function CreateProductController(CreateProductsResource, $scope) {
    	$scope.title = 'Crear Producto';
    	
    	$scope.insert = insert;
    	
    	function insert(data) {
    		CreateProductsResource.save(data,function(){
    		});
    	}
    }
})();