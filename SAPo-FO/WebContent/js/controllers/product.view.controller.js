(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ViewProductController', ViewProductController);
    ViewProductController.$inject = ['$scope'];
    /* @ngInject */
    function ViewProductController($scope) {
    	
    	$scope.title = 'hola';
    }
})();