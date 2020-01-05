app.controller('PatientController', function ($scope, $rootScope, $http, $location, $window) {
    $rootScope.pageTitle = "Patient Setup";

    $scope.patientList = [];
    $scope.patient = {};
    $scope.heading = 'Edit Patient Details';
    $scope.itemDisabled = false;
    $scope.actionType = '';
    $scope.genderLst = ['MALE', 'FEMALE'];

    var loggedUser = '-';
    if ($rootScope.globals && $rootScope.globals.currentUser) {
        loggedUser = $rootScope.globals.currentUser.username;
    }

    $scope.showUI = function (itm, opType) {
        $scope.actionType = opType;
        if ('add' === $scope.actionType) {
            $scope.heading = 'Add Patient Details';
            $scope.itemDisabled = false;
            $scope.patient = {};
            $scope.patient.status = 'ACTIVE';
        } else if ('edit' === $scope.actionType) {
            $scope.heading = 'Edit Patient Details :' + itm.id;
            $scope.itemDisabled = false;
            $scope.patient = itm;
            $scope.patient.dateOfBirth = new Date(itm.dateOfBirth);
        } else if ('delete' === $scope.actionType) {
            $scope.heading = 'Delete Patient Details :' + itm.id;
            $scope.itemDisabled = true;
            $scope.patient = itm;
        }
        $("#modal-inv").modal("show");
    };

    $scope.saveModal = function () {
        $scope.patient.lastModified = new Date();
        $scope.patient.actionBy = loggedUser;

        if ('add' === $scope.actionType) {
            $scope.patient.dateCreated = new Date();
            $http.post('/patient/save', $scope.patient).then(function (response) {
             }, function (response) {
            });
        } else if ('edit' === $scope.actionType) {
            $scope.patient.status = 'ACTIVE';
            $http.post('/patient/save', $scope.patient).then(function (response) {
             }, function (response) {
            });
        } else if ('delete' === $scope.actionType) {
            $http.delete('/patient/delete?id='+$scope.patient.id).then(function (response) {
             }, function (response) {
            });
        }
        loadList();
    };


    $scope.loadBulkData = function () {
        $http.get("patient/loadPatient").then(function (response) {
            loadList();
        });
    };

    var loadList = function () {
        $http.get("patient/getList").then(function (response) {
            $scope.patientList = response.data.content;
        });
    };
    loadList();
});
