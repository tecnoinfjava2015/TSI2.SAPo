(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageController', VirtualStorageController);
    VirtualStorageController.$inject = ['VirtualStorageResource',  '$scope', '$mdDialog'];
    /* @ngInject */
    function VirtualStorageController(VirtualStorageResource, $scope, $mdDialog) {
    	$scope.test = 'Crear Almac&eacute;n Virtual';
    	$scope.master = {};
    	$scope.upload = upload;
    	
    	function upload() {
    		//alert('hola');
    		//alert(document.getElementById("file"));
    		document.getElementById("file").click();
    	}
    	
    	$scope.insert = insert;
    	
    	function insert(data) {
    		
    		var f = document.getElementById('file').files[0],
	            r = new FileReader();
	        r.onloadend = function(e){
	          data.logo = e.target.result;
	        }
	        if(typeof f !== "undefined") {
		        r.readAsDataURL(f);	        	
	        }
	        data.enabled = true;
    		VirtualStorageResource.save(data,function(){
    			
    		});//.$promise.then(function(result) {
    			/*alert('hola');
    			console.log(result);
    			if (result.$success) {
    				alert('hola 2');
    			}*/
    		//});
    		reset();
    		//showAlert();
    	}    
    	
    	$scope.reset = reset;
    	
    	function reset() {
            $scope.vs = angular.copy($scope.master);
            var fn = document.getElementById('fileName'); 
            fn.value="";
        };
        
        $scope.themes = ['theme test'];
        
        $scope.sidenavTops = ['side nav test'];
        
        $scope.sidenavBottoms = ['side nav bottom test'];
        
        $scope.showAlert = showAlert;
        
        function showAlert() {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog
            $mdDialog.show(
              $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title('Exito!')
                .content('Se ha creado su almac&eacute;n virtual de forma exitosa')
                .ariaLabel('Alert Dialog Demo')
                .ok('Cerrar')
            );
        };
          
    }
})();