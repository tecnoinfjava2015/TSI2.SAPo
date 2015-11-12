(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('SidenavUserController', SidenavUserController);
    SidenavUserController.$inject = ['$scope','$cookies'];
    /* @ngInject */
    function SidenavUserController($scope,$cookies) {
    	var userTwitter = $cookies.getObject('sapoUser')
        $scope.userLogged = (userTwitter.twitterId!==0);
        $scope.userName = userTwitter.name;
        if (userTwitter.mail==""){
        	$scope.userMail = "...";	
        }else{
        	$scope.userMail = userTwitter.mail;
        }
    }
})();