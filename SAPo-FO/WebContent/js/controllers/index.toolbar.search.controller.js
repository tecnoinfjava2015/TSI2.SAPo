(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('ToolbarSearchController', ToolbarSearchController);
    ToolbarSearchController.$inject = ['$scope', '$cookies', 'CategoriesResource', '$rootScope'];
    /* @ngInject */
    function ToolbarSearchController($scope, $cookies, CategoriesResource, $rootScope) {
    	$scope.loadCategories = loadCategories;
        var vm = this;
        vm.title = 'ToolbarSearchController';
        vm.selectedCategories = [];
        var vsId = $cookies.get("sapoCurrentVirtualStorage");
        loadCategories();
        vm.product = '';
        vm.toggleSearch = toggleSearch;
        vm.search = search;
        
        $scope.$on('ToggleSearch',function(event, searching){
			if(searching){
				loadCategories();
			}
		});
		
        function search(){
        	var searchParams = {};
        	var categories = [];

        	for (var i = 0; i < vm.selectedCategories.length; i++) {
				categories.push(vm.selectedCategories[i].localId);
				console.log(vm.selectedCategories[i].localId);
			} 
        	searchParams.categories = categories;
        	searchParams.search = vm.product;
      		$rootScope.$broadcast('searchProducts',searchParams);
        }
        function toggleSearch(){
        	vm.selectedCategories = [];
        	vm.product = '';
        	$rootScope.$broadcast('ToggleSearch',false);
        }
        function loadCategories(){
            CategoriesResource.query({
                tenantId: vsId,
                limit: 100,
            }).$promise.then(function(result) {
                vm.categories = result;
            }); 
            }
    }
})(); 
  