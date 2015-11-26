(function() {
	'use strict';
	angular.module('sapo')
	.controller('ReportsController', ReportsController)
	.config(function($mdDateLocaleProvider) {
		$mdDateLocaleProvider.formatDate = function(date) {
			return moment(date).format('YYYY-MM-DD');
		};
	});
	ReportsController.$inject = ['ReportsResource', 'ReportsResourceVSWorth', 'ReportsResourceMovList', '$scope' ];
	/* @ngInject */
	function ReportsController(ReportsResource, ReportsResourceVSWorth, ReportsResourceMovList, $scope) {
		$scope.today = new Date();

//		ReportsResource.get({
//            tenantId: 1
//    	}).$promise.then(function(result) {
//            $scope.movementQty = result;
//            console.log("qty:");
//            console.log($scope.movementQty);
//        });
				
		ReportsResourceVSWorth.get({
            tenantId: 1
    	}).$promise.then(function(result) {
            $scope.vsWorth = "$ " + result.data;
        });
		
		ReportsResourceMovList.query({tenantId: 1}, {fromDate: '2015-11-20', toDate: '2015-11-25'})
		.$promise.then(function(result) {
            $scope.movements = result;
        });

	}
})();