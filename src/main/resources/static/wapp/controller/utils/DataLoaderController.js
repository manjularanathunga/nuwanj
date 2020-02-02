app.controller('DataLoaderController', function ($scope, $rootScope, $http, $location, $window, Pop) {
    $rootScope.pageTitle = "DataLoader";

    $scope.patientList = [];

    $scope.patientLoader = function () {
        $http.get("loader/loadPatient").
        then(function (response) {

        });
    };
});
