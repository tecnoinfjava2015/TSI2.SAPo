(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('DashboardController', DashboardController);
    DashboardController.$inject = ['$scope', '$routeParams'];
    /* @ngInject */
    function DashboardController($scope, $routeParams) {
    	$scope.title = 'Dashboard';   
//    	$scope.$emit('menuOption',null); 
    	var tenant = $routeParams.tenantName;
    	
    	if (typeof tenant !== 'undefined') {
    		$scope.disableReport = false;
    	}
    	else {
    		$scope.disableReport = true;
    	}
    	//console.log(tenant);
    }
})();