(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('CreateProductController', CreateProductController);
    CreateProductController.$inject = ['CreateProductsResource',  '$scope'];
    /* @ngInject */
    function CreateProductController(CreateProductsResource, $scope) {
    	$scope.title = 'Crear Producto';
    	$scope.tenantId = 1;
    	$scope.fields = []; 
    	$scope.insert = insert;
    	$scope.upload = upload;
    	$scope.addAttribute = addAttribute;
    	
    	function upload() {
			document.getElementById("file").click();
		}
    	
    	function insert( data) {   
    		var images = $scope.images;
    		var count = images.length;
    		var mongoImages = [];
    		var i = 0;
    		console.log($scope.fields);
    		//data.attributes = $scope.fields;
    		
    		for (i = 0; i < count; i++) {
    			 			
    			var image = "data:" + images[i].filetype + ";base64," + images[i].base64;
    			mongoImages.push(image);
    			
    		}
    		
    		data.images = mongoImages;
    		CreateProductsResource.save({tenantId: $scope.tenantId },data,function(){
    		});
    	}
    	
    	function addAttribute() {
    		$scope.fields.push({'id':'choice'});
    	}
    }
})();