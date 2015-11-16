(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageEditController',
			VirtualStorageEditController);
	VirtualStorageEditController.$inject = [ 'VirtualStorageEditResource', '$scope', '$location',
			'$mdDialog', '$cookies' ];
	/* @ngInject */
	function VirtualStorageEditController(VirtualStorageEditResource, $scope, $location, $mdDialog, $cookies) {
		$scope.vs = new VirtualStorageEditResource();
		$scope.master = {};
		$scope.logoFile;
		$scope.themes = [ 'theme test' ];
		$scope.sidenavTops = [ 'side nav test' ];
		$scope.sidenavBottoms = [ 'side nav bottom test' ];

		$scope.showAlert = showAlert;
		
		$scope.upload = upload;
		$scope.update = update;
		$scope.cancel = cancel;
		
		
		function upload() {
			document.getElementById("file").click();
		}

		function update(data) {			
			$scope.vs.enabled = true;
			
			if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
				$scope.vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
				$scope.vs.logo = $scope.vs.logo + $scope.logoFile.base64;
			}
			
			var res = $location.path().split("/");
	    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
	    	var count = virtualStorages.owned.length;
	    	var i = 0;
	    	for (i = 0; i < count; i++) {
	    		if (virtualStorages.owned[i].name == res[2]) {
	    			$scope.virtualStorageName = virtualStorages.owned[i].name;
	    			$scope.virtualStorageId = virtualStorages.owned[i].id;
	    		}
	    	}
	    	
	    	$scope.vs.id = $scope.virtualStorageId;

			$scope.vs.$save(function(r) {
				showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
			}, function(r){
				console.log(r);
				showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
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




    	
    	