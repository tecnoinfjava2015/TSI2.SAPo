(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('DashboardController', DashboardController);
    DashboardController.$inject = ['$scope'];
    /* @ngInject */
    function DashboardController($scope) {
    	$scope.title = 'Dashboard';    	
    }
})();