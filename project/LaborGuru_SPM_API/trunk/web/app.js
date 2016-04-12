angular.module('web', ['ui.bootstrap','ui.utils','ui.router','ngAnimate', 'smart-table']);

angular.module('web').config(function($stateProvider, $urlRouterProvider) {

    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'app/login/login.html'
    });
    $stateProvider.state('customer-list', {
        url: '/customer',
        templateUrl: 'app/customer/customer-list.html'
    });
    $stateProvider.state('store-list', {
        url: '/store',
        templateUrl: 'app/store/store-list.html'
    });
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: 'app/home/home.html'
    });
    $stateProvider.state('show-details', {
        url: '/store/show/:id',
        templateUrl: 'app/store/details/show-details.html'
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
