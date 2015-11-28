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
    	$scope.redirectToProfile = redirectToProfile;
    	
    	
        var vm = this
    	var userTwitter = $cookies.getObject('sapoUser');
    	vm.userLogged = false;
    	
    	$scope.userAvatar = $cookies.get('userAvatar');
    	
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
			$cookies.remove('userAvatar');
			$cookies.remove('sapoCurrentVirtualStorage');
			
			//var landingUrl = $window.location.host + "/SAPo-FO/index.html";
			
			//$window.location.href = landingUrl;
			
			var landingUrl = "http://" + $window.location.host + "/SAPo-FO/";
			console.log(landingUrl);
			$window.location.href = landingUrl;
			
		}
		
		function redirectToProfile() {
			var path = '';
			if (typeof $location.Path !== 'undefined') {
				path = $location.Path;
			}
			var landingUrl = "http://" + $window.location.host + "/SAPo-FO/#/" + path + "userProfile";
			console.log($location.Path);
			$window.location.href = landingUrl;
		}
		
        function redirect(route){
        	$scope.$apply(function() { $location.path(route); });
        }
        
    }
})();