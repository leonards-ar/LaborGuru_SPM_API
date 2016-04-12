angular.module('web').controller('ShowDetailsCtrl',function($scope,$stateParams,$modal,storeService, $location, $rootScope, $window,$log){

  var vm = $scope;
  vm.dataLoading = false;
  vm.storeList = {};
  var storeId = $stateParams.id;

  storeService.loadStore(storeId)
  				       .then(function(res){
                   vm.store = res;
                 },function(res){
                   $log.log(res.data || "Request Failed");
                 }
               );

vm.showOperationTime = function (storeId){
  var promise = storeService.loadHoursOfOperation(storeId);
  promise.then(function(data){

    var modalCustomerInfo = $modal.open({
          animation: true,
          templateUrl: 'app/store/details/show-operationTime.html',
          controller: 'ShowOperationtimeCtrl',
          size: "lg",
          resolve: {
            showInfo: function() {
              return {
                "storeId": data.store.id,
                "client": data.store.clientName,
                "number": data.store.code,
                "name": data.store.name,
                "firstDayOfWeek": data.firstDayOfWeek,
                "openHours": data.openHour,
                "closeHours": data.closeHours,
                "openingExtraHours": data.openingExtraHours,
                "closingExtraHours": data.closingExtraHours
              };
            }
          }
      });
    },function(res){
      $log.log(res.data || "Request Failed");
    }
  );

};

});
