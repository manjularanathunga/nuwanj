app.controller('ScanningController', function($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Patient Medical Scanning";
    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.heading = '';
    $scope.uicompo = {};
    $scope.patient = {};
    $scope.patientTest = {};
    $scope.patientScan = {};
    $scope.scanList = [];
    $scope.scanHistortyList = [];
    $scope.uicompo.itemDisabled = false;

    // $scope.patientScan.dateCreated = new Date();

    var resetComponent = function(){
        $scope.patient = {};
        $scope.patientTest = {};
        $scope.patientScan = {};
        $scope.scanList = [];
        $scope.scanHistortyList = [];
    }

    $scope.searchBilling = function () {
        resetComponent();
        var billingNumber = $scope.uicompo.billingNumber;
        if(!billingNumber){
            Pop.timeMsg('success', 'SEARCH SCAN', ' SCAN NUMMBER NOT FOUND ', 2000);
            return;
        }

        $http.get("scan/getPatientByBilling?billingNumber=" + billingNumber)
            .then(function(resp) {
                if (resp.data.success) {
                    var resLst = resp.data.response;
                    $scope.patientTest = resLst[0];
                    $scope.patient = resLst[1];
                    $scope.scanHistortyList = resLst[2];
                    $scope.patientScan.scanNumber = $scope.patientScan.scanNumber;
                    $scope.patientScan.billingNumber = billingNumber;
                    $scope.patientScan.patientId = $scope.patient.patientId;
                    if(!$scope.patient.dateOfBirth){
                        $scope.patient.currentAge = (new Date().getFullYear() - $scope.patient.dateOfBirth.getFullYear());
                    }
                    //$scope.patientScan.procedure;
                    //$scope.patientScan.indication;
                    //$scope.patientScan.finding;
                    //$scope.patientScan.impression;
                    //$scope.patientScan.remarks;

                } else {
                    Pop.timeMsg('error', 'SEARCH SCAN', ' SCAN NUMMBER NOT FOUND ', 2000);
                }
            });

    }

    $scope.saveModal = function() {
        $scope.patientScan.actionBy= loggedUser;
        $scope.patientScan.lastDateModified = new Date();
        $scope.patientScan.dateCreated = new Date();
        $scope.patientScan.status = 'ACTIVE';
        $http.post('/scan/save', $scope.patientScan)
            .then(function(resp) {
                Pop.timeMsg('success', 'SAVE SCAN', ' SCAN HAS BEEN SAVED SUCCESSFULLY ', 2000);
            }, function(resp) {
                Pop.timeMsg('error', 'SAVE SCAN', ' SCAN SAVING NOT SUCCESS', 2000);
            }).catch(function(e) {
            Pop.timeMsg('error', 'SAVE SCAN', ' SCAN SAVING NOT SUCCESS ' + e, 5000);
        });
    };

    $scope.showImage = function() {
        Pop.timeMsg('success', 'SAVE SCAN', 'showImage', 2000);
    }

    $scope.showHistory = function(itm) {
        $scope.heading = 'Scan history'
        $("#modal-scan-history").modal("show");
    }

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