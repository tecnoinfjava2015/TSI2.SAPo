(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['$scope'];
    /* @ngInject */
    function VirtualStorageNavigateController($scope) {
    	$scope.title = 'Almacenes virtuales';    	
    }
})();