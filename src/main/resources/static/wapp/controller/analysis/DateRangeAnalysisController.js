app.controller('DateRangeAnalysisController', function ($scope, $rootScope, $http, $location, $window, AuthenticationService, Pop) {
    $rootScope.pageTitle = "Data Analysis Based on Date Range";

    $scope.statics = {};
    $scope.statics.genderbytest = [];
    $scope.statics.genderbypatient = [];
    $scope.testList = [];
    $scope.statics.testLst = [];
    $scope.medicalTestList = [];
    $scope.medicalTestGraphList = [];
    $scope.uicompo = {};
    $scope.uicompo.selectedTestId = "";

    var testStatics = function () {
        c3.generate({
            bindto: '#testChart',
            data: {
                columns: $scope.statics.genderbytest,
                type: 'pie'
            },
            pie: {
            label: {
                format: function(value, ratio, id) {
                    ratio = d3.format("%")(ratio); // format ratio
                    //return [id, value, ratio].join(); // used to pass values to the onrender function
                    return value + " - " + ratio;

                }
            }
        }
        });
    }

    var patientStatics = function () {
        c3.generate({
            bindto: '#patientChart',
            data: {
                columns: $scope.statics.genderbypatient,
                type: 'pie'
            },
            pie: {
                label: {
                    format: function(value, ratio, id) {
                        ratio = d3.format("%")(ratio); // format ratio
                        //return [id, value, ratio].join(); // used to pass values to the onrender function
                        return value + " - " + ratio;

                    }
                }
            }
        });
    }

    var patientGraph = function () {

        var chart = c3.generate({
            bindto: '#patientGraph',
            data: {
                columns:  $scope.medicalTestGraphList,
                type: 'bar'
            },
            bar: {
                width: {
                    ratio: 0.5 // this makes bar width 50% of length between ticks
                }
                // or
                //width: 100 // this makes bar width 100px
            }
        });
    }

    $scope.genderStatics = function () {
        var res = $http.get("analysis/genderStatics")
            .then(function (response) {
                $scope.statics.genderbytest = response.data;
                testStatics();
            }, function (response) {

            }).catch(function () {

            });
    }

    $scope.genderStaticsByPatient = function () {
        var res = $http.get("analysis/genderStaticsByPatient?testid=12")
            .then(function (response) {
                $scope.statics.genderbypatient = response.data;
                patientStatics();
            }, function (response) {

            }).catch(function () {

            });
    }

    $scope.onChangeTestName = function (key) {
        var res = $http.get("analysis/genderStaticsByTest?testid=" + $scope.uicompo.selectedTestId)
        .then(function (response) {
            $scope.statics.genderbypatient = response.data;
            patientStatics();
        }, function (response) {

        }).catch(function () {

        });
    }

    var loadList = function () {
        $http.get("medicaltest/getIdNameList").then(function (jsn) {
            $scope.medicalTestList = jsn.data;
        });
    };

    var loadGraph = function () {
        $http.get("analysis/staticGraph").then(function (jsn) {
            $scope.medicalTestGraphList = jsn.data;
            patientGraph();
        });
    };


    $scope.genderStatics();
    $scope.genderStaticsByPatient();
    loadGraph();
    loadList();


});


/*        */

/*var charThree = c3.generate({
    bindto: "#chartThree",
    size: {
        width: 500,
        height: 300
    },
    data: {
        colors: {
            A: 'yellow',
            B: 'red',
            C: 'green',
            D: 'orange',
            E: 'blue'
        },
        columns: [
            ['A',20],
            ['B',40],
            ['C',20],
            ['D',10],
            ['E',9]
        ],
        type: 'pie'
    },
    pie: {
        labels: {
            show: true,
            threshold: 0.1,
            format: {
                A: function (value, ratio, id) {
                    if(value=20) {
                        return "A<br/>9item<br/>20.2%";
                    }
                }
            }
        }
    }

});*/
