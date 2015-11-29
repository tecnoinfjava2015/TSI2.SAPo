(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarController', ToolbarController);
    ToolbarController.$inject = ['$mdSidenav','$scope','$location'];
    /* @ngInject */
    function ToolbarController($mdSidenav,$scope,$location) {
        $scope.title = 'ToolbarController';
		var res = $location.path().split("/");
    	$scope.virtualStorageName = res[2];
        $scope.toggleSidenavLeft = toggleSidenavLeft;
        function toggleSidenavLeft(){
		$mdSidenav('left').toggle();
        }
    }
})();