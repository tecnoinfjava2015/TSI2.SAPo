(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('MenuController', MenuController);
    MenuController.$inject = ['$scope','$location', '$cookies'];
    /* @ngInject */
    function MenuController($scope,$location, $cookies) {
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	
        $scope.menu = [{
            icon:"home",
            name: "Home",
            url: "/"
        }];
        
        var item = {
                icon:"dashboard",
                name: "Dashboard",
                url: "/dashboard"
            }
        $scope.menu.push(item);
        
        if (typeof $scope.virtualStorageId !== 'undefined') {
        	var item = {
                    icon:"assessment",
                    name: "Reports",
                    url: "/reports"
                }
            $scope.menu.push(item);
        }
        
        $scope.$on('showReports',function(ev,t){
        	$scope.menu = [];
            $scope.menu = [{
                icon:"home",
                name: "Home",
                url: "/"
            },
            {
                icon:"dashboard",
                name: "Dashboard",
                url: "/dashboard"
            },
            {
                icon:"assessment",
                name: "Reports",
                url: "/reports"
            }];       	
        })
        

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
