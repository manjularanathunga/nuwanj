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
    $scope.patientMediTestList = [];

    var loadList = function () {
        $http.get("medicaltest/getIdNameList").then(function (jsn) {
            $scope.medicalTestList = jsn.data;
        });
    };

    $scope.loadByTest = function() {
        $scope.uicompo.showContent = false;
        var res = $http.get("patientmedicaltest/getAllByTestNumberOrderByPriority?testNumber=" + $scope.uicompo.selectedTestId)
            .then(function(rsp) {
                if (rsp.data.success) {
                    $scope.patientMediTestList = rsp.data.response;
                } else {
                    $scope.patientMediTestList = [];
                    $scope.uicompo.showContent = false;
                    Pop.timeMsg('success', response.data.exception, '', 2000);
                }
            }, function(response) {
                $scope.uicompo.showContent = false;
            });
    }

    $scope.getRandomColor = function(item) {
        if(item.status == 'OPEN'){
            return {
                "color" : "white",
                "background-color" : "red"
            };
        }else{
            return {

            };
        }
    }

    loadList();
});