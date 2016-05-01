var firstLoad = true;
angular.module('web').factory('loginService',function($q, $http, $cookieStore, $rootScope, $timeout, $localStorage, uiHelper) {

	// Private functions

	function clearAuthenticationData() {
			$rootScope.globals = {};
			$localStorage.$reset();
			$http.defaults.headers.common.Authorization = '';
	}

	function addAuthenticationData(authData) {
			$rootScope.globals = {
					currentUser: authData,
					datetime: new Date(),
					expiresIn : authData.expires_in
			};

			$localStorage.globals = $rootScope.globals;

			refreshAuthorizationToken();


	}

	// Public functions

	function login(username, password) {
			clearAuthenticationData();
			var deferred = $q.defer();
			var url = uiHelper.getURI("api/login");
			var request = {
					"username": username,
					"password": password
			};
			$http.post(url, request)
					.success(function(data, status, headers, config) {
							addAuthenticationData(data);
							fillInitialData(function(){


									deferred.resolve(data);
							});

					})
					.error(function(data, status, headers, config) {
							deferred.reject({
									data: data,
									status: status
							});
					});
			return deferred.promise;
	}

	function logout() {
			var deferred = $q.defer();
			try{
				clearAuthenticationData();
				deferred.resolve();
			}
			catch(ex){
				deferred.reject(ex);
			}

			return deferred.promise;
	}
	function refreshAuthorizationToken(){
		if(getCurrentUser().token_type){
				$http.defaults.headers.common['Authorization'] = getCurrentUser().token_type + ' ' + getCurrentUser().access_token;
				return true;
		}
		else{
			return false;
		}

	}
	function refreshToken() {

			var deferred = $q.defer();
			if (sessionEmpty()) {
					deferred.reject();
			} else if (sessionExpired()) {

					var url = uiHelper.getURI("oauth/access_token");
					url += "?grant_type=refresh_token&refresh_token=" + getCurrentUser().refresh_token;

					$http.post(url, {})
							.success(function(data, status, headers, config) {
									addAuthenticationData(data);
									fillInitialData(function(){
											deferred.resolve(getCurrentUser());
									});
							})
							.error(function(data, status, headers, config) {
									deferred.reject({
											data: data,
											status: status
									});
							});
			} else {
					refreshAuthorizationToken();

					if(!firstLoad){
						deferred.resolve(getCurrentUser());
					}
					else{

						fillInitialData(function(){
								deferred.resolve(getCurrentUser());
						});
					}

			}

			return deferred.promise;
	}


	function sessionExpired() {

			var difference = new Date().getTime() - new Date($localStorage.globals.datetime).getTime();

			var secondsDifference = Math.floor(difference / 1000);

			// We are gonna make session expire 100 seconds before the max time
			return secondsDifference >= ($localStorage.globals.expiresIn - 100);
	}

	function sessionEmpty() {
			var result =  $localStorage.globals === undefined || $localStorage.globals.currentUser === undefined || $localStorage.globals.datetime === undefined;

			return result;
	}

	function getCurrentUser() {
			if (sessionEmpty()) {
					return {};
			}

			return $localStorage.globals.currentUser;
	}

	var loginService = {};

	loginService.login = login;
	loginService.logout = logout;
	loginService.refreshToken = refreshToken;
	loginService.sessionExpired = sessionExpired;
	loginService.sessionEmpty = sessionEmpty;
	loginService.getCurrentUser = getCurrentUser;

	return loginService;

});
