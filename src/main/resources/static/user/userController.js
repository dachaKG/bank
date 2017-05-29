var app = angular.module('user.controllers',[]);

app.controller('userController',['$scope','userService','$location',
	function($scope, userService,$location) {
	
		$scope.registerUser = function(){
			userService.registerUser($scope.user).then(
				function(response){
					
				}, function(response){
					alert("greska pri registraciji");
				}
			)
		}
		
		$scope.changeAndCheckPassword = function(){
			userService.changePassword($scope.changePassword).then(
				function(response){
					if(response.data == "true"){
						alert("uspesno izvrsena promena lozinke")
						$location.path("/home");
					} else {
						alert("nije tacna stara lozinka");
					}
				}, function(response){
					alert("greska pri izmeni lozinke");
				}
			)
		}
		
		/*$scope.logout = function(){
			userService.logout().then(
				function(response){
					$location.path("/logout");
				}, function(response){
					alert("Greska pri logout-u");
				}
			)
		}*/

}])