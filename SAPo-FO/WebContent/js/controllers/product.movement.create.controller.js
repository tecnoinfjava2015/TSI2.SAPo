(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementCreateController', ProductMovementCreateController);
    ProductMovementCreateController.$inject = ['$scope','ProductsResource','$q'];
    /* @ngInject */
    function ProductMovementCreateController($scope,ProductsResource,$q) {
    	var vm = this;
    	
		vm.title = "Nuevo Movimiento";
		vm.loadProducts = loadProducts;
		


		function loadProducts(s){
			 return ProductsResource.query({
	            tenantId: 1,
	            limit: 5, 
	            minSearch: true,
	            search: s
	        }).$promise; 
		} 
    }  
})();