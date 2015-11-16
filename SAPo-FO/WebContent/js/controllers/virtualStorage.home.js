(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageHomeController', VirtualStorageHomeController);
    VirtualStorageHomeController.$inject = ['$scope', '$mdDialog'];
    /* @ngInject */
    function VirtualStorageHomeController($scope, $mdDialog) {
    	$scope.showAdvanced = showAdvanced;
    	
        function showAdvanced(ev) {
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
