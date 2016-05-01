angular.module('web').controller('ShowOperationtimeCtrl',function($scope, showInfo){

var vm = $scope
vm.client = showInfo.client;
vm.number = showInfo.number;
vm.name = showInfo.name;
vm.firstDayOfWeek = showInfo.firstDayOfWeek;

});
