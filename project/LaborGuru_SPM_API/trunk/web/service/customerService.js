angular.module('web').factory('customerService',function($q, $http) {

	function getCustomers() {
        var deferred = $q.defer();
        var url = "http://localhost:8080/spm/api/customer";
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

		function loadCustomer(customerId) {
			var deferred = $q.defer();
			var url = "http://localhost:8080/spm/api/customer/" + customerId;

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

		function removeCustomer(customerId) {
			var deferred = $q.defer();
			var url = "http://localhost:8080/spm/api/customer/" + customerId;
			$http.delete(url)
				.success(function(data, status, headers, config){
						deferred.resolve(data);
				})
				.error(function(data,status,headers,config){
						deferred.rejected({
								data: data,
								status: status
						});
				});

				return deferred.promise;
		}

	var customerService = {};
	customerService.getCustomers = getCustomers;
	customerService.loadCustomer = loadCustomer;
	customerService.removeCustomer = removeCustomer;
	return customerService;
});
