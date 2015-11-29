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
        $scope.theme = {};
        var userId = $cookies.getObject("sapoUser").id;

        var tenantid = $rootScope.tenantId;
        
        $scope.vs = VirtualStorageEditResource.get({
            id: tenantid
        });


        $scope.showAlert = showAlert;

        $scope.upload = upload;
        $scope.update = update;
        $scope.cancel = cancel;

		$scope.primaries = ["purple", "indigo", "light-blue", "teal","amber", "deep-orange", "brown", "grey"];
		$scope.accents = ["pink","cyan","lime", "yellow"];
		$scope.images = ["Barras1","Barras2","Barras3","Barras4","Barras5","Circulos1","Circulos2","Circulos3","Circulos4","Circulos5","Concentricos1","Concentricos2","Concentricos3","Concentricos4","Concentricos5","Diagonales1","Diagonales2","Diagonales3","Diagonales4","Diagonales5","Servilletas1","Servilletas2","Servilletas3","Servilletas4","Servilletas5","Tarjetas1","Tarjetas2","Tarjetas3","Tarjetas4","Tarjetas5"];
        function upload() {
            document.getElementById("file").click();
        }

        function update(vs) {
        	console.log(vs);
        	if ($scope.theme.primary=='' || $scope.theme.primary==null) {
        		if(vs.theme==''||vs.theme==null||vs.theme=='NaN'||vs.theme=='undefined'){
        			$scope.theme.primary="indigo";
        		}
			}
			if ($scope.theme.accent=='' || $scope.theme.accent==null ) {
				if(vs.theme==''||vs.theme==null||vs.theme=='NaN'||vs.theme=='undefined'){
					$scope.theme.accent="pink";
				}
			}
			if ($scope.theme.sidenavTop=='' || $scope.theme.sidenavTop==null ) {
				if(vs.sidenavTop==''||vs.sidenavTop==null||vs.sidenavTop=='NaN'||vs.sidenavTop=='undefined'){
					$scope.theme.sidenavTop="Barras1";
				}
			}
			
			vs.theme = $scope.theme.primary + $scope.theme.accent;
			vs.sidenavTop = $scope.theme.sidenavTop
			var style = {};
			style.theme = vs.theme;
			style.sidenavTop = vs.sidenavTop;
			
			
            if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
                vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
                vs.logo = $scope.vs.logo + $scope.logoFile.base64;
            }
            
            if (vs != null && typeof vs.name !== 'undefined') {
            	VirtualStorageEditResource.update({id: vs.id}, vs).$promise.then(function(data){

                	showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
            		console.log(style);
					$rootScope.$broadcast("changeTheme",style);
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
