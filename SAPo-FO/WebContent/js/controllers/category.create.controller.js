(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CreateCategoryController', CreateCategoryController);
    CreateCategoryController.$inject = ['$scope', 'CreateCategoriesResource'];
    /* @ngInject */
    function CreateCategoryController(CreateCategoriesResource, $scope) {
    	$scope.master = {};
    	$scope.create = create;
		$scope.reset = reset;
		
		function create(data) {			
			alert('hola');
			$scope.tenantId = 151;
			$scope.category.$save(function(r) {
				showAlert('Exito!','Se ha creado su categor&iacute;a de forma exitosa');
			}, function(r){
				console.log(r);
				showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
			});
			 
			//pepe.$promise.then(function(result){alert(result.status);});
			
			// showAlert();
		}


		function reset() {
			$scope.category = angular.copy($scope.master);
		};
    }
})();