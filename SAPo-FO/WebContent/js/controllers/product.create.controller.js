(function() {
	'use strict';
	angular.module('sapo').controller('CreateProductController',
			CreateProductController);
	CreateProductController.$inject = [ 'CreateProductsResource', 'UnitResource',
			'CategoriesResource', 'GenericProductResource', '$scope',
			'$mdDialog', '$location', '$cookies' ];
	/* @ngInject */
	function CreateProductController(CreateProductsResource, UnitResource,
			CategoriesResource, GenericProductResource, $scope, $mdDialog,
			$location, $cookies) {
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