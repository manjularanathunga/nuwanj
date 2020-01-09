app.controller('ScanningController', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Patient Medical Scanning";
    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.uicompo = {};
    $scope.patient = {};
    $scope.patientScan = {};
    $scope.scanList = [];
    $scope.scanHistortyList = [];
    $scope.uicompo.itemDisabled = false;

    // $scope.patientScan.dateCreated = new Date();

    var loadList = function () {
        $http.get("scan/getList").then(function (response) {
            $scope.scanHistortyList = response.data;
        });
    };

    var loadList = function () {
        $http.get("scan/getList").then(function (response) {
            $scope.scanList = response.data;
        });
    };
    loadList();
});