(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', '$scope'];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, $scope) {
    	var vm = this;
    	$scope.virtualStorages = {};
    	$scope.title = 'Almacenes virtuales';    
    	
    	VirtualStorageViewResource.query({
            oId: 1
        }).$promise.then(function(result) {
            $scope.virtualStorages = result;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });
    	console.log($scope.virtualStorages);
    	
    }
})();
