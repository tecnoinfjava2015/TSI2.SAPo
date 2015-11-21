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
    	
    	$scope.Spec = Spec;
    	
    	function upload() {
			document.getElementById("file").click();
		}
    	
    	function insert( data) {   
    		var images = $scope.images;
    		if (images != null && typeof images !== 'undefined') {
    			var count = images.length;
        		var mongoImages = [];
        		var i = 0;
        		
        		//data.attributes = JSON.stringify($scope.fields);
        		
        		for (i = 0; i < count; i++) {
        			 			
        			var image = "data:" + images[i].filetype + ";base64," + images[i].base64;
        			mongoImages.push(image);
        			
        		}
        		
        		data.images = mongoImages;
    		}
    		
    		console.log($scope.fields);
    		data.specs = [];
    		
    		for (i = 0; i < $scope.fields.length; i++) {
    			$scope.spec = new Spec( $scope.fields[i].name, $scope.fields[i].value, $scope.fields[i].type);
    			data.specs.push($scope.spec);
    			console.log(data.specs);
    			//data.attributes += JSON.stringify($scope.fields[i]);
    		}
    		
    		CreateProductsResource.save({tenantId: $scope.tenantId },data,function(){
    		});
    	}
    	
    	function addAttribute() {
    		$scope.fields.push({});
    	}
    	
    	function Spec(name, value, type) {
    		this.name = name;
    		this.value = value;
    		this.type = type;
    	}
    }
})();