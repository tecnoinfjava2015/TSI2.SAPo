(function() {
	'use strict';
	angular.module('sapo')
	.controller('ShoppingListController', ShoppingListController);
	
	ShoppingListController.$inject = ['$scope', 'ShoppingListRecommendationResouce', 'ProductMovementResource', 'ShoppingListEditResource', 'ShoppingListDeleteResource', 'ShoppingListProductsResource', 'ShoppingListResource', 'ShoppingListInsertResource', '$cookies', '$mdDialog', '$timeout' ];
	/* @ngInject */
	function ShoppingListController($scope, ShoppingListRecommendationResouce, ProductMovementResource, ShoppingListEditResource, ShoppingListDeleteResource, ShoppingListProductsResource, ShoppingListResource, ShoppingListInsertResource, $cookies, $mdDialog, $timeout) {
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
		$scope.updateItem = updateItem;
		$scope.confirmBuyItem = confirmBuyItem;
		$scope.addNewItem = addNewItem;
		$scope.addRecommendedToShoppingList = addRecommendedToShoppingList;
		$scope.hideRecommendations = true;
		$scope.deleteRecommendedItem = deleteRecommendedItem;
		
		ShoppingListResource.get({
			VSId: $scope.virtualStorageId
    	}).$promise.then(function(result) {
            $scope.shoppingList = result;
        });

		ShoppingListRecommendationResouce.query({
			VSId: $scope.virtualStorageId
//			VSId: 1
		}).$promise.then(function(result) {
			$scope.recommendationList = result;
			if(result !== null && (typeof result !== 'undefined') && result.length > 0) {
				$scope.hideRecommendations = false;
			}
		});
		
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
		
        function addRecommendedToShoppingList(item) {
      		ShoppingListInsertResource.save({virtualStorageId: $scope.virtualStorageId, productBarcode: item.barCode, productname: item.name, quantity: item.stock })
			.$promise.then(function(result2) {
				var itemAlreadyInList = false;
				var count = $scope.shoppingList.length;
				for(var i = 0; i < count; i++){
					if($scope.shoppingList[i].productBarcode == result2.productBarcode) {
						itemAlreadyInList = true;
					}
				}
				if(itemAlreadyInList) {
					showAlert('Aviso!','El item ya se encuentra en la lista');
				} else {
					showAlert('Exito!','Se ha agregado el item a la lista');
					$scope.shoppingList.push(item);
				}
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
		
		function edititem(item) { //llama al md-dialog para solicitar datos para el update
			console.log(item);
			$scope.itemtoedit_productname = item.productname;
			$scope.itemtoedit_barcode = item.productBarcode;
			$scope.itemtoedit_quantity = item.quantity;
			//console.log($scope.itemtoedit_barcode);
			$mdDialog.show({
      	    	controller: 'ShoppingListController',
                templateUrl: 'templates/shoppingList.item.update.html',
                parent: angular.element(document.body),
      	        targetEvent: item,
      	        scope: this,
      	        clickOutsideToClose:true
      	    })
      	    .then(function(answer) {
      	    	//TODO: update de la lista de compras?
      	        $scope.status = 'You said the information was "' + answer + '".';
      	    }, function() {
      	        $scope.status = 'You cancelled the dialog.';
      	    });
			console.log("edititem");
		}
		
		function updateItem(itemtoedit_quantity) { //Inserta en la DB
			ShoppingListEditResource.update({virtualStorageId: $scope.virtualStorageId, productBarcode: $scope.$parent.itemtoedit_barcode, productname: $scope.$parent.itemtoedit_productname, quantity: itemtoedit_quantity })
			.$promise.then(function(result) {
				$scope.newItem = result;
				console.log($scope.newItem);
				showAlert('Exito!','Se ha actualizado el item');
				$scope.item.quantity = $scope.newItem.quantity;
				//$scope.shoppingList.push($scope.newItem);
				//$scope.$apply();
	        });
      	}
		
		function deleteitem(item) {
			ShoppingListDeleteResource.delete({ VSId: $scope.virtualStorageId, barcode: item.productBarcode })
			.$promise;
			
			removeItem(item);
			showAlert('Exito!','Se ha eliminado el item a la lista');
			
		}
		
		function deleteBoughtItem(item) {
			ShoppingListDeleteResource.delete({ VSId: $scope.virtualStorageId, barcode: item.productBarcode })
			.$promise;
			removeItem(item);
		}
		
		function deleteRecommendedItem(item) {
			var index = $scope.recommendationList.indexOf(item);
			$scope.recommendationList.splice(index, 1); 
			if($scope.recommendationList.length < 1) {
				$scope.hideRecommendations = true;
			}
		}
		
		function removeItem(item) { //Remueve de la lista de la UI
			var index = $scope.shoppingList.indexOf(item);
			$scope.shoppingList.splice(index, 1);     
		}
		
		function addNewItem(item) { //Agrega en la lista de la UI
	        $scope.shoppingList.push(item);
			window.location.reload();
		}
		
		function confirmBuyItem(finalPrice) {
			$scope.movement.finalPrice = finalPrice;
			ProductMovementResource
            .save({},
                $scope.movement,
                function() {
                    showAlert('Exito!', 'Se ha creado el movimiento de forma exitosa');
                    //quitarlo de la lista
                    //removeItem($scope.$parent.boughtItem);
                },
                function(r) {
                    console.log(r);
                    showAlert('Error!', 'Ocurri&oacute; un error al procesar su petici&oacute;n');
                });
		}
		
		function buyitem(item) {
			$scope.boughtItem = item;
			$scope.userID = $cookies.getObject("sapoUser").id;

			$scope.movement = {};
			$scope.movement.virtualStorageId = $scope.virtualStorageId;
			$scope.movement.stock = item.quantity;
			$scope.movement.barCode = item.productBarcode;
			$scope.movement.userID = $scope.userID;
			$scope.movement.initialPrice = 0;
			$scope.movement.finalPrice = $scope.finalPrice;
			$scope.movement.toAV = false;
			$scope.movement.origin = 0;
			$scope.movement.destination = $scope.virtualStorageId;

			//abrir md-message para completar precio de compra
			$mdDialog.show({
      	    	controller: 'ShoppingListController',
                templateUrl: 'templates/shoppingList.item.buy.html',
                parent: angular.element(document.body),
      	        targetEvent: item,
      	        scope: this,
      	        clickOutsideToClose:true
      	    })
      	    .then(function(answer) {
      	    	//TODO: update de la lista de compras?
      	        //$scope.status = 'You said the information was "' + answer + '".';
      	    	deleteBoughtItem($scope.boughtItem);
      	    	deleteRecommendedItem($scope.boughtItem);
      	    }, function() {
      	        $scope.status = 'You cancelled the dialog.';
      	    });
			//llamar a agregar movement con los datos actuales del item
			
			
				            
			//remover de la lista
			console.log("buyitem");
		}

      	function insertItem(data) { //Inserta en la DB
      		ShoppingListInsertResource.save({virtualStorageId: $scope.virtualStorageId, productBarcode: $scope.product.barCode, productname: $scope.productname, quantity: $scope.quantity })
			.$promise.then(function(result) {
				$scope.$parent.newItem = result;
				showAlert('Exito!','Se ha agregado el item a la lista');
				//return $scope.newItem;
				//$scope.shoppingList.push($scope.newItem);
				//$scope.$apply();
				console.log("new item:");
				console.log(result);
				addNewItem(result);
	        });
      	}

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