(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('UserProfileController', UserProfileController);
    UserProfileController.$inject = [/*'UserProfileResource', */ '$scope', '$cookies'];
    /* @ngInject */
    function UserProfileController(/*UserProfileResource, */$scope, $cookies) {
    	$scope.test = 'Perfil de usuario';    	
    	
    	var user = $cookies.getObject("sapoUser");
    	$scope.nick = user.nick;
    	
    	
    }
})();