(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementCreateController', ProductMovementCreateController);
    ProductMovementCreateController.$inject = ['$scope','ProductsResource','$location', '$cookies'];
    /* @ngInject */
    function ProductMovementCreateController($scope,ProductsResource,$location, $cookies) {
    	var vm = this;
		vm.title = "Nuevo Movimiento";
		vm.loadProducts = loadProducts;
		
		var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];

		function loadProducts(s){
			 return ProductsResource.query({
	            tenantId: $scope.virtualStorageId,
	            limit: 5, 
	            minSearch: true,
	            search: s
	        }).$promise; 
		} 
    }  
})(); 