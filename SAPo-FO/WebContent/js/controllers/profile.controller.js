(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('UserProfileController', UserProfileController);
    UserProfileController.$inject = [/*'UserProfileResource', */ 'UserProfileResourceSubmit', '$scope', '$cookies'];
    /* @ngInject */
    function UserProfileController(/*UserProfileResource, */UserProfileResourceSubmit, $scope, $cookies) {
    	$scope.test = 'Perfil de usuario';    	
    	
    	var user = $cookies.getObject("sapoUser");
    	$scope.nick = user.nick;
    	$scope.mail = user.mail;
    	
    	//el valor del input esta bindeado con $scope.mail
    	//al guardar hay que llamar al rest: /api/usuario/emailUpdate (POST)
    	//con un body: {"nick":"nick_del_cristiano","mail":"nuevo_email"} 
    	//eso devuelve el nuevo json del usuario completito, incluyendo el nuevo email
    	//que supongo se tendría que volver a cargar en la cookie
        $scope.submitMail = function(){
    	    UserProfileResourceSubmit.save({nick: $scope.nick, mail: $scope.mail})
    		.$promise.then(function(result) {
    			//console.log("hola");
    	        //$scope.sapoUser = result;
    	    });
    	    user.mail = $scope.mail;
    	    console.log(user);
    	    $cookies.put("sapoUser", angular.toJson(user));
        	//console.log(user);
        }
    }
    
})();