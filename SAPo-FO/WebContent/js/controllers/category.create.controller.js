(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CreateCategoryController', CreateCategoryController);
    CreateCategoryController.$inject = ['CreateCategoryResource',  '$scope', '$cookies', '$location', '$mdDialog'];
    /* @ngInject */
    function CreateCategoryController(CreateCategoryResource, $scope, $cookies, $location, $mdDialog) {
    	$scope.fields = []; 
    	$scope.insert = insert;
    	
    	var userId = $cookies.getObject("sapoUser");
    	
    	$scope.cancel = cancel;
    	$scope.showAlert = showAlert;
    	
    	var res = $location.path().split("/");
    	$scope.virtualStorageId = $cookies.get('sapoCurrentVirtualStorage');
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	$scope.virtualStorageName = res[2];
    	
    	function insert( data) {   
    		if (data != null && typeof data.name !== 'undefined') {
	    		data.virtualStorageName = $scope.virtualStorageName;
	    		data.virtualStorageId = $scope.virtualStorageId;
	    		CreateCategoryResource.save({tenantId: $scope.virtualStorageId },data,function(){
	    		showAlert('Exito!','Se ha creado su categor&iacute;a de forma exitosa');
				}, function(r){
					console.log(r);
					showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
				});
	    		
    		}
    		else {
    			showAlert('Error', 'Debe ingresar el nombre de la categor&iacute;a');
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