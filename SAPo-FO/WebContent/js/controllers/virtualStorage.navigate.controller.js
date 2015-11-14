(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', '$scope', '$location', '$mdDialog'];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, $scope, $location, $mdDialog) {
    	var vm = this;
    	$scope.virtualStorages = {};
    	$scope.title = 'Almacenes virtuales';    
    	
    	$scope.showAdvanced = showAdvanced;
    	$scope.editVirtualStorage = editVirtualStorage;
    	
    	VirtualStorageViewResource.query({
            oId: 151
        }).$promise.then(function(result) {
            $scope.virtualStorages = result;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });
    	console.log($scope.virtualStorages);
    	
    	
    	function editVirtualStorage(virtualStorageName) {
    		$location.url('http://localhost:8080/SAPo-FO/index.html#/virtualStorage/' + virtualStorageName + '/edit');
    	}
    	
    	
    	function showAdvanced(ev) {
    	    $mdDialog.show({
    	    	controller: 'VirtualStorageController',
                templateUrl: 'templates/virtualStorage.create.html',
    	    	
    	        parent: angular.element(document.body),
    	        targetEvent: ev,
    	        clickOutsideToClose:true
    	    })
    	    .then(function(answer) {
    	        $scope.status = 'You said the information was "' + answer + '".';
    	    }, function() {
    	        $scope.status = 'You cancelled the dialog.';
    	    });
    	  };
    }
})();
