(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('NotificationCreateController', NotificationCreateController);
    NotificationCreateController.$inject = ['$scope', 'NotificationResource', '$mdDialog', '$location', '$cookies', '$rootScope'];
    /* @ngInject */
    function NotificationCreateController($scope, NotificationResource, $mdDialog, $location, $cookies, $rootScope) {
    	console.log('hola1');
    	$scope.title = 'Crear Notificaci&oacute;n';
    	$scope.cancel = cancel;
    	$scope.showAlert = showAlert;
    	
    	var res = $location.path().split("/");
    	var virtualStorages = $cookies.getObject("sapoVirtualStorages");
    	var count = virtualStorages.owned.length;
    	var i = 0;
    	for (i = 0; i < count; i++) {
    		if (virtualStorages.owned[i].name == res[2]) {
    			$scope.virtualStorageName = virtualStorages.owned[i].name;
    			$scope.tenantId = virtualStorages.owned[i].id;
    			console.log($scope.tenantId);
    		}
    	}
    	console.log('hola2');
//    	$scope.tenantId = 2;
    	
        var vm = this;
//        vm.insert = insert;
        
        $scope.insert = function() {
        	console.log('hola3');
        	if ($scope.notification != null && typeof $scope.notification.barcode !== 'undefined' && typeof $scope.notification.minStock !== 'undefined' && $scope.notification.mensaje !== 'undefined') {
    			$scope.loading = true;
        	
    			$scope.notification.vsid = $scope.tenantId;
    			$scope.notification.active = false;
        	
            NotificationResource.save({
                tenantId: $scope.tenantId
            }, $scope.notification, function() {
                showAlert('Exito!', 'Se ha creado su notificaci&oacute;n de forma exitosa');

            }, function(r) {
                console.log(r);
                showAlert('Error!', 'Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
        	}
    		else {
    			showAlert('Error!','Debe ingresar el codigo de barras y el stock minimo.');
    		}
        }

        function showAlert(title, content) {
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
    	}
    }
})();
