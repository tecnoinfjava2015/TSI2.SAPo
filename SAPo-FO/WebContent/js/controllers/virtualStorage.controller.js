(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageController', VirtualStorageController);
    VirtualStorageController.$inject = ['VirtualStorageResource',  '$scope'];
    /* @ngInject */
    function VirtualStorageController(VirtualStorageResource, $scope) {
    	$scope.test = 'Crear Almac&eacute;n Virtual';
    	
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
	          data.loading = e.target.result;
	        }
	        r.readAsDataURL(f);
	            	    
    		VirtualStorageResource.save(data,function(){
    			
    		});
    	}    	
    }
})();