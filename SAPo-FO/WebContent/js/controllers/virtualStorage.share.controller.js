(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageShareController', VirtualStorageShareController);
    VirtualStorageShareController.$inject = ['VirtualStorageShareResource', '$scope', '$routeParams', '$cookies', '$mdDialog'];
    /* @ngInject */
    function VirtualStorageShareController(VirtualStorageShareResource, $scope, $routeParams, $cookies, $mdDialog) {
    	$scope.title = 'Compartir Almacen';   
    	$scope.share = share;
    	
    	$scope.cancel = cancel;
    	$scope.showAlert = showAlert;   	
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');

    	function share() {   
    		if ($scope.nick != null) {
	    		VirtualStorageShareResource.save({tenantId: $scope.virtualStorageId, nick: $scope.nick}, function(){
	    		showAlert('Exito!','Ha compartido su almac&eacute;n con ' + $scope.nick);
				}, function(r){
					console.log(r);
					showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
				});
	    		
    		}
    		else {
    			showAlert('Error', 'Debe ingresar el nick del usuario con quien desea compartir');
    		}
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