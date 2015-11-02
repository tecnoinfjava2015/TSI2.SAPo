(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageController', VirtualStorageController);
    VirtualStorageController.$inject = ['VirtualStorageResource',  '$scope'];
    /* @ngInject */
    function VirtualStorageController(VirtualStorageResource, $scope) {
    	$scope.test = 'Crear Almac&eacute;n Virtual';
    	
    	//$scope.vs = {
    	//		id:2,
//    			connection:'',
//    			url:'',
//    			createdDate:null,
//    			name:'',
//    			css:'algo',
//    			logo:'',
//    			loading:'',
//    			enable:true,
    	//		owner:{id:1}
    			
    	//};
    	
    	$scope.insert = insert;
    	
    	function insert(data) {
    		VirtualStorageResource.save(data,function(){
    			alert('Alertaaaaaa');
    		});
    	}
    	/*private int id;
    	private String connection;
    	private String url;
    	private Date createdDate;
    	private String name;
    	private String CSS;
    	private String logo;
    	private String loading;
    	private Boolean enabled;
    	
    	@ManyToOne (optional = true)
        @JoinColumn(name = "tenantCreados")
    	private Usuario owner;*/
    	    	
    }
})();