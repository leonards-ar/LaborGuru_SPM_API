angular.module('web').controller('HeaderCtrl',function($scope){
  var vm = $scope;
  
  vm.isAuthenticated = function() {
        return false;
    };

});
