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
        
        $scope.redirect = redirect;
        
        var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];
    	loadProducts();
       
        $scope.getProducts = getProducts;
        $scope.toggleListGrid = toggleListGrid;
        $scope.list = true;
//        $scope.$emit('menuOption',"PRODUCTS_NAVIGATION"); 
        $scope.$on('ToggleSearch',function(event, searching){
			if(!searching){
				loadProducts();
			}
		});
        
        $scope.$on('productAdded',function(ev,searchParams){
        	loadProducts();
        });
        $scope.$on('searchProducts',function(ev,searchParams){
        	console.log(searchParams);
        	search(searchParams);
        });

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

        function loadProducts(){
        	$scope.products = [];
    	$scope.loading = true;
    	 ProductsResource.query({
             tenantId: $scope.virtualStorageId,
             offset: $scope.offset,
             limit: $scope.limit
         }).$promise.then(function(result) {
             $scope.products = result;
             $scope.loading = false;
         });
        }
        function redirect(route){
        	$location.path("/virtualStorage/" + $scope.virtualStorageName + "/Product/" + route);            
        }
        
        function search(params){
        	$scope.products = [];
        	$scope.loading = true;
        	ProductsResource.query({
                tenantId: $scope.virtualStorageId,
                offset: 0,
                limit: 20,
                searching:true,
                category: params.categories,
                search: params.search
            }).$promise.then(function(result) {
                $scope.products = result;
                $scope.loading = false;
            });        	
        }

    }
})();