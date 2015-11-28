(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ProductsNavigationController', ProductsNavigationController);
    ProductsNavigationController.$inject = ['ProductsResource', '$scope', '$cookies', '$location'];
    /* @ngInject */
    function ProductsNavigationController(ProductsResource, $scope, $cookies, $location) {
        var vm = this;
        $scope.title = 'ProductsNavigationController';
        $scope.offset = 0;
        $scope.limit = 2;
        $scope.loading = true;
        $scope.redirect = redirect;
        
        var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];
    	
        ProductsResource.query({
            tenantId: $scope.virtualStorageId,
            offset: $scope.offset,
            limit: $scope.limit
        }).$promise.then(function(result) {
            $scope.products = result;
            $scope.loading = false;
        });
        $scope.getProducts = getProducts;
        $scope.toggleListGrid = toggleListGrid;
        $scope.list = true;
        $scope.$emit('menuOption',"PRODUCTS_NAVIGATION"); 


        function getProducts() {
            $scope.loading = true;
            $scope.offset = $scope.offset + $scope.limit;
            ProductsResource.query({
                tenantId: $scope.virtualStorageId,
                offset: $scope.offset,
                limit: $scope.limit
            }).$promise.then(function(result) {
                var productsAux = $scope.products.concat(result);
                $scope.products = productsAux;
                $scope.loading = false;
            });
        }
        
        function toggleListGrid(){ 
        	$scope.list=!$scope.list;
        	
        }


        function redirect(route){
        	$location.path("/virtualStorage/" + $scope.virtualStorageName + "/Product/" + route);            
        }


    }
})();