var host="";

function getURI(relativeURL) {
    return host + relativeURL;
}

alert("Inicion conteo");
angular.module('web', ['ui.bootstrap','ui.utils','ui.router','ngAnimate','smart-table','blockUI']);

var messagesLoadedQueue = [];
var sessionLoadedQueue = [];
var applicationInitializedQueue = [];
var isMessagesLoaded = false;
var isSessionLoaded = false;
var isApplicationInitialized = false;

function onMessagesLoaded(fn){
  messagesLoadedQueue.push(fn);
}

function onSessionLoaded(fn){
  sessionLoadedQueue.push(fn);
}

function onApplicationInitialized(fn){
  applicationInitializedQueue.push(fn);
}

function showLoading(){
  setTimeout(function(){$(".page-loading-container").fadeIn(750, function(){
    $(".vd_body").fadeOut(750);

   });}, 100);
}

function hideLoading
alert("Estoy aca!!!!!");
  setTimeout(function(){$(".vd_body").fadeIn(750, function(){
    $(".page-loading-container").fadeOut(750);

   });}, 100);
}


function applicationInitialized(){
  if(isMessagesLoaded && isSessionLoaded){
    // console.error("On application initialized");
    while(applicationInitializedQueue.length > 0 ){
      var func= applicationInitializedQueue.pop();
      func.call();
    }
    isApplicationInitialized = true;
    hideLoading();
  }


}

function sessionLoaded(){

  if(!isSessionLoaded){
  // console.error("On session loaded");
  while(sessionLoadedQueue.length > 0 ){
    var func= sessionLoadedQueue.pop();
    func.call();
  }
  isSessionLoaded = true;
  applicationInitialized();
  }
}

function messagesLoaded(){
  if(!isMessagesLoaded){
  // console.error("On messages loaded");
  while(messagesLoadedQueue.length > 0 ){
    var func= messagesLoadedQueue.pop();
    func.call();
  }
  isMessagesLoaded = true;
  applicationInitialized();
  }
}

angular.module('web').factory('lifeCycleHelper', function () {
    var lifeHelper = {};

    lifeHelper.onMessagesLoaded = onMessagesLoaded;
    lifeHelper.onSessionLoaded = onSessionLoaded;
    lifeHelper.onApplicationInitialized = onApplicationInitialized;

    lifeHelper.applicationInitialized = applicationInitialized;
    lifeHelper.sessionLoaded = sessionLoaded;
    lifeHelper.messagesLoaded = messagesLoaded;

    return lifeHelper;
});

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

angular.module('web').run(function($rootScope, $location, $cookieStore, $http, loginService, $localStorage, $q, uiHelper) {

    $rootScope.safeApply = function(fn) {
      alert("Estoy en safeAply!!!!");
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

    function checkLogin() {
      var restrictedPage = $.inArray($location.path(), ['/login', '/logout']) === -1;
      var isAdminPage = $location.path().indexOf('/administration')>=0;

      $location.path("/");

    //   loginService.refreshToken().then(function(currentUser){
    //     sessionLoaded();
    //     var userIsAdmin = currentUser.roles.indexOf("ROLE_ADMIN") >= 0;
    //
    //   if(!restrictedPage || !userIsAdmin && isAdminPage ){
    //     $location.path("/");
    //   }
    // }, function(){
    //     isSessionLoaded = false;
    //     loginService.logout();
    //
    //     if(!restrictedPage){
    //       hideLoading();
    //     }
    //     else{
    //     $location.path("/login");
    //     }
    // });
  }


    $rootScope.$on('$locationChangeStart', function(event, next, current) {
      alert("Estoy aca!!!!!!!! en onCheckin");
      var restrictedPage = $.inArray($location.path(), ['/login', '/logout']) === -1;
      if(restrictedPage && !isApplicationInitialized){
          event.preventDefault();
          // Keep track of which location the user was about to move to.
          var targetPath = $location.path();
          var targetSearch = $location.search();
          var targetHash = uiHelper.generateUUID();

         onApplicationInitialized(function(){
          checkLogin();
           $location
               .path( targetPath )
               .search( targetSearch )
               .hash( targetHash )
           ;
         });

          //  loginService.refreshToken().then(sessionLoaded, function(){
          //    $location.path( "/login" ).hash( targetHash );
          //  });
      }
      else{
        checkLogin();
      }

    });

    setInterval(checkLogin, 5000);
});
