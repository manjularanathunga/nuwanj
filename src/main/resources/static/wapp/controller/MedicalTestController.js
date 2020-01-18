app.controller('MedicalTestController', function ($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Medical Test Setup";
    $scope.uicompo = {};

    $scope.medicalTestList = [];
    $scope.mtest = {};
    $scope.heading = 'Edit Medical Test Details';
    $scope.itemDisabled = false;
    $scope.actionType = '';
    $scope.uicompo.modalpagetitle = 'MEDICAL TEST';
    $scope.uicompo.modalScreen = 'Medical Test';
    $scope.uicompo.search = '';
    $scope.uicompo.ref = {};
    $scope.uicompo.refLst = [];

    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.showUI = function (itm, opType) {
        $scope.actionType = opType;
        if ('add' === $scope.actionType) {
            $scope.heading = 'Add Medical Test Details';
            $scope.itemDisabled = false;
            $scope.mtest = {};
        } else if ('edit' === $scope.actionType) {
            $scope.heading = 'Edit Medical Test Details';
            $scope.itemDisabled = false;
            $scope.mtest = itm;
            $scope.mtest.dateOfBirth = new Date(itm.dateOfBirth);
        } else if ('delete' === $scope.actionType) {
            $scope.heading = 'Delete Medical Test Details';
            $scope.itemDisabled = true;
            $scope.mtest = itm;
        }
        $("#modal-inv").modal("show");
    };

    $scope.saveModal = function () {
        $scope.mtest.lastModified = new Date();
        $scope.mtest.actionBy = loggedUser;

        if ('add' === $scope.actionType) {
            $scope.mtest.dateCreated = new Date();
            $scope.mtest.status = 'ACTIVE';
        } else if ('edit' === $scope.actionType) {
            $scope.mtest.status = 'ACTIVE';

        } else if ('delete' === $scope.actionType) {
            $scope.mtest.status = 'DELETED';
        }

        $http.post('/medicaltest/save', $scope.mtest).then(function (response) {
            loadList();
            $("#modal-inv").modal("hide");
            Pop.msgWithButton($scope.uicompo.modalScreen + ' Record Saved Successfully', $scope.uicompo.modalpagetitle , 'success');
        }, function (response) {
            Pop.msgWithButton('Error Saving ' + $scope.uicompo.modalScreen, $scope.uicompo.modalpagetitle , 'error');
        });
    };

    var searchByName = function(strName){
        $http.get("medicaltest/getListByName?name=" + strName)
            .then(function(jsn) {
                $scope.medicalTestList = jsn.data.response;
            }, function(response) {
            }).catch(function() {
            //Pop.timeMsg('error', 'ADDED PATIENT', ' PATIENT SAVING NOT SUCCESS ' + e, 3000);
        });
    }

    $scope.keypressId = function(e) {
        if (e.keyCode == 13) {

            if ($scope.uicompo.search.length > 0) {
                searchByName($scope.uicompo.search);
            }

            if($scope.medicalTestList.length == 0){
                Pop.timeMsg('error', 'SEARCH MEDICAL TEST', ' TEST NOT FOUND ' + e, 2000);
            }
        }

        if ($scope.uicompo.search.length > 2) {
            searchByName($scope.uicompo.search);
            if($scope.medicalTestList.length == 0){
                Pop.timeMsg('error', 'SEARCH MEDICAL TEST', ' TEST NOT FOUND ' + e, 2000);
            }
        }
    }

    var loadList = function () {
        $http.get("medicaltest/getList").then(function (jsn) {
            $scope.medicalTestList = jsn.data.response.content;
        });
    };

    $scope.addReference = function() {
        $scope.uicompo.ref.medicalTest = $scope.mtest.id;
        var refItem = $scope.uicompo.ref;
        console.log(JSON.stringify(refItem))
        $scope.uicompo.refLst.push(refItem);
        $scope.uicompo.ref = {};
    }

    $scope.loadBulkData = function () {
        $http.get("medicaltest/loadBulk").then(function (response) {
            loadList();
        });
    };
    loadList();
});
