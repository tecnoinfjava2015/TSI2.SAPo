(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarController', ToolbarController);
    ToolbarController.$inject = ['$mdSidenav','$scope'];
    /* @ngInject */
    function ToolbarController($mdSidenav,$scope) {
        $scope.title = 'ToolbarController';
        $scope.virtualStorageName = "SAPO Prueba" ;
        $scope.toggleSidenavLeft = toggleSidenavLeft;
        function toggleSidenavLeft(){
		$mdSidenav('left').toggle();
        }
    }
})();