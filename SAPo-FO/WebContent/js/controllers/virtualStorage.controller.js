(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageController',
			VirtualStorageController);
	VirtualStorageController.$inject = [ 'VirtualStorageResource', '$scope', '$cookies', '$mdDialog', '$window'];
	/* @ngInject */
	function VirtualStorageController(VirtualStorageResource, $scope, $cookies, $mdDialog, $window) {
		var user = $cookies.getObject("sapoUser");
		
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
					var auxUser = JSON.stringify(user);
					
					var aux2 = JSON.parse(auxUser);
					console.log(aux2);
					
					//$cookies.put("newUser", aux2);
					showAlert('Exito!', 'Se ha creado su almac&eacute;n virtual de forma exitosa');
					var landingUrl = "http://" + $window.location.host + "/SAPo-FO/#/virtualStorage/" + vsName;
					console.log(landingUrl);
					$window.location.href = landingUrl;
					
				}, function(r){
					console.log(r);
					showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
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