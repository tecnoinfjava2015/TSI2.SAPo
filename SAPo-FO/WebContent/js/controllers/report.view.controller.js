(function() {
	'use strict';
	angular.module('sapo').controller('ReportsController',
			ReportsController)
			.config(function($mdDateLocaleProvider) {
  $mdDateLocaleProvider.formatDate = function(date) {
    return moment(date).format('YYYY-MM-DD');
  };
});
	ReportsController.$inject = ['ReportsResource', '$scope' ];
	/* @ngInject */
	function ReportsController(ReportsResource, $scope) {
		//$scope.title = 'Hola Araujo!!';
		$scope.today = new Date();
		$scope.vsValue = '$ '+'321'+',00'; //llamar a /api/VirtualStorage/worth?id={int}

		ReportsResource.get({
            tenantId: 290
    	}).$promise.then(function(result) {
            $scope.movementQty = result;
            console.log($scope.movementQty);
        });
		
		
	}
	
	
})();