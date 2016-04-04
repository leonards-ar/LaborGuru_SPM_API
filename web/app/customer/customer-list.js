angular.module('web').controller('CustomerListCtrl',function($scope,$modal,customerService, $location, $rootScope, $window,$log){

var vm = $scope;
vm.dataLoading = false;
vm.customerList = {};

customerService.getCustomers()
				       .then(function(res){
                 vm.customerList = res;
               },function(res){
                 $log.log(res.data || "Request Failed");
               }
             );

// customerservice.getCustomers().then(function(response){
//   vm.customerList = response.data;
// });

vm.showCustomer = function(customerId){
  var promise = customerService.loadCustomer(customerId);
  promise.then(function(customer){

    var modalCustomerInfo = $modal.open({
          animation: true,
          templateUrl: 'app/customer/customer-info.html',
          controller: 'CustomerInfoCtrl',
          size: "lg",
          resolve: {
            showInfo: function() {
              return {
                "name": customer.name,
                "code": customer.code,
                "regions": customer.regionDto
              };
            }
          }
      });
    },function(res){
      $log.log(res.data || "Request Failed");
    }
    );
  };

  vm.removeCustomer  = function(customerId){
    var promise = customerService.removeCustomer(customerId);
    promise.then(function(){
      vm.message = "Customer deleted";
      $rootScope.showSuccessAlert = true;
    });
  };

});
