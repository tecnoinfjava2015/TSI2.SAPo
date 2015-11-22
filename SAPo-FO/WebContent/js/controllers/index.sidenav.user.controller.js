(function() {
    'use strict';
    angular
        .module('sapo')
        .controller('SidenavUserController', SidenavUserController);
    SidenavUserController.$inject = ['$scope','$cookies', '$location', '$window'];
    /* @ngInject */
    function SidenavUserController($scope,$cookies, $location, $window) {
    	$scope.logout = logout;
    	$scope.redirect = redirect
    	
        var vm = this
    	var userTwitter = $cookies.getObject('sapoUser');
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
		
		function logout() {
			$cookies.remove('sapoUser');
			$cookies.remove('sapoVirtualStorages');
			
			//var landingUrl = $window.location.host + "/SAPo-FO/index.html";
			
			//$window.location.href = landingUrl;
			
			var landingUrl = "http://" + $window.location.host + "/SAPo-FO/index.html";
			console.log(landingUrl);
			$window.location.href = landingUrl;
			
		}
		
		
        function redirect(route){
        	$scope.$apply(function() { $location.path(route); });
        }
        
    }
})();