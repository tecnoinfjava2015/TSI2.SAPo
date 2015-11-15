/*(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CategoriesNavigateController', CategoriesController);
    CategoriesNavigateController.$inject = ['$scope', '$location', 'CategoriesResource'];
    /* @ngInject */
    /*function CategoriesNavigateController($scope, $location, CategoriesResource) {
    	$scope.title = 'test';
        $scope.loading = true;
        $scope.linear = false;
        CategoriesResource.query({
            tenantId: 1,
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
*/