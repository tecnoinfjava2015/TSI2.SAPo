(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', 'VirtualStorageEditResource', '$scope', '$location', '$mdDialog', '$rootScope', '$cookies', '$window' ];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, VirtualStorageEditResource, $scope, $location, $mdDialog, $rootScope, $cookies, $window ) {
    	var loggedUser = $cookies.getObject("sapoUser");
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	/*
    	alert(loggedUser.type === 'Free' && virtualStorages.owned.length > 1);
    	*/
    	
    	var vm = this;
    	$scope.virtualStorages = {};
    	$scope.title = 'Almacenes virtuales';    
    	
        //JPMC Para emitir el menu, se levanta en el indexcontroller
        $scope.$emit('menuOption',"VS_NAVIGATION"); 
        
    	$scope.showAdvanced = showAdvanced;
    	$scope.editVirtualStorage = editVirtualStorage;
    	$scope.deleteVirtualStorage = deleteVirtualStorage;
    	$scope.cancel = cancel;
    	$scope.showAlert = showAlert;
    	$scope.updateIndex = updateIndex;
    	
    	VirtualStorageViewResource.query({
        }).$promise.then(function(result) {
        	console.log(result);
            $scope.virtualStorages = result.owned;
            $scope.virtualStoragesFollowing = result.following;
            $scope.loading = false;
        },function(error) {
        	console.log(error);
        
        });
    	
    	$scope.$on("editVirtualStorage", function(event,option) {
    		console.log("EN EDIT VIRTUAL STORAGE");
    		$scope.virtualStorages = [];
    		$scope.virtualStoragesFollowing=[];
    		VirtualStorageViewResource.query({
            }).$promise.then(function(result) {
            	console.log(result);
                $scope.virtualStorages = result.owned;
                $scope.virtualStoragesFollowing = result.following;
                $scope.loading = false;
            },function(error) {
            	console.log(error);
            
            });
		})
		
		
    	//console.log($scope.virtualStorages);
    	
    	
    	function editVirtualStorage(ev, virtualStorage) {
    		$rootScope.tenantName = virtualStorage.name;
    		$rootScope.tenantId = virtualStorage.id;
    		$rootScope.ownerId = virtualStorage.owner;
    		$mdDialog.show({
    	    	controller: 'VirtualStorageEditController',
                templateUrl: 'templates/virtualstorage.edit.html',
    	    	
    	        parent: angular.element(document.body),
    	        targetEvent: ev,
    	        clickOutsideToClose:true
    	    })
    	    .then(function(answer) {
    	        $scope.status = 'You said the information was "' + answer + '".';
    	    }, function() {
    	        $scope.status = 'You cancelled the dialog.';
    	    });
    		//$location.url('http://localhost:8080/SAPo-FO/index.html#/virtualStorage/' + virtualStorageName + '/edit');
    	}
    	
    	
    	function deleteVirtualStorage(vs) {
    		vs.enabled = false;
    		VirtualStorageEditResource.update({id: vs.id}, vs).$promise.then(function(data){
    			
				
            	showAlert('Exito!','Se ha eliminado su almac&eacute;n virtual de forma exitosa');
        		$scope.$apply();
            }, function(error){
            	showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });

    	}
    	
    	function cancel() {
    		$mdDialog.cancel();
		};
		
		function showAlert(title,content) {
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
    	
    	function showAdvanced(ev) {
    		var virtualStoragesOwned;
    		VirtualStorageViewResource.query({
            }).$promise.then(function(result) {
            	console.log(result);
            	virtualStoragesOwned = result.owned;
            	console.log(virtualStoragesOwned);
        		if (loggedUser.type === 'Free' && typeof virtualStoragesOwned !== 'undefined' && virtualStoragesOwned.length > 0) {
        			showAlert('Error!', 'Usted ha alcanzado el m&aacute;ximo de almacenes virtuales permitidos.</br>Si desea agregar m&aacute;s almacenes virutales debe realizar un upgrade de su cuenta.');
        		}
        		else if (loggedUser.type === 'FREEMIUM' && typeof virtualStoragesOwned !== 'undefined' && virtualStoragesOwned.length > 1) {
        			showAlert('Error!', 'Usted ha alcanzado el m&aacute;ximo de almacenes virtuales permitidos.</br>Si desea agregar m&aacute;s almacenes virutales debe realizar un upgrade de su cuenta.');
        		}
        		else {
        			$mdDialog.show({
            	    	controller: 'VirtualStorageController',
                        templateUrl: 'templates/virtualStorage.create.html',
            	    	
            	        parent: angular.element(document.body),
            	         targetEvent: ev,
            	        clickOutsideToClose:true
            	    })
            	    .then(function(answer) {
            	        $scope.status = 'You said the information was "' + answer + '".';
            	    }, function() {
            	        $scope.status = 'You cancelled the dialog.';
            	    });
        		}
            },function(error) {
            	console.log(error);
            });
    	  };
    	  
    	  function updateIndex(vs){
    		  var style = {};
    		  style.theme = vs.theme;
    		  style.sidenavTop = vs.sidenavTop 
    		  console.log(style);
    		  $rootScope.$broadcast("changeTheme",style);
    		  $cookies.remove("sapoCurrentVirtualStorage");
    		  $cookies.put("sapoCurrentVirtualStorage", vs.id);
    		  $cookies.put("sapoCurrentVirtualStorageName", vs.name);
    		  $window.location.href = "#/virtualStorage/"+vs.name;
    	  }
    }
})();
