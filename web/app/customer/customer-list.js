angular.module('web').controller('CustomerListCtrl',function($scope,$http,customerService, $location, $window,$log){

var vm = $scope;
vm.dataLoading = false;
vm.customerList = {};

$log.log("!!!!!!Estoy aca !!!!!!!!!!!");
customerService.getCustomers()
				       .then(function(res){
                 $log.log("Successful!!! " + res.length);
				    	   vm.customerList = res;
               },function(res){
                 $log.log(res.data || "Request Failed");
               }
             );

// customerservice.getCustomers().then(function(response){
//   vm.customerList = response.data;
// });



});
