app.controller('PatientMedicalHistory', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Patient Medical History Report";
    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.uicompo = {};
    $scope.uicompo.patientId='';
    $scope.uicompo.rest = {};
    $scope.patientHistoryLst = []

    $scope.loadPatientHistory = function () {
        $http.get("patientmedicaltest/findAllByPatientId?patientId="+$scope.uicompo.patientId)
            .then(function (rsp) {
                //$scope.uicompo.rest = rsp.data;
            if(rsp.data.success){
                $scope.patientHistoryLst = rsp.data.response;
            }else{
                Pop.msgWithButton('Patient Medical History', 'Records Not found', 'warning');
            }
        });
    }

});