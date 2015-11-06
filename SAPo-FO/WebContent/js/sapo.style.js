(function() {
    'use strict';
    angular
        .module('sapo')
        .config(config);
    config.$inject = ['$mdThemingProvider'];
    /* @ngInject */
    function config($mdThemingProvider) {

        $mdThemingProvider
            .theme('default')
            .primaryPalette('indigo')
            .accentPalette('pink')
            .warnPalette('red');

        $mdThemingProvider.theme('test1')
            .primaryPalette('indigo')
            .accentPalette('pink')

        $mdThemingProvider.theme('test2')
            .primaryPalette('lime')
            .accentPalette('orange')

        $mdThemingProvider.theme('test3')
            .primaryPalette('red')
            .accentPalette('blue')

        $mdThemingProvider.theme('test4')
            .primaryPalette('teal')
            .accentPalette('blue-grey')

        $mdThemingProvider.alwaysWatchTheme(true);
    }
})();
