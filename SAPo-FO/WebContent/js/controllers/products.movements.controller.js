(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementController', ProductMovementController);
    ProductMovementController.$inject = ['$scope','ProductsResource'];
    /* @ngInject */
    function ProductMovementController($scope,ProductsResource) {
    	var vm = this
		vm.title = "Nuevo Movimiento";
		vm.newMovement=newMovement;
		vm.loadProducts = loadProducts;
		


		function loadProducts(s){
			ProductsResource.query({
	            tenantId: 1,
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