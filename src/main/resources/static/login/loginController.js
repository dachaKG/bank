var app = angular.module('login.controllers',[]);


app.controller('loginController',['$scope','loginService','$location',
	function($scope, loginService, $location) {
	
	/*$scope.credentials = function(){
		loginService.getLogin().then(
			function(response){
				return response.data;
			}
		)
	}*/
	
	$scope.error = false;
	$scope.login = function(){
		loginService.login($scope.credentials).then(
			function(response){
				$location.path("/home");
		        $scope.error = false;
			}, function(response){
				$location.path("/login");
		        $scope.error = true;
			}
		)
	}
	
	/*$scope.login = function() {
	      authenticate($scope.credentials, function() {
	        if ($rootScope.authenticated) {
	          $location.path("/home");
	          $scope.error = false;
	        } else {
	          $location.path("/login");
	          $scope.error = true;
	        }
	      });
	  };*/
	
	
}]);