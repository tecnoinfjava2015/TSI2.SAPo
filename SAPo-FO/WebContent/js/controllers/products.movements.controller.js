(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductMovementController', ProductMovementController);
    ProductMovementController.$inject = ['$scope','$mdDialog'];
    /* @ngInject */
    function ProductMovementController($scope,$mdDialog) {
    	var vm = this;
		vm.title = "Nuevo Movimiento";
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