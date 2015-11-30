(function() {
	'use strict';
	angular.module('sapo').controller('CreateProductController',
			CreateProductController);
	CreateProductController.$inject = [ 'CreateProductsResource', 'ShoppingListProductsResource', 'UnitResource',
			'CategoriesResource', 'GenericProductResource', '$scope',
			'$mdDialog', '$location', '$cookies','$rootScope' ];
	/* @ngInject */
	function CreateProductController(CreateProductsResource, ShoppingListProductsResource, UnitResource,
			CategoriesResource, GenericProductResource, $scope, $mdDialog,
			$location, $cookies,$rootScope) {
		$scope.title = 'Crear Producto';
		$scope.fields = [];
		$scope.insert = insert;
		$scope.upload = upload;
		$scope.addAttribute = addAttribute;
		$scope.cancel = cancel;
		$scope.showAlert = showAlert;
		$scope.categoriesAux = [];
		$scope.selected = [];
		$scope.toggle = toggle;
		$scope.exists = exists;
		$scope.prod = {};
		$scope.unit = [];

		$scope.loadGenerics = loadGenerics;
		$scope.getGeneric = getGeneric;

		function loadGenerics(search) {
			return GenericProductResource.query({
				limit : 5,
				search : search
			}).$promise;
		}

		function getGeneric() {
			var barcode = null;
			if ($scope.generic !== null && $scope.generic !== 'undefined') {
				barcode = $scope.generic.barcode;
			}
			if (barcode !== '' && barcode !== null) {
				GenericProductResource.get({
					barcode : barcode
				}).$promise.then(function(result) {
					console.log(result);
					$scope.prod.barCode = result.barcode;
					$scope.prod.name = result.name;
					$scope.prod.description = result.purchasePrice;
				}, function() {
					console.log("No se encontró el producto");
				});
			}
		}

		/*var res = $location.path().split("/");
		var virtualStorages = $cookies.getObject("sapoVirtualStorages");
		var count = virtualStorages.owned.length;
		var i = 0;
		for (i = 0; i < count; i++) {
			if (virtualStorages.owned[i].name == res[2]) {
				$scope.virtualStorageName = virtualStorages.owned[i].name;
				$scope.tenantId = virtualStorages.owned[i].id;
			}
		}
*/
		$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	$scope.virtualStorageName = $cookies.get('sapoCurrentVirtualStorageName');

		CategoriesResource.query({
			tenantId : $scope.virtualStorageId
		}).$promise.then(function(result) {
			$scope.categories = result;
		});
		
		console.log($scope.virtualStorageId);
		UnitResource.query({
			tenantId : $scope.virtualStorageId
		}).$promise.then(function(result) {
			$scope.unit = result;
		});
		
		

		$scope.Spec = Spec;

		function upload() {
			document.getElementById("file").click();
		}

		function insert(data) {
			data.name = $('#genericName').val();
			if (data != null && typeof data.name !== 'undefined'
					&& typeof data.barCode !== 'undefined') {
				
							
				ShoppingListProductsResource.get({
					tenantId: $scope.virtualStorageId,
		            barcode: data.barCode
		        }).$promise.then(function(result) {
		        	$scope.auxProds = result;
		            console.log(result);
					if (typeof $scope.auxProds == 'undefined' || typeof $scope.auxProds.name === 'undefined') {
						var i = 0;
						data.categories = [];
						for (i = 0; i < $scope.selected.length; i++) {
							var category = new Category($scope.selected[i].name,
									$scope.selected[i].virtualStorageId,
									$scope.selected[i].virtualStorageName);
							console.log($scope.category);
							data.categories.push(category);
							/*
							 * var cat = []; cat.name = $scope.selected[i].name;
							 * cat.virtualStorageId =
							 * $scope.selected[i].virtualStorageId;
							 * cat.virtualStorageName =
							 * $scope.selected[i].virtualStorageName; cat.starred =
							 * $scope.selected[i].starred; data.categories.push(cat);
							 */
						}
	
						/* data.categories = $scope.selected; */
						$scope.loading = true;
						var images = $scope.images;
						if (images != null && typeof images !== 'undefined') {
							var count = images.length;
							var mongoImages = [];
							var i = 0;
	
							// data.attributes = JSON.stringify($scope.fields);
	
							for (i = 0; i < count; i++) {
	
								var image = "data:" + images[i].filetype + ";base64,"
										+ images[i].base64;
								mongoImages.push(image);
	
							}
	
							data.images = mongoImages;
						}
						else {
							data.images = [];
							data.images.push('data:image/gif;base64,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');
						}
	
						console.log($scope.fields);
						data.specs = [];
						
						console.log(data.name);
						data.virtualStorageId = $scope.virtualStorageId;
						data.virtualStorageName = $scope.virtualStorageName;
						for (i = 0; i < $scope.fields.length; i++) {
							$scope.spec = new Spec($scope.fields[i].name,
									$scope.fields[i].value, $scope.fields[i].type);
							data.specs.push($scope.spec);
							console.log(data.specs);
							// data.attributes += JSON.stringify($scope.fields[i]);
						}
						
						data.unit = $scope.unit;
	
						CreateProductsResource
								.save(
										{
											tenantId : $scope.virtualStorageId
										},
										data,
										function() {
											$scope.loading = false;
											$rootScope.$broadcast('productAdded',$scope.loading);
											showAlert('Exito!',
													'Se ha creado su producto de forma exitosa');
	
										},
										function(r) {
											$scope.loading = false;
											console.log(r);
											showAlert('Error!',
													'Ocurri&oacute; un error al procesar su petici&oacute;n');
										});
	
					} else {
						showAlert('Error!',
								'Ya existe un producto con este c&oacute;digo de barras.');
					}
		        });
			} else {
				showAlert('Error!',
						'Debe ingresar nombre y c&oacute;digo de barras.');
			}
				
		}

		function toggle(item, list) {
			var idx = list.indexOf(item);
			if (idx > -1)
				list.splice(idx, 1);
			else
				list.push(item);
		}
		;

		function exists(item, list) {
			return list.indexOf(item) > -1;
		}
		;

		function addAttribute() {
			$scope.fields.push({});
		}

		function Spec(name, value) {
			this.name = name;
			this.value = value;
		}

		function Category(name, virtualStorageId, virtualStorageName) {
			this.name = name;
			this.virtualStorageId = virtualStorageId;
			this.virtualStorageName = virtualStorageName;
		}

		function showAlert(title, content) {
			// Appending dialog to document.body to cover sidenav in docs app
			// Modal dialogs should fully cover application
			// to prevent interaction outside of dialog
			$mdDialog.show($mdDialog.alert().parent(
					angular.element(document.querySelector('#popupContainer')))
					.clickOutsideToClose(true).title(title).content(content)
					.ariaLabel('Alert Dialog Demo').ok('Cerrar'));
		}
		;

		function cancel() {
			$mdDialog.cancel();
		}
	}
})();