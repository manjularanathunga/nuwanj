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
    $scope.scanList = [];
    $scope.scanHistortyList = [];
    $scope.uicompo.itemDisabled = false;
    $scope.uicompo.history = {};
    $scope.uicompo.historyLst = [];
    $scope.currentAge  = 0;
    $scope.uicompo.showItem  = false;
    $scope.uicompo.showSave  = false;
    $scope.uicompo.selectIndicationList = [];
    $scope.uicompo.selectFindingList = [];
    $scope.uicompo.imageurl  = '';

    var resetComponent = function(){
        $scope.patient = {};
        $scope.patientTest = {};
        $scope.scanList = [];
        $scope.scanHistortyList = [];
        $scope.uicompo.showItem  = false;
        $scope.uicompo.showSave  = false;
    }

    $scope.searchBilling = function (type) {
        resetComponent();
        var searchNum ='';
        if("BILLING" == type){
            var billingNumber = $scope.uicompo.billingNumber.trim();
            if(!billingNumber){
                Pop.timeMsg('error', ' SCAN NUMMBER NOT FOUND ', 'SEARCH SCAN', 2000);
                return;
            }
            searchNum = billingNumber;
        }else{
            var scanNumber = $scope.uicompo.scanNumber.trim();
            if(!scanNumber){
                Pop.timeMsg('error', ' SCAN NUMMBER NOT FOUND ', 'SEARCH SCAN', 2000);
                return;
            }
            searchNum = scanNumber;
        }

        var res = $http.get("scan/getPatientByBilling?searchNum=" + searchNum+"&type="+type);
        res.then(function(resp) {
                if (resp.data.success) {
                    var obj = resp.data.response;
                  try{
                        $scope.patientTest = obj.patientTest;
                    }catch (e) {
                        console.log('error retreving patientTest');
                      $scope.patientTest = {};
                    }
                    try{
                        $scope.patient = obj.patient;
                        if($scope.patient.dateOfBirth){
                            $scope.currentAge = (new Date().getFullYear() - new Date($scope.patient.dateOfBirth).getFullYear());
                        }
                    }catch (e) {
                        console.log('error retreving patient');
                        $scope.patient = {};
                    }
                    try{
                        $scope.scanHistortyList = obj.scanHistortyList;
                    }catch (e) {
                        console.log('error retreving scanHistortyList');
                        $scope.scanHistortyList = [];
                    }


                    try{
                        var selected = JSON.parse(obj.ScanOpsionProps);
                        $scope.uicompo.selectIndicationList = selected;
                    }catch (e) {
                        console.log('error retreving ScanOpsionProps');
                        $scope.ScanOpsionProps = {};
                    }

                    $scope.uicompo.showItem  = true;
                    $scope.uicompo.showSave  = false;

                } else {
                    Pop.timeMsg('error', 'SEARCH SCAN', obj.exception, 2000);
                }
            });

    }

    $scope.clearScreen = function() {
        $scope.uicompo = {};
        $scope.patientTest = {};
        $scope.patient = {};
        $scope.scanHistortyList = [];
        $scope.uicompo.showItem  = false;
        $scope.uicompo.showSave  = false;
    }

    $scope.onChangeIndication = function() {
        if($scope.uicompo.selectIndication.findingList){
            $scope.uicompo.selectFindingList = val.findingList;
        }
    }

    $scope.saveModal = function() {

        if($scope.patientTest.scanNumber.includes('#')){
            Pop.timeMsg('warning', 'SAVE SCAN', 'Scan # cannot containg #', 5000);
            return;
        }

        $scope.patientTest.actionBy= loggedUser;
        $scope.patientTest.lastModified = new Date();
        //console.log('patientTest > ' + JSON.stringify($scope.patientTest));
        $http.post('/patientmedicaltest/SaveScan', $scope.patientTest)
            .then(function(resp) {
                if(resp.data.success){
                    Pop.timeMsg('success', 'SAVE SCAN', ' SCAN HAS BEEN SAVED SUCCESSFULLY ', 2000);
                    $scope.uicompo.showSave  = false;
                }else{
                    Pop.timeMsg('error', resp.data.exception, 'SAVE SCAN', 2000);
                }
            }, function(resp) {
                Pop.timeMsg('error', 'SAVE SCAN', ' SCAN SAVING NOT SUCCESS', 2000);
            }).catch(function(e) {
                Pop.timeMsg('error', 'SAVE SCAN', ' SCAN SAVING NOT SUCCESS ' + e, 5000);
        });
    };

    $scope.showImage = function(item) {
        var img_home ='images';//20WB123.jpg
        //var img_home ='/Users/sirimewanranathunga/Desktop/projects/Images';//20WB123.jpg
        $scope.uicompo.imageurl = img_home + '/' + $scope.patientTest.scanNumber+".jpg";
        $("#modal-image").modal("show");
    }

    $scope.showHistory = function(itm) {
        $scope.heading = 'History'
        var selectedBillingNumber = itm.substr(0,itm.indexOf("-"));
        $http.get("scan/getHistoryByPatient?billingNumber=" + selectedBillingNumber)
            .then(function(resp) {
                if (resp.data.success) {
                    $scope.uicompo.history = resp.data.response.prop;
                    $scope.uicompo.historyLst = resp.data.response.propList;
                     $("#modal-scan-history").modal("show");
                } else {
                    Pop.timeMsg('error', 'SEARCH SCAN', ' SCAN NUMBER NOT FOUND ', 2000);
                }
            });
        //$scope.patientTest = ;

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

    $scope.enableSave = function(itm) {
        $scope.uicompo.showSave  = true;
    }

    loadList();
});