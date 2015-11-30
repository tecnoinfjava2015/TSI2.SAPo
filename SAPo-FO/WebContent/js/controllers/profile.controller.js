(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('UserProfileController', UserProfileController);
    UserProfileController.$inject = [ 'UserProfileResourceSubmit', '$scope', '$cookies', '$mdDialog','NgMap' ];
    /* @ngInject */
    function UserProfileController(UserProfileResourceSubmit, $scope, $cookies, $mdDialog,NgMap) {
    	$scope.test = 'Perfil de usuario';    	
    	var user = $cookies.getObject("sapoUser");
    	$scope.nick = user.nick;
    	$scope.mail = user.mail;
    	var alert;
    	$scope.$emit('menuOption','NULL');  
    	//$scope.showDialog = showDialog;
    	
        $scope.submitMail = function(){
    	    UserProfileResourceSubmit.save({nick: $scope.nick, mail: $scope.mail})
    		.$promise.then(function(result) {
    			//console.log("hola");
    	        //$scope.sapoUser = result;
    	    });
    	    user.mail = $scope.mail;
    	    console.log(user);
    	    $cookies.put("sapoUser", JSON.stringify(user));
        	//TODO: tirar un alert o cosa linda que diga que se cambió OK.
	        alert = $mdDialog.alert()
	          .title('Exito!')
	          .content('Se ha cambiado su email correctamente')
	          .ok('Cerrar');

	        $mdDialog
	            .show( alert )
	            .finally(function() {
	              alert = undefined;
	            });
        }
    	$scope.address = user.latitud + ',' + user.longitud;
    	
    	NgMap.getMap().then(function(map) {
    	    console.log(map.getCenter());
    	    console.log('markers', map.markers);
    	    console.log('shapes', map.shapes);
    	});
    }
    
})();