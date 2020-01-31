app.controller('DateRangeAnalysisController', function ($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Data Analysis Based on Date Range";

    $scope.statics = {};
    $scope.statics.genderbytest = [];
    $scope.testList = [];
    $scope.statics.testLst = [];

    var onLoad = function () {
        var chart = c3.generate({
            bindto: '#chart',
            data: {
                columns: $scope.statics.genderbytest,
                type: 'pie'
            }
        });
    }

    $scope.genderStaticsByTest = function () {
        var res = $http.get("analysis/genderStaticsByTest?testid=12")
            .then(function (response) {
                $scope.statics.genderbytest = response.data;
                onLoad();
            }, function (response) {

            }).catch(function () {

            });
    }

});


/*        c3.generate({
            bindto: '#chart',
            data: {
                columns: [
                    ['data1', 30, 200, 100, 400, 150, 250],
                    ['data2', 50, 20, 10, 40, 15, 25]
                ],
                axes: {
                    data2: 'y2'
                }
            },
            axis: {
                y: {
                    label: { // ADD
                        text: 'Y Label',
                        position: 'outer-middle'
                    }
                },
                y2: {
                    show: true,
                    label: { // ADD
                        text: 'Y2 Label',
                        position: 'outer-middle'
                    }
                }
            }
        });*/
