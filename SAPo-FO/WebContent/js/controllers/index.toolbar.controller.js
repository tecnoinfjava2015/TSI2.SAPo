(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarController', ToolbarController);
    ToolbarController.$inject = ['$mdSidenav','$scope','$location','VirtualStorageEditResource','$cookies'];
    /* @ngInject */
    function ToolbarController($mdSidenav,$scope,$location,VirtualStorageEditResource,$cookies) {
        $scope.title = 'ToolbarController';
//		var res = $location.path().split("/");
    	//$scope.virtualStorageName = res[2];
    	var vsId=$cookies.get("sapoCurrentVirtualStorage");
    	if (typeof vsId!=='undefined' && vsId!==null && vsId!=='' ){
        	$scope.vs = VirtualStorageEditResource.get({
                id: vsId
            });
        	if (typeof $scope.vs!=='undefined' && $scope.vs!==null && $scope.vs!=='' ){
        		if (typeof $scope.vs.logo==='undefined' || $scope.vs.logo===null || $scope.vs.logo===''){
        			$scope.vs.logo = "images/littleFrog.gif"
        		}
        	} 
		}
    	


    	
    	
    	
        $scope.toggleSidenavLeft = toggleSidenavLeft;
        function toggleSidenavLeft(){
		$mdSidenav('left').toggle();
        }
    }
})();