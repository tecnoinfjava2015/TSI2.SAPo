(function() {
    'use strict';
    angular.module('sapo').controller('VirtualStorageEditController',
        VirtualStorageEditController);
    VirtualStorageEditController.$inject = ['VirtualStorageEditResource', '$scope',
        '$mdDialog', '$cookies', '$rootScope'
    ];
    /* @ngInject */
    function VirtualStorageEditController(VirtualStorageEditResource, $scope, $mdDialog, $cookies, $rootScope) {
        $scope.master = {};
        $scope.logoFile;
        $scope.themes = ['theme test'];
        $scope.sidenavTops = ['side nav test'];
        $scope.sidenavBottoms = ['side nav bottom test'];


        var virtualStorages = $cookies.getObject("sapoVirtualStorages");
        var count = virtualStorages.owned.length;
        var i = 0;
        for (i = 0; i < count; i++) {
            if (virtualStorages.owned[i].name == $rootScope.tenantName) {
                var vsId = virtualStorages.owned[i].id;

            }
        }
        

        $scope.vs = VirtualStorageEditResource.get({
            id: vsId
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
            console.log("VS>>>" + vs);
            // var resource = VirtualStorageEditResource.get({
            //     id: data.id
            // });
            // resource.id = $rootScope.tenantId;
            // alert(resource.id);
            //$scope.id = resource.id;

            //$scope.vs.id = $scope.virtualStorageId;

            VirtualStorageEditResource.update({id: vs.id}, vs).$promise.then(function(data){
            	showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
            	
            }, function(error){
            	showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            /*
	    	VirtualStorageEditResource.$save(function(r) {
				showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
			}, function(r){
				console.log(r);
				showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
			});*/ 

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
