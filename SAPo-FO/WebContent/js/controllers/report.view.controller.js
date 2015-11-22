(function() {
	'use strict';
	angular.module('sapo').controller('ReportsController',
			ReportsController);
	ReportsController.$inject = ['$scope' ];
	/* @ngInject */
	function ReportsController($scope) {
		$scope.title = 'Hola Araujo!!';
		
	}
	
});