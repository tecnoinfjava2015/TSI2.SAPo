(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageHomeController', VirtualStorageHomeController);
    VirtualStorageHomeController.$inject = ['$scope', '$mdDialog'];
    /* @ngInject */
    function VirtualStorageHomeController($scope, $mdDialog) {
    	$scope.createCategory = createCategory;
    	$scope.editCategory = editCategory;
    	$scope.createProduct = createProduct;

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
    	
    	if (typeof $scope.virtualStorageId !== 'undefined') {
        	$rootScope.virtualStorageId = $scope.virtualStorageId;
    	}
    	else {
        	$rootScope.virtualStorageId = 1;
    	}
    	
    	function createProduct(ev) {
    		$mdDialog.show({
    	    	controller: 'CreateProductController',
                templateUrl: 'templates/product.create.html',
    	    	
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
    	
    	function editCategory(ev) {
      	    $mdDialog.show({
      	    	controller: 'EditCategoryController',
                  templateUrl: 'templates/category.edit.html',
      	    	
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
      	  
    	
        function createCategory(ev) {
    	    $mdDialog.show({
    	    	controller: 'CreateCategoryController',
                templateUrl: 'templates/category.create.html',
    	    	
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
