angular.module('web').controller('StoreListCtrl',function($scope,$modal,storeService, $location, $rootScope, $window,$log){

  var vm = $scope;
  vm.dataLoading = false;
  vm.rowCollection = [];
  vm.itemsByPage = 20;

  storeService.getStores()
  				       .then(function(res){
                   vm.rowCollection = res;
                 },function(res){
                   $log.log(res.data || "Request Failed");
                 }
               );

  vm.showStore = function showStore(showDetailsPath){
    $location.path( showDetailsPath );
  };

});
