app.controller('ScanningController', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Patient Medical Scanning";
    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }
});