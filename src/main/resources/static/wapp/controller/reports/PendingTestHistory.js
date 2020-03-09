app.controller('PendingTestHistory', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Pending Test History Report";

    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }
    $scope.medicalTestList = [];
    $scope.uicompo = {};
    $scope.uicompo.patientId='';
    $scope.uicompo.rest = {};
    $scope.patientHistoryLst = [];

    var loadList = function () {
        $http.get("medicaltest/getIdNameList").then(function (jsn) {
            $scope.medicalTestList = jsn.data;
        });
    };

    loadList();
});