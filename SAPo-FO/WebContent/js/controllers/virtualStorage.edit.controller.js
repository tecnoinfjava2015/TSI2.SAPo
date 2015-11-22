(function() {
    'use strict';
    angular.module('sapo').controller('VirtualStorageEditController',
        VirtualStorageEditController);
    VirtualStorageEditController.$inject = ['VirtualStorageEditResource', '$scope',
        '$mdDialog', '$cookies', '$rootScope', '$window'
    ];
    /* @ngInject */
    function VirtualStorageEditController(VirtualStorageEditResource, $scope, $mdDialog, $cookies, $rootScope, $window) {
        $scope.master = {};
        $scope.logoFile;
        $scope.themes = ['theme test'];
        $scope.sidenavTops = ['side nav test'];
        $scope.sidenavBottoms = ['side nav bottom test'];
        var userId = $cookies.getObject("sapoUser").id;

        var tenantid = $rootScope.tenantId;
        
        $scope.vs = VirtualStorageEditResource.get({
            id: tenantid
        });


        $scope.showAlert = showAlert;

        $scope.upload = upload;
        $scope.update = update;
        $scope.cancel = cancel;


        function upload() {
            document.getElementById("file").click();
        }

        function update(vs) {
        	
            if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
                vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
                vs.logo = $scope.vs.logo + $scope.logoFile.base64;
            }
            
            if (vs != null && typeof vs.name !== 'undefined') {
            	VirtualStorageEditResource.update({id: vs.id}, vs).$promise.then(function(data){
                	showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                	var landingUrl = "http://" + $window.location.host + "/SAPo-FO/index.html#/virtualStorage/" + vs.name;
					console.log(landingUrl);
					$window.location.href = landingUrl;
                	
                }, function(error){
                	showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
                });
            }
            else {
				showAlert('Error', 'Debe ingresar el nombre del almac&eacute;n');
			}
            

        }

        function showAlert(title, content) {
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
