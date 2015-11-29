(function() {
	'use strict';
	angular.module('sapo')
	.controller('ShoppingListController', ShoppingListController);
	
	ShoppingListController.$inject = ['$scope', 'ShoppingListResource', 'ShoppingListInsertResource', '$cookies', '$mdDialog'];
	/* @ngInject */
	function ShoppingListController($scope, ShoppingListResource, ShoppingListInsertResource, $cookies, $mdDialog) {
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		$scope.additem = additem;
		$scope.edititem = edititem;
		$scope.deleteitem = deleteitem;
		$scope.buyitem = buyitem;
		$scope.cancel = cancel;
		$scope.insertItem = insertItem;
		$scope.showAlert = showAlert;
		
		ShoppingListResource.get({
			VSId: $scope.virtualStorageId
    	}).$promise.then(function(result) {
            $scope.shoppingList = result;
        });
		
		function additem(ev) {
			$mdDialog.show({
      	    	controller: 'ShoppingListController',
                templateUrl: 'templates/shoppingList.item.create.html',
      	        parent: angular.element(document.body),
      	        targetEvent: ev,
      	        clickOutsideToClose:true
      	    })
      	    .then(function(answer) {
      	    	//TODO: update de la lista de compras?
      	        $scope.status = 'You said the information was "' + answer + '".';
      	    }, function() {
      	        $scope.status = 'You cancelled the dialog.';
      	    });
		}
		
		function edititem(data) {
			console.log("edititem");
		}
		
		function deleteitem(data) {
			console.log("deleteitem");
		}
		
		function buyitem(data) {
			console.log("buyitem");
		}

      	function insertItem(data) {
      		ShoppingListInsertResource.save({virtualStorageId: $scope.virtualStorageId, productBarcode: $scope.productBarcode, productname: $scope.productname, quantity: $scope.quantity })
			.$promise.then(function(result) {
				console.log("en el then");
				$scope.newItem = result;
	            console.log($scope.newItem);
				showAlert('Exito!','Se ha agregado el item a la lista');
				$scope.shoppingList.push($scope.newItem);
				$scope.$apply();
	            console.log($scope.shoppingList);
	        });
      	}
//		$scope.getMovements = function(){
//			var fromDate = moment(this.from_Date).format('YYYY-MM-DD');
//			var toDate = moment(this.to_Date).format('YYYY-MM-DD');
//			ReportsResourceMovList.query({tenantId: $scope.virtualStorageId}, { fromDate: fromDate, toDate: toDate })
//			.$promise.then(function(result) {
//	            $scope.movements = result;
//	            $scope.hideMovements = false;
//	            
//	        });
//		}
      	function showAlert(title,content) {
			// Appending dialog to document.body to cover sidenav in docs app
			// Modal dialogs should fully cover application
			// to prevent interaction outside of dialog
			$mdDialog
					.show($mdDialog
							.alert()
							.parent(
									angular.element(document
											.querySelector('#popupContainer')))
							.clickOutsideToClose(true)
							.title(title)
							.content(content)
							.ariaLabel('Alert Dialog Demo').ok('Cerrar'));
		};
		
    	function cancel() {
    		$mdDialog.cancel();
		};	

	}
})();