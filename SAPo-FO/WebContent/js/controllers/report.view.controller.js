(function() {
	'use strict';
	angular.module('sapo').controller('ReportsController',
			ReportsController);
	ReportsController.$inject = ['ReportsResource', '$scope' ];
	/* @ngInject */
	function ReportsController(ReportsResource, $scope) {
		$scope.title = 'Hola Araujo!!';

		ReportsResource.get({
            tenantId: 290
    	}).$promise.then(function(result) {
            $scope.movementQty = result;
            console.log($scope.movementQty);
        });
		
		
	}
	
})();