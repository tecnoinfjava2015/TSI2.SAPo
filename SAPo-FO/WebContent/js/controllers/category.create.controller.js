(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CreateCategoryController', CreateCategoryController);
    CreateCategoryController.$inject = ['CreateCategoryResource',  '$scope', '$cookies', '$location', '$mdDialog'];
    /* @ngInject */
    function CreateCategoryController(CreateCategoryResource, $scope, $cookies, $location, $mdDialog) {
    	$scope.fields = []; 
    	$scope.insert = insert;
    	$scope.tenantId = 1;
    	var userId = $cookies.getObject("sapoUser");
    	
    	$scope.cancel = cancel;
    	
    	var res = $location.path().split("/");
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	var count = virtualStorages.owned.length;
    	var i = 0;
    	for (i = 0; i < count; i++) {
    		if (virtualStorages.owned[i].name == res[2]) {
    			$scope.virtualStorageName = virtualStorages.owned[i].name;
    			$scope.virtualStorageId = virtualStorages.owned[i].id;
    		}
    	}
    	
    	
    	function insert( data) {   
    		
    		data.virtualStorageName = $scope.virtualStorageName;
    		data.virtualStorageId = $scope.virtualStorageId;
    		CreateCategoryResource.save({tenantId: $scope.tenantId },data,function(){
    		});
    	}
    	

    	function cancel() {
    		$mdDialog.cancel();
		};
    	
    }
})();