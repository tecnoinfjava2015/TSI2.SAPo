(function() {
	'use strict';
	angular.module('sapo').controller('IndexController', IndexController);
	IndexController.$inject = [ '$scope', '$cookies'];
	/* @ngInject */
	function IndexController($scope,$cookies) {
		var vm = this;

		var cookieStyle = $cookies.getObject('vsStyle');
		if (typeof cookieStyle!=='undefined' && cookieStyle!==null && cookieStyle!=='' ){
			if(typeof cookieStyle.vsId!=='undefined' && cookieStyle.vsId!==null && cookieStyle.vsId!==''){
				vm.vsSelected = true;
			}else{
				vm.vsSelected = false;
			}
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/"+cookieStyle.sidenavTop+".png');background-size: cover;";
			vm.theme = cookieStyle.theme;
		}else{
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/Servilletas4.png');background-size: cover;";
			vm.theme = "indigopink";
		}
    	var userTwitter = $cookies.getObject('sapoUser');
    	vm.userLogged = false;
		if ((typeof userTwitter !== "undefined") && (userTwitter !== null)) {
			vm.userLogged = (userTwitter.twitterId!==0);
		}  
		
		vm.sidenavAux = '';
		vm.toolbarTemplate = 'templates/index.toolbar.html';
		vm.toolbarSearch = 'templates/index.toolbar.search.html';
		vm.navTopLeft = "templates/index.sidenav.menu.html";
		vm.navBottomLeft = "templates/index.sidenav.categories.html";
		vm.menuShow=false;
		vm.searching = false;
		vm.a=true;
		vm.b=false;
		
		$scope.$on('ToggleSearch',function(event, searching){
			vm.searching = searching;
		})
		$scope.$on("changeTheme", function(event, t) {
			if(typeof t.vsId!=='undefined' && t.vsId!==null && t.vsId!==''){
				vm.vsSelected = true;
			}else{
				vm.vsSelected = false;
			}
			
			vm.theme = t.theme; 
			vm.sidenavImage = "background-image: url('images/mdBackgrounds/"+t.sidenavTop+".png');background-size: cover;";
			$cookies.remove('vsStyle');
			$cookies.putObject('vsStyle',t);
		});
		$scope.$on("menuOption",function(event,option){
			console.log(option);
			vm.ProductsNavigationMenu = (option=="PRODUCTS_NAVIGATION"||option=="VS_HOME" || option=="VS_HOME_CAT");
			vm.VirtualStorageNavigateMenu = (option=="VS_NAVIGATION");
			vm.VirtualStorageHome	= (option=="VS_HOME" || option=="VS_HOME_CAT");
			vm.VirtualStorageHomeCat = (option=="VS_HOME_CAT");
			vm.Notifications = (option=="NOTIFICATION_NAVIGATION");
			vm.ShoppingList = (option=="SHOPPING_LIST");
			vm.menuShow=(!(option=="NULL")); 
			vm.searching = false;
			
		})
 
	}
})(); 