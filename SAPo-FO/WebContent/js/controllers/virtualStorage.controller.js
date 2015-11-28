(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageController',
			VirtualStorageController);
	VirtualStorageController.$inject = [ 'VirtualStorageResource', 'UnitResource', '$scope', '$cookies', '$mdDialog', '$window'];
	/* @ngInject */
	function VirtualStorageController(VirtualStorageResource, UnitResource, $scope, $cookies, $mdDialog, $window) {
		var user = $cookies.getObject("sapoUser");
		var virtualStorages = $cookies.getObject("sapoVirtualStorages");
		
		$scope.vs = new VirtualStorageResource();
		$scope.master = {};
		$scope.logoFile;
		$scope.themes = [ 'theme test' ];
		$scope.sidenavTops = [ 'side nav test' ];
		$scope.sidenavBottoms = [ 'side nav bottom test' ];

		$scope.showAlert = showAlert;
		
		$scope.upload = upload;
		$scope.insert = insert;
		$scope.reset = reset;
		$scope.cancel = cancel;
		$scope.unit = new UnitResource();
		
		function upload() {
			document.getElementById("file").click();
		}

		function insert(vs) {			
			var vsName = vs.name;
			vs.enabled = true;
			
			if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
				vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
				vs.logo = vs.logo + $scope.logoFile.base64;
			}
			
			if (typeof vs.name !== 'undefined') {
				vs.$save(function(r) {
					var i = 0;
					var vsIdAux = '';
					while (typeof r[i] !== 'undefined') {
						vsIdAux += r[i];						
						i++;
					}
					user.tenantCreados.push(vsIdAux);
					user = JSON.stringify(user);
					
					virtualStorages.owned.push(vsIdAux);
					
					console.log(virtualStorages);
					$cookies.remove("sapoVirtualStorages");
					
					$cookies.put("sapoVirtualStorages", JSON.stringify(virtualStorages));
					$cookies.remove("sapoUser");
					$cookies.put("sapoUser", user);
					$scope.unit.virtualStorageId = parseInt(vsIdAux);
					UnitResource.save(
						$scope.unit,
						function(result) {
							$scope.loading = false;
							showAlert('Exito!', 'Se ha creado su almac&eacute;n virtual de forma exitosa');
							var landingUrl = "http://" + $window.location.host + "/SAPo-FO/index.html#/virtualStorage/" + vsName;
							console.log(landingUrl);
							$window.location.href = landingUrl;
						},
						function(result) {
							$scope.loading = false;
							console.log(r);
							showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
						});
				
					
					
				}, function(r){
					console.log(r);
					
					/*showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');*/
				});
				reset();
			}
			else {
				showAlert('Error', 'Debe ingresar el nombre del almac&eacute;n');
			}
		}

		

		function reset() {
			$scope.vs = angular.copy($scope.master);
			var fn = document.getElementById('fileName');
			fn.value = "";
		};
		

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