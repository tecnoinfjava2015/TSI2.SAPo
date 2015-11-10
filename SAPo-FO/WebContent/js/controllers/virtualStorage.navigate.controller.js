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
            var vsAux = $scope.virtualStorages.concat(result);
            $scope.virtualStorages = vsAux;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });

    	$scope.vsService = new VirtualStorageViewResource();
    	
    	
    	$scope.getVirtualStorages = getVirtualStorages;
    	
    	function getVirtualStorages() {
    		$scope.loading = true;
        	
    		VirtualStorageViewResource.query({
                oId: 1
            }).$promise.then(function(result) {
            	console.log(result);
                var vsAux = $scope.virtualStorages.concat(result);
                $scope.virtualStorages = vsAux;
                $scope.loading = false;
            }, function(error) {
            	console.log(error);
                
            });
        }	
    }
})();
