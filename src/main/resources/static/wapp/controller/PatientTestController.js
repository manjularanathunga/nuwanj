app.controller('PatientTestController', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Patient Test Page";
    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.patientMediTestList = [];
    $scope.uicompo = {};
    $scope.uicompo.billingNumber = "";
    $scope.uicompo.patientId = "";

    $scope.loadByBillingNum = function() {
    var res = $http.get("patientmedicaltest/findAllActiveByBillingNumber?billingNumber=" + $scope.uicompo.billingNumber)
        .then(function(response) {
            if (response.data.success) {
                var dList = response.data.response;
                $scope.patient = dList[0];
                $scope.patientMediTestList = dList[1];
            } else {
                $scope.patientMediTestList = [];
            }
        }, function(response) {

        });
    }

    $scope.loadByPatientId = function() {
        var res = $http.get("patientmedicaltest/findAllByBillingNumber?billingNumber=" + $scope.uicompo.billingNumber)
            .then(function(response) {
                if (response.data.success) {
                    $scope.patientMediTestList = response.data.response;
                } else {
                    $scope.patientMediTestList = [];
                }
            }, function(response) {

            });
    }
});

