(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('VirtualStorageNavigateController', VirtualStorageNavigateController);
    VirtualStorageNavigateController.$inject = ['VirtualStorageViewResource', 'VirtualStorageEditResource', '$scope', '$location', '$mdDialog', '$rootScope', '$cookies'];
    /* @ngInject */
    function VirtualStorageNavigateController(VirtualStorageViewResource, VirtualStorageEditResource, $scope, $location, $mdDialog, $rootScope, $cookies) {
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
    		/*var index = loggedUser.tenantCreados.indexOf(vs.id.toString());
    		var removed = 0;
			if (index > -1) {
				removed = loggedUser.tenantCreados.splice(index, 1);
				console.log(removed);
			}

			loggedUser = JSON.stringify(loggedUser);
			
			index = virtualStorages.owned.indexOf(vs.id.toString());
			if (index > -1) {
				virtualStorages.owned.splice(index, 1);
			}
			
			
			$cookies.remove("sapoVirtualStorages");
			
			$cookies.put("sapoVirtualStorages", JSON.stringify(virtualStorages));
			$cookies.remove("sapoUser");
			$cookies.put("sapoUser", loggedUser);*/
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
    		  
    	  }
    }
})();
