(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', '$scope', '$location'];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, $scope, $location) {
    	var vm = this;
    	$scope.virtualStorages = {};
    	$scope.title = 'Almacenes virtuales';    
    	
    	VirtualStorageViewResource.query({
            oId: 151
        }).$promise.then(function(result) {
            $scope.virtualStorages = result;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });
    	console.log($scope.virtualStorages);
    	
    	
    	$scope.editVirtualStorage = editVirtualStorage;
    	function editVirtualStorage(virtualStorageName) {
    		$location.url('http://localhost:8080/SAPo-FO/index.html#/virtualStorage/' + virtualStorageName + '/edit');
    	}
    }
})();
