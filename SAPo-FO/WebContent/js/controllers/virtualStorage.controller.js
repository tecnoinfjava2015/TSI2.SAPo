(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageController',
			VirtualStorageController);
	VirtualStorageController.$inject = [ 'VirtualStorageResource', '$scope',
			'$mdDialog' ];
	/* @ngInject */
	function VirtualStorageController(VirtualStorageResource, $scope, $mdDialog) {
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

		function insert(data) {			
			$scope.vs.enabled = true;
			
			if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
				$scope.vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
				$scope.vs.logo = $scope.vs.logo + $scope.logoFile.base64;
			}
			


			$scope.vs.$save(function(r) {
				showAlert('Exito!','Se ha creado su almac&eacute;n virtual de forma exitosa');
			}, function(r){
				console.log(r);
				showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
			});
			 
			//pepe.$promise.then(function(result){alert(result.status);});
			
			reset();
			// showAlert();
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