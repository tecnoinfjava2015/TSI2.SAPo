(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CategoriesSidenavController', CategoriesSidenavController);
    CategoriesSidenavController.$inject = ['$scope', '$location', 'CategoriesResource', 
                                           '$cookies', '$rootScope'];
    /* @ngInject */
    function CategoriesSidenavController($scope, $location, CategoriesResource, $cookies, $rootScope) {
        $scope.loading = true;
        $scope.linear = false;

        var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];
        
        CategoriesResource.query({
            tenantId: $scope.virtualStorageId,
            limit: 10,
            starred: true
        }).$promise.then(function(result) {
            $scope.categories = result;
            $scope.loading = false;
        });;

        $scope.redirect = redirect

        function redirect(route) {
            $location.path(route);
        }
    }
})();
