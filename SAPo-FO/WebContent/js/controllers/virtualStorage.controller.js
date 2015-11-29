(function() {
	'use strict';
	angular.module('sapo').controller('VirtualStorageController',
			VirtualStorageController);
	VirtualStorageController.$inject = [ 'VirtualStorageResource', 'UnitResource', '$scope', '$cookies', '$mdDialog', '$window','$rootScope'];
	/* @ngInject */
	function VirtualStorageController(VirtualStorageResource, UnitResource, $scope, $cookies, $mdDialog, $window,$rootScope) {
		var user = $cookies.getObject("sapoUser");
		var virtualStorages = $cookies.getObject("sapoVirtualStorages");
		
		$scope.vs = new VirtualStorageResource();
		$scope.master = {};
		$scope.logoFile;
		$scope.themes = [ 'theme test' ];
		$scope.sidenavTops = [ 'side nav test' ];
		$scope.sidenavBottoms = [ 'side nav bottom test' ];
		$scope.theme = {};
		$scope.showAlert = showAlert;
		
		$scope.upload = upload;
		$scope.insert = insert;
		$scope.reset = reset;
		$scope.cancel = cancel;
		$scope.unit = new UnitResource();
//		$scope.primaries = ["purple", "deep-purple", "indigo", "blue", "light-blue", "teal", "green","amber", "deep-orange", "brown", "grey"];
		$scope.primaries = ["purple", "indigo", "light-blue", "teal","amber", "deep-orange", "brown", "grey"];
//		$scope.accents = ["pink","cyan","lime", "yellow","orange"];
		$scope.accents = ["pink","cyan","lime", "yellow"];
		$scope.images = ["Barras1","Barras2","Barras3","Barras4","Barras5","Circulos1","Circulos2","Circulos3","Circulos4","Circulos5","Concentricos1","Concentricos2","Concentricos3","Concentricos4","Concentricos5","Diagonales1","Diagonales2","Diagonales3","Diagonales4","Diagonales5","Servilletas1","Servilletas2","Servilletas3","Servilletas4","Servilletas5","Tarjetas1","Tarjetas2","Tarjetas3","Tarjetas4","Tarjetas5"];
		function upload() {
			document.getElementById("file").click();
		}

		function insert(vs) {	
			if ($scope.theme.primary=='' || $scope.theme.primary==null ) {
				$scope.theme.primary="indigo";
			}
			if ($scope.theme.accent=='' || $scope.theme.accent==null ) {
				$scope.theme.accent="pink";
			}
			if ($scope.theme.sidenavTop=='' || $scope.theme.sidenavTop==null ) {
				$scope.theme.sidenavTop="Barras1";
			}			 
			vs.theme = $scope.theme.primary + $scope.theme.accent;
			vs.sidenavTop = $scope.theme.sidenavTop
			var style = {};
			style.theme = vs.theme;
			style.sidenavTop = vs.sidenavTop;
			var vsName = vs.name;
			vs.enabled = true;
			
			if (typeof $scope.logoFile !== 'undefined' && $scope.logoFile !== null) {
				vs.logo = "data:" + $scope.logoFile.filetype + ";base64,";
				vs.logo = vs.logo + $scope.logoFile.base64;
			}
			
			if (typeof vs.name !== 'undefined') {
				vs.$save(function(r) {
					var i = 0;
					var vsIdAux = '';
					while (typeof r[i] !== 'undefined') {
						vsIdAux += r[i];						
						i++;
					}
					user.tenantCreados.push(vsIdAux);
					user = JSON.stringify(user);
					
					virtualStorages.owned.push(vsIdAux);
					
					console.log(virtualStorages);
					$cookies.remove("sapoVirtualStorages");
					
					$cookies.put("sapoVirtualStorages", JSON.stringify(virtualStorages));
					$cookies.remove("sapoUser");
					$cookies.put("sapoUser", user);
					$scope.unit.virtualStorageId = parseInt(vsIdAux);
					UnitResource.save(
						$scope.unit,
						function(result) {
							$scope.loading = false;
							showAlert('Exito!', 'Se ha creado su almac&eacute;n virtual de forma exitosa');
							var landingUrl = "http://" + $window.location.host + "/SAPo-FO/index.html#/virtualStorage/" + vsName;
							console.log(landingUrl);
							console.log(style);
							$rootScope.$broadcast("changeTheme",style);
							$window.location.href = landingUrl;
						},
						function(result) {
							$scope.loading = false;
							console.log(r); 
							showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
						});
				
					
					
				}, function(r){
					console.log(r);
					
					/*showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');*/
				});
				reset();
			}
			else {
				showAlert('Error', 'Debe ingresar el nombre del almac&eacute;n');
			}
		}

		

		function reset() {
			$scope.vs = angular.copy($scope.master);
			var fn = document.getElementById('fileName');
			fn.value = "";
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
		

    	function cancel() {
    		$mdDialog.cancel();
		};

	}
})();