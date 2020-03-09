var app = angular.module('app', ['ngRoute', 'ngCookies'])
    .run(['$rootScope', '$location', '$window', '$http', '$cookies', function ($rootScope, $location, $window, $http, $cookies) {


        $rootScope.currentUserId = $window.localStorage.getItem('mdbUserId') || false;
        $rootScope.mdbRole = $window.localStorage.getItem('mdbRole') || false;
        $rootScope.authdata = $window.localStorage.getItem('mdbAuthData') || false;
        $rootScope.mdbloggedUser = $window.localStorage.getItem('mdbloggedUser');

        $rootScope.presentDate = new Date();
        $rootScope.mainTitle = "Medical Data Analysis System";
        $rootScope.loggedUser = $window.localStorage.getItem('mdbloggedUser');
        $rootScope.pageTitle = "Login";

        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                //$location.path('/login');
            }
        });


    }])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when("/login", {
                templateUrl: 'wapp/view/admin/loginPage.html',
                controller: 'LoginController'
            })
            .when("/dashboard", {
                templateUrl: 'wapp/view/dashboardPage.html',
                controller: 'DashboardController'
            })
            .when("/useradmin", {
                templateUrl: 'wapp/view/admin/userAdminPage.html',
                controller: 'UserAdminController'
            })
            .when("/patient", {
                templateUrl: 'wapp/view/medical/patientPage.html',
                controller: 'PatientController'
            })
            .when("/logout", {
                templateUrl: 'wapp/view/admin/logoutPage.html',
                controller: 'LogoutController'
            })
            .when("/billing", {
                templateUrl: 'wapp/view/billingPage.html',
                controller: 'BillingController'
            })
            .when("/meditest", {
                templateUrl: 'wapp/view/medical/medicalTestPage.html',
                controller: 'MedicalTestController'
            })
            .when("/labresults", {
                templateUrl: 'wapp/view/lab/labResultsPage.html',
                controller: 'LabResultsController'
            })
            .when("/patienttest", {
                templateUrl: 'wapp/view/reports/patientTestPage.html',
                controller: 'PatientTestController'
            })
            .when("/dailypatientreport", {
                templateUrl: 'wapp/view/reports/dailyPatientPage.html',
                controller: 'DailyPatientController'
            })
            .when("/screenaccess", {
                templateUrl: 'wapp/view/admin/screenAccessPage.html',
                controller: 'ScreenAccessController'
            })
            .when("/userscreen", {
                templateUrl: 'wapp/view/admin/userScreenPage.html',
                controller: 'UserScreenController'
            })
            .when("/scanning", {
                templateUrl: 'wapp/view/medical/scanningPage.html',
                controller: 'ScanningController'
            })
            .when("/daterange", {
                templateUrl: 'wapp/view/analysis/DateRangeAnalysisPage.html',
                controller: 'DateRangeAnalysisController'
            })
            .when("/dataLoader", {
                templateUrl: 'wapp/view/utils/dataLoader.html',
                controller: 'DataLoaderController'
            })
            .when("/patientMedicalHistoryReport", {
                templateUrl: 'wapp/view/reports/patientMedicalHistory.html',
                controller: 'PatientMedicalHistory'
            })
            .when("/pendingTestHistory", {
                templateUrl: 'wapp/view/reports/pendingTestHistory.html',
                controller: 'PendingTestHistory'
            })



    }]);

