(function() {
	'use strict';
	angular.module('sapo').controller('IndexController', IndexController);
	IndexController.$inject = [ '$scope', '$cookies'];
	/* @ngInject */
	function IndexController($scope,$cookies) {
		var vm = this;
		
		var cookieStyle = $cookies.getObject('vsStyle');
		console.log(cookieStyle);
		if (typeof cookieStyle!=='undefined' && cookieStyle!==null && cookieStyle!=='' ){
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/"+cookieStyle.sidenavTop+".png');background-size: cover;";
			vm.theme = cookieStyle.theme;
		}else{
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/Barras1.png');background-size: cover;";
			vm.theme = "indigopink";
		}
		
		
		
		vm.sidenavAux = '';
		vm.toolbarTemplate = 'templates/index.toolbar.html';
		vm.navTopLeft = "templates/index.sidenav.menu.html";
		vm.navBottomLeft = "templates/index.sidenav.categories.html";
		vm.menuShow=false;
		vm.a=true;
		vm.b=false;
		$scope.$on("changeTheme", function(event, t) {
			vm.theme = t.theme; 
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/"+t.sidenavTop+".png');background-size: cover;";
			$cookies.remove('vsStyle');
			$cookies.putObject('vsStyle',t);
			
		});
		$scope.$on("menuOption",function(event,option){
			vm.ProductsNavigationMenu = (option=="PRODUCTS_NAVIGATION");
			vm.VirtualStorageNavigateMenu = (option=="VS_NAVIGATION");
			vm.menuShow=(!(option=="NULL"));
			
		})

	}
})(); 