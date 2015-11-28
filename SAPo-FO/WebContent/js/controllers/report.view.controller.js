(function() {
	'use strict';
	angular.module('sapo')
	.controller('ReportsController', ReportsController)
	.config(function($mdDateLocaleProvider) {
		$mdDateLocaleProvider.formatDate = function(date) {
			return moment(date).format('YYYY-MM-DD');
		};
	});
	ReportsController.$inject = ['$scope', 'ReportsResource', 'ReportsResourceVSWorth', 'ReportsResourceMovList', '$cookies'];
	/* @ngInject */
	function ReportsController($scope, ReportsResource, ReportsResourceVSWorth, ReportsResourceMovList, $cookies) {
		$scope.today = new Date();
		$scope.hideMovements = true;
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		
		ReportsResourceVSWorth.get({
			tenantId: $scope.virtualStorageId
    	}).$promise.then(function(result) {
            $scope.vsWorth = "$ " + result.data;
        });
		
		$scope.getMovements = function(){
			var fromDate = moment(this.from_Date).format('YYYY-MM-DD');
			var toDate = moment(this.to_Date).format('YYYY-MM-DD');
			ReportsResourceMovList.query({tenantId: $scope.virtualStorageId}, { fromDate: fromDate, toDate: toDate })
			.$promise.then(function(result) {
	            $scope.movements = result;
	            $scope.hideMovements = false;
	            
	        });
		}
	}
})();