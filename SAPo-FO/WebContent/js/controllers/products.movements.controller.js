(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementController', ProductMovementController);
    ProductMovementController.$inject = ['$scope','ProductsResource', '$location', '$cookies'];
    /* @ngInject */
    function ProductMovementController($scope,ProductsResource, $location, $cookies) {
    	var vm = this
		vm.title = "Nuevo Movimiento";
		vm.newMovement=newMovement;
		vm.loadProducts = loadProducts;
		
		var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];

		function loadProducts(s){
			ProductsResource.query({
	            tenantId: $scope.virtualStorageId,
	            limit: 5,
	            minSearch: true,
	            search: s
	        }).$promise.then(function(result) {
	            
	            return result;
	            
	        }); 
		}
		function newMovement(type){
			switch(type) {
				case '+':
					alert("ENTRADA DE STOOOOOCK");
					break;
				case '-':
					alert("SALIDA DE STOOOOOCK");
					break;
			}
		}
    }  
})();