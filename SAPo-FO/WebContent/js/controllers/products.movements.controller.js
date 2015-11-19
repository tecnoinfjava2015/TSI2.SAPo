(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementController', ProductMovementController);
    ProductMovementController.$inject = ['$scope'];
    /* @ngInject */
    function ProductMovementController($scope) {
    	var vm = this
		vm.newMovement=newMovement;

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