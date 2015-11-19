(function() {
	'use strict';
	angular.module('sapo').controller('IndexController', IndexController);
	IndexController.$inject = [ '$scope' ];
	/* @ngInject */
	function IndexController($scope) {
		var vm = this;
		vm.theme = "test1";
		vm.sidenavAux = '';
		vm.toolbarTemplate = 'templates/index.toolbar.html';
		vm.navTopLeft = "templates/index.sidenav.menu.html";
		vm.navBottomLeft = "templates/index.sidenav.categories.html";
		vm.menuShow=false;
		vm.a=true;
		vm.b=false;
		$scope.$on("changeTheme", function(event, t) {
			vm.theme = t;
		});
		$scope.$on("menuOption",function(event,option){
			vm.ProductsNavigationMenu = (option=="PRODUCTS_NAVIGATION");
			vm.VirtualStorageNavigateMenu = (option=="VS_NAVIGATION");
			vm.menuShow=(!(option=="NULL"));
		})

	}
})(); 