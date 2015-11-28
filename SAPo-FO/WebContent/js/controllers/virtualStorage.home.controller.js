(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageHomeController', VirtualStorageHomeController);
    VirtualStorageHomeController.$inject = ['$scope', '$mdDialog', '$location', '$cookies', '$rootScope'];
    /* @ngInject */
    function VirtualStorageHomeController($scope, $mdDialog, $location, $cookies, $rootScope ) {
    	$scope.createCategory = createCategory;
    	$scope.editCategory = editCategory;
    	$scope.createProduct = createProduct;
    	$scope.shareVS = shareVS;
    	$cookies.remove("sapoCurrentVirtualStorage");
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
    	
    	$cookies.put("sapoCurrentVirtualStorage", $scope.virtualStorageId);
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
    	  
    	function shareVS(ev) {
      	    $mdDialog.show({
      	    	controller: 'VirtualStorageShareController',
                templateUrl: 'templates/virtualStorage.share.html',
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
