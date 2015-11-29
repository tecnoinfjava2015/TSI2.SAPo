(function() {
	'use strict';
	angular.module('sapo')
	.controller('ShoppingListController', ShoppingListController);
	
	ShoppingListController.$inject = ['$scope', 'ShoppingListDeleteResource', 'ShoppingListProductsResource', 'ShoppingListResource', 'ShoppingListInsertResource', '$cookies', '$mdDialog'];
	/* @ngInject */
	function ShoppingListController($scope, ShoppingListDeleteResource, ShoppingListProductsResource, ShoppingListResource, ShoppingListInsertResource, $cookies, $mdDialog) {
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
		$scope.additem = additem;
		$scope.edititem = edititem;
		$scope.deleteitem = deleteitem;
		$scope.buyitem = buyitem;
		$scope.cancel = cancel;
		$scope.insertItem = insertItem;
		$scope.showAlert = showAlert;
		$scope.getProduct = getProduct;
		$scope.loadProducts = loadProducts;
		$scope.removeItem = removeItem;
		
		ShoppingListResource.get({
			VSId: $scope.virtualStorageId
    	}).$promise.then(function(result) {
            $scope.shoppingList = result;
        });
		
//		ShoppingListProductsResource.query({
//			tenantId: $scope.virtualStorageId
//		}).$promise.then(function(result) {
//			$scope.products = result;
//		});
//		console.log("Productos: ");
//		console.log($scope.products);
		
		function loadProducts(search) {
            return ShoppingListProductsResource.query({
                tenantId: $scope.virtualStorageId, 
                limit: 5,
                minSearch: true,
                search: search
            }).$promise;
        }
		
        function getProduct() {
            $scope.barCode = '';
            $scope.productname = '';
            $scope.quantity = '';
            ShoppingListProductsResource.get({
                tenantId: $scope.virtualStorageId,
                barcode: $scope.product.barCode
            }).$promise.then(function(result) {
                $scope.barCode = result.barCode;
                $scope.productname = result.name;
            });
        }
		
		function additem(ev) { //llama al modal que permite agregar nuevo item
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
		
		function edititem(item) {
			console.log("edititem");
		}
		
		function deleteitem(item) {
			ShoppingListDeleteResource.delete({ VSId: $scope.virtualStorageId, barcode: item.productBarcode })
			.$promise;
			
			removeItem(item);
			showAlert('Exito!','Se ha eliminado el item a la lista');
			
		}
		
		function removeItem(item) { //Remueve de la lista de la UI
			var index = $scope.shoppingList.indexOf(item);
			$scope.shoppingList.splice(index, 1);     
		}
		
		function buyitem(data) {
			console.log("buyitem");
		}

      	function insertItem(data) { //Inserta en la DB
      		ShoppingListInsertResource.save({virtualStorageId: $scope.virtualStorageId, productBarcode: $scope.product.barCode, productname: $scope.productname, quantity: $scope.quantity })
			.$promise.then(function(result) {
				$scope.newItem = result;
				showAlert('Exito!','Se ha agregado el item a la lista');
				$scope.shoppingList.push($scope.newItem);
				$scope.$apply();
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