(function() {
    'use strict';
    angular
        .module('sapo')
        .config(config);
    config.$inject = ['$mdThemingProvider'];
    /* @ngInject */
    function config($mdThemingProvider) {
    $mdThemingProvider.theme('test1')
      .primaryPalette('indigo')
      .accentPalette('pink')
      .backgroundPalette('grey');

    $mdThemingProvider.theme('test2')
      .primaryPalette('lime')
      .accentPalette('orange')
      .backgroundPalette('blue');

	$mdThemingProvider.theme('test3')
      .primaryPalette('red')
      .accentPalette('blue')
      .backgroundPalette('teal');

    $mdThemingProvider.theme('test4')
      .primaryPalette('teal')
      .accentPalette('blue-grey')
      .backgroundPalette('red');
    
    $mdThemingProvider.alwaysWatchTheme(true);
    }
})();