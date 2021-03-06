(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageHomeController',
			VirtualStorageHomeController);
	VirtualStorageHomeController.$inject = [ '$scope', '$mdDialog',
			'$location', '$cookies', '$rootScope', "$window" ];
	/* @ngInject */
	function VirtualStorageHomeController($scope, $mdDialog, $location,
			$cookies, $rootScope, $window) {
		$scope.createCategory = createCategory;
		$scope.editCategory = editCategory;
		$scope.createProduct = createProduct;
		$scope.redirectShoppingList = redirectShoppingList;

		$scope.shareVS = shareVS;
		var sharedVS = isShared();
		if (sharedVS) {
			$rootScope.$broadcast("menuOption", "VS_HOME")
		} else {
			$rootScope.$broadcast("menuOption", "VS_HOME_CAT")
		}

		function createProduct(ev) {
			$mdDialog.show({
				controller : 'CreateProductController',
				templateUrl : 'templates/product.create.html',

				parent : angular.element(document.body),
				targetEvent : ev,
				clickOutsideToClose : true
			}).then(
					function(answer) {
						$scope.status = 'You said the information was "'
								+ answer + '".';
					}, function() {
						$scope.status = 'You cancelled the dialog.';
					});
		}
		;

		function editCategory(ev) {
			$mdDialog.show({
				controller : 'EditCategoryController',
				templateUrl : 'templates/category.edit.html',

				parent : angular.element(document.body),
				targetEvent : ev,
				clickOutsideToClose : true
			}).then(
					function(answer) {
						$scope.status = 'You said the information was "'
								+ answer + '".';
					}, function() {
						$scope.status = 'You cancelled the dialog.';
					});
		}
		;

		function createCategory(ev) {
			$mdDialog.show({
				controller : 'CreateCategoryController',
				templateUrl : 'templates/category.create.html',

				parent : angular.element(document.body),
				targetEvent : ev,
				clickOutsideToClose : true
			}).then(
					function(answer) {
						$scope.status = 'You said the information was "'
								+ answer + '".';
					}, function() {
						$scope.status = 'You cancelled the dialog.';
					});
		}
		;

		function shareVS(ev) {
			$mdDialog.show({
				controller : 'VirtualStorageShareController',
				templateUrl : 'templates/virtualStorage.share.html',
				parent : angular.element(document.body),
				targetEvent : ev,
				clickOutsideToClose : true
			}).then(
					function(answer) {
						$scope.status = 'You said the information was "'
								+ answer + '".';
					}, function() {
						$scope.status = 'You cancelled the dialog.';
					});
		}
		;

		function redirectShoppingList() {
			$window.location.href = "#/virtualStorage/"
					+ $cookies.get("sapoCurrentVirtualStorageName")
					+ "/shoppingList";
		}

		function isShared() {
			var currentVsId = $cookies.get("sapoCurrentVirtualStorage");
			var result = false;
			var virtualStorages = $cookies.getObject("sapoVirtualStorages");
			var followingLenght = virtualStorages.following.length;
			var i = 0;
			if (followingLenght > 0) {
				for (i = 0; i < followingLenght; i++) {
					if (currentVsId == virtualStorages.following[i].id) {
						result = true;
					}
				}
			}
			return result;
		}
	}
})();
