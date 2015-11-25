(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('MenuController', MenuController);
    MenuController.$inject = ['$scope','$location'];
    /* @ngInject */
    function MenuController($scope,$location) {
        $scope.menu = [{
            icon:"home",
            name: "Home",
            url: "/"
        }];
        var item = {
            icon:"airplay",
            name: "Test",
            url: "/test"
        }
        $scope.menu.push(item);
        
        var item = {
                icon:"dashboard",
                name: "Dashboard",
                url: "/dashboard"
            }
        $scope.menu.push(item);

        $scope.redirect = redirect

        function redirect(route){
        	console.log($location.path() + route);
        	
            $location.path(route);
        }
    }
})();
