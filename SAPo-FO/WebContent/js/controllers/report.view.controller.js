(function() {
	'use strict';
	angular.module('sapo').controller('ReportsController',
			ReportsController)
			.config(function($mdDateLocaleProvider) {
  $mdDateLocaleProvider.formatDate = function(date) {
    return moment(date).format('YYYY-MM-DD');
  };
});
	ReportsController.$inject = ['ReportsResource', '$scope', '$rootScope'];
	/* @ngInject */
	function ReportsController(ReportsResource, $scope, $rootScope) {
		//$scope.title = 'Hola Araujo!!';
		$scope.today = new Date();
		$scope.vsValue = '$ '+'321'+',00'; //llamar a /api/VirtualStorage/worth?id={int}
		var vsId = 1;
		if (typeof $rootScope.virtualStorageId !== 'undefined') {
			vsId = $rootScope.virtualStorageId;
		}
		
		ReportsResource.get({
            tenantId: vsId
    	}).$promise.then(function(result) {
            $scope.movementQty = result;
            console.log($scope.movementQty);
        });
		
		
	}
	
	
})();