(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('SidenavUserController', SidenavUserController);
    SidenavUserController.$inject = ['$scope','$cookies'];
    /* @ngInject */
    function SidenavUserController($scope,$cookies) {
        var vm = this
    	var userTwitter = $cookies.getObject('sapoUser')
    	vm.userLogged = false;
    	
		if ((typeof userTwitter !== "undefined") && (userTwitter !== null)) {
			vm.userLogged = (userTwitter.twitterId!==0);
	        vm.userName = userTwitter.name;
	        if (userTwitter.mail==""){
	        	vm.userMail = "...";	
	        }else{
	        	vm.userMail = userTwitter.mail;
	        }
		}  
        
    }
})();