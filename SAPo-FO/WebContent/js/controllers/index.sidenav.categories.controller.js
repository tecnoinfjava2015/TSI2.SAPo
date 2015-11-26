(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CategoriesSidenavController', CategoriesSidenavController);
    CategoriesSidenavController.$inject = ['$scope', '$location', 'CategoriesResource', '$rootScope'];
    /* @ngInject */
    function CategoriesSidenavController($scope, $location, CategoriesResource, $rootScope) {
        $scope.loading = true;
        $scope.linear = false;
        var vsId = 1;
        if (typeof $rootScope.virtualStorageId !== 'undefined') {
        	vsId = $rootScope.virtualStorageId;
        }
        CategoriesResource.query({
            tenantId: vsId,
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
