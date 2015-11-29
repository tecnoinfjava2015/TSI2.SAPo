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
        	VirtualStorageEditResource.get({
                id: vsId
            }).$promise.then(function(result){
            	$scope.vs = result;
            	console.log($scope.vs);
            	if (typeof $scope.vs!=='undefined' && $scope.vs!==null && $scope.vs!=='' ){
            		if (typeof $scope.vs.logo==='undefined' || $scope.vs.logo===null || $scope.vs.logo===''){
            			$scope.vs.logo = "images/littleFrog.gif";
            		}
            	} 
            },function(){
            	console.log("ERROR: Ocurrio un error al obtener el almacén virtual.")
            });
        	
		}
        $scope.toggleSidenavLeft = toggleSidenavLeft;
        function toggleSidenavLeft(){
		$mdSidenav('left').toggle();
        }
    }
})();