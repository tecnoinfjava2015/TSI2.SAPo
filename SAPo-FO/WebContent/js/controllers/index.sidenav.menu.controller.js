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

        $scope.redirect = redirect;

        function redirect(route){
        	var res = $location.path().split("/");
        	var count = res.length;
        	var i = 0;
        	var vsName;
        	for (i = 0; i < count; i++) {
        		if (res[i] == 'virtualStorage') {
        			vsName = res[i + 1];
        		}
        	}
        	console.log('virtualStorage/' + vsName + route);
        	
        	if (typeof vsName !== 'undefined') {
        		$location.path('virtualStorage/' + vsName + route);
        	}
        	else {
        		$location.path(route);
        	}
            
        }
    }
})();
