var app = angular.module("VendorOnbardingManagment", ['ui.bootstrap', 'datatables', 'ngRoute']);

app.config(['$locationProvider', function ($locationProvider) { $locationProvider.html5Mode({ enabled: true, requireBase: false }); }])
app.factory('Excel', function ($window) {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function (s) { return $window.btoa(unescape(encodeURIComponent(s))); },
        format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) };
    return {
        tableToExcel: function (tableId, worksheetName) {
            var table = $(tableId),
                ctx = { worksheet: worksheetName, table: table.html() },
                href = uri + base64(format(template, ctx));
            return href;
        }
    };
})



//Vendor Register Onboarding module controller 
app.controller("VendorRegisterOnboardingController", function ($scope, $http, Excel, $timeout, $location) {

    $scope.form = {};
    $scope.views = {};
    $scope.invitee = $location.search().invitee;
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            //console.log(response);
            $scope.locations = response.data;
            $scope.form.invitee = $scope.invitee;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchLocationsDetailsById = function (id) {
        $http({
            method: 'GET',
            params: { 'name': id },
            url: 'api/master/getLocationByName'

        }).then(function successCallback(response) {
            // console.log(response);
            $scope.location_details = response.data;
            $scope.form.countrycode = $scope.location_details.countryId;
            $scope.fetchState($scope.form.countrycode);
            $scope.form.pin = $scope.location_details.pincode;
            $scope.form.stateid = $scope.location_details.stateId;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchCountry = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCountry'

        }).then(function successCallback(response) {

            $scope.countries = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchState = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getStateByCountryCode'

        }).then(function successCallback(response) {

            $scope.states = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchPaymentMode = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getPaymentmode'

        }).then(function successCallback(response) {

            $scope.payment_modes = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.fetchLocations();
    $scope.fetchCountry();
    $scope.fetchPaymentMode();

    $scope.addSaveVendorRegOnboarding = function () {
        if ($scope.form.gst_no !== undefined && $scope.form.cont_pers !== undefined && $scope.form.cont_mobile !== undefined) {

            console.log($scope.form);
            showHideLoad();
            var method = "POST";
            var url = 'api/vendor/postVendorRegistrationOnboarding';

            $http({
                method: method,
                url: url,
                data: angular.toJson($scope.form),
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: angular.identity
            }).then(_success, _error);
        }
        else {
            console.log("Required field Missing");
        }
    };
    function _success(response) {
        showHideLoad(true);
        // console.log(response);
        $scope.form = {};
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        // console.log(response);
        showHideLoad(true);
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.checkUsername = function (username) {
        // console.log(username);
        if (username != undefined) {

            showHideLoad();
            $http({
                method: 'GET',
                params: { 'username': username },
                url: 'CheckUsername'

            }).then(function successCallback(response) {

                $scope.username_status = response.data;
                // console.log($scope.username_status);
                if ($scope.username_status == false) {
                    $('#username_msg').hide();
                } else {
                    $('#username_msg').show();
                }
                showHideLoad(true);
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }
    }

    $scope.CheckUserEmail = function (email) {
        // console.log(email);
        if (email != undefined) {

            showHideLoad();
            $http({
                method: 'GET',
                params: { 'email': email },
                url: 'CheckUserEmail'

            }).then(function successCallback(response) {

                $scope.email_status = response.data;
                console.log($scope.email_status);
                if ($scope.email_status == false) {
                    $('#email_msg').hide();
                } else {
                    $('#email_msg').show();
                }
                showHideLoad(true);
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }
    }

    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});