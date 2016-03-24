angular.module('web', ['ui.bootstrap','ui.utils','ui.router','ngAnimate']);

angular.module('web').config(function($stateProvider, $urlRouterProvider) {

    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'app/login/login.html'
    });
    $stateProvider.state('customer-list', {
        url: '/customer',
        templateUrl: 'app/customer/customer-list.html'
    });
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: 'app/home/home.html'
    });
    /* Add New States Above */
    $urlRouterProvider.otherwise('/home');

});

angular.module('web').run(function($rootScope) {

    $rootScope.safeApply = function(fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

});
