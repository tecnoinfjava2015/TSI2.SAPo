(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementController', ProductMovementController);
    ProductMovementController.$inject = ['$scope','$mdDialog','ProductMovementDataService'];
    /* @ngInject */
    function ProductMovementController($scope,$mdDialog,ProductMovementDataService) {
    	var vm = this;
		vm.newMovement=newMovement;
//		$scope.$emit('menuOption',null); 
		function newMovement(ev,type){
			switch(type) {
				case '+':
					ProductMovementDataService.data.title = "Entrada de stock";
					ProductMovementDataService.data.modifier = 1;				
					console.log(ProductMovementDataService.data);
					break;
				case '-':
					ProductMovementDataService.data.title = "Salida de stock";
					ProductMovementDataService.data.modifier = -1;
					console.log(ProductMovementDataService.data);
					break;
			}
			$mdDialog.show({
    	    	controller: 'ProductMovementCreateController',
                templateUrl: 'templates/product.movement.create.html',
    	    	
    	        parent: angular.element(document.body),
    	        targetEvent: ev,
    	        clickOutsideToClose:true
    	    })
		}
    }  
})();