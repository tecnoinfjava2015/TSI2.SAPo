(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('UserProfileController', UserProfileController);
    UserProfileController.$inject = [/*'UserProfileResource', */ '$scope'];
    /* @ngInject */
    function UserProfileController(/*UserProfileResource, */$scope) {
    	$scope.test = 'Perfil de usuario';
    	
    }
})();