(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', '$scope', '$location', '$mdDialog', '$rootScope'];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, $scope, $location, $mdDialog, $rootScope) {
    	var vm = this;
    	$scope.virtualStorages = {};
    	$scope.title = 'Almacenes virtuales';    
    	
    	$scope.showAdvanced = showAdvanced;
    	$scope.editVirtualStorage = editVirtualStorage;
    	
    	VirtualStorageViewResource.query({
            oId: 151
        }).$promise.then(function(result) {
        	console.log(result);
            $scope.virtualStorages = result.owned;
            $scope.virtualStoragesFollowing = result.following;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });
    	console.log($scope.virtualStorages);
    	
    	
    	function editVirtualStorage(ev, virtualStorage) {
    		$rootScope.tenantName = virtualStorage.name;
    		$rootScope.tenantId = virtualStorage.id;
    		$mdDialog.show({
    	    	controller: 'VirtualStorageEditController',
                templateUrl: 'templates/virtualstorage.edit.html',
    	    	
    	        parent: angular.element(document.body),
    	        targetEvent: ev,
    	        clickOutsideToClose:true
    	    })
    	    .then(function(answer) {
    	        $scope.status = 'You said the information was "' + answer + '".';
    	    }, function() {
    	        $scope.status = 'You cancelled the dialog.';
    	    });
    		//$location.url('http://localhost:8080/SAPo-FO/index.html#/virtualStorage/' + virtualStorageName + '/edit');
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
