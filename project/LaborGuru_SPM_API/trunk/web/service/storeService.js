angular.module('web').factory('storeService',function($q, $http) {

	function getStores() {
        var deferred = $q.defer();
        var url = "http://localhost:8080/spm/api/store";
        // var request = {
        //     params: {
        //         query: query,
        //         page: page,
        //         size: size
        //     }
        // };
        $http.get(url)
            .success(function(data, status, header, config) {
                deferred.resolve(data);
            })
            .error(function(data, status, headers, config) {
                deferred.reject({
                    data: data,
                    status: status
                });
            });
        return deferred.promise;
    }

		function loadStore(storeId) {
			var deferred = $q.defer();
			var url = "http://localhost:8080/spm/api/store/" + storeId;

			$http.get(url)
					.success(function(data, status, headers, config){
							deferred.resolve(data);
					})
					.error(function(data, status, headers, config){
						deferred.rejected({
							data: data,
							status: status
						});
					});

					return deferred.promise;
		}

		function loadHoursOfOperation(storeId){
			var deferred = $q.defer();
			var url = "http://localhost:8080/spm/api/store/" + storeId + "/operationTimes";

			$http.get(url)
					.success(function(data, status, headers, config){
							deferred.resolve(data);
					})
					.error(function(data, status, headers, config){
						deferred.rejected({
							data: data,
							status: status
						});
					});

					return deferred.promise;
		}

	var storeService = {};
	storeService.getStores = getStores;
	storeService.loadStore = loadStore;
	storeService.loadHoursOfOperation = loadHoursOfOperation;

	return storeService;
});
