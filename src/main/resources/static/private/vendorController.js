var app = angular.module("VendorManagment", ['ui.bootstrap', 'datatables']);

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

// DIRECTIVE - FILE MODEL
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };

}]);

//Invite Vendor module controller 
app.controller("InviteVendorController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoInviteVendorListFetch();
    function autoInviteVendorListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorInvitation'

        }).then(function successCallback(response) {
            console.log("++++++++++++++++++++++++");
            console.log(response);
            $scope.invitations = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddInviteVendor = function () {
        $scope.form = {};
        $('#add_invite_email_modal').modal('show');
    }
    $scope.addSaveVendorInvitation = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/vendor/postVendorInvitation';
        } else {
            var method = "PUT";
            var url = 'api/vendor/updateVendorInvitation';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_invite_email_modal').modal('hide');
        autoInviteVendorListFetch();
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
        console.log(response);
        showHideLoad(true);
        $('#add_invite_email_modal').modal('hide');
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

    $scope.editVendorInvitation = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorInvitationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_invite_email_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteBrand = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Invitation!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/vendor/deleteBrand';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoBrandListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Vendor Register module controller 
app.controller("VendorRegisterController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoVendorRegisterListFetch();
    function autoVendorRegisterListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorRegistration'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.RegVendor = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $scope.fetchLocations();
        // $scope.fetchLocationsDetailsById();
        $scope.fetchCountry();
        $scope.fetchPaymentMode();
        $scope.fetchFileUploadListForAdd('V');
        $scope.fetchState(103);
        $scope.changeView('add');
    }
    $scope.addSaveVendorReg = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/vendor/postVendorRegistration';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoVendorRegisterListFetch();
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
        console.log(response);
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
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchLocationsDetailsById = function (id) {
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getLocationById'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.location_details = response.data;
            $scope.form.countrycode = $scope.location_details.countryId;
            $scope.form.stateid = $scope.location_details.stateId;
            $scope.form.pin = $scope.location_details.pincode;
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
    $scope.checkUsername = function (username) {
        console.log(username);
        if (username != undefined) {

            showHideLoad();
            $http({
                method: 'GET',
                params: { 'username': username },
                url: 'CheckUsername'

            }).then(function successCallback(response) {

                $scope.username_status = response.data;
                console.log($scope.username_status);
                if ($scope.username_status == false) {
                    $('#username_msg').hide();
                    $('#username_msg_edit').hide();
                } else {
                    $('#username_msg').show();
                    $('#username_msg_edit').show();
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
                    $('#email_msg_edit').hide();
                } else {
                    $('#email_msg').show();
                    $('#email_msg_edit').show();
                }
                showHideLoad(true);
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }
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
    $scope.editVendorReg = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorRegistrationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchLocations();
            $scope.fetchCountry();
            $scope.fetchPaymentMode();
            $scope.fetchFileUploadList('V');
            $scope.fetchState(response.data.countrycode);
            $scope.form = response.data;
            if ($scope.form.trade == true) {
                $scope.form.trade = 'true';
            } else {
                $scope.form.trade = 'false';
            }
            showHideLoad(true);
            $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveVendorReg = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'api/vendor/updateVendorRegistration';
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
    $scope.deleteVendorReg = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Vendor!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/vendor/deleteVendorRegistration';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoVendorRegisterListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }
    $scope.fetchFileUploadListForAdd = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details = response.data.details;


        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchFileUploadList = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.file_details = response.data.details;
            if ($scope.form.details.length == 0) {
                $scope.form.details = response.data.details;
            } else if ($scope.form.details.length != $scope.form.file_details) {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    if ($scope.form.details[key] == undefined || val.refId != $scope.form.details[key].refId) {
                        $scope.form.details.push(val);
                    } else {
                        $scope.form.details[key].file = val.file;
                    }
                });
            } else {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    $scope.form.details[key].file = val.file;
                });
            }

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.uploadAnyFile = function (kee, fileup) {
        console.log(form_data);
        console.log(fileup);
        var form_data = new FormData;
        form_data.append('module', 'vendor');
        form_data.append("file", fileup);
        console.log(form_data);

        var config = {
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        var url = "api/upload/addFile";

        $http.post(url, form_data, config).then(
            // Success
            function (response) {
                console.log(response);
                $scope.form.details[kee].link = response.data;
                Swal.fire({
                    text: "File Uploaded",
                    icon: "success",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
            },
            // Error
            function (response) {
                Swal.fire({
                    text: "File Upload failed! Please Upload Again",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
                console.log(response);
            }
        );
    }
    $scope.downloadFile = function (link) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'link': link },
            responseType: 'arraybuffer',
            url: 'api/upload/getFile'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = link;
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
        }, function errorCallback(response) {
            Swal.fire({
                text: "File Not Found! Please Upload Again",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
            showHideLoad(true);
        });
    }
    $scope.removeFile = function (kee, id) {
        $scope.form.details[kee].link = "";
        showHideLoad();
        var method = "DELETE";
        var url = 'api/master/deleteOutletDocument';
        $http({
            method: method,
            params: { 'id': id },
            url: url,
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details.splice(kee, 1);
            showHideLoad(true);
            Swal.fire(
                'Deleted!',
                response.data,
                'success'
            )
        }, function errorCallback(response) {
            console.log(response);
        });
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

//Vendor Approval module controller 
app.controller("VendorApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoVendorApprovalListFetch();
    function autoVendorApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorsForApprove'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendor_approval = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.vendorRegApprovalForm = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorRegistrationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            $scope.form.type = 'approve';

            $('#formReason').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();

            $('#vendor_reg_approve_modal').modal('show');
            $scope.vendor = response.data;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.vendorRegDisApprovalForm = function (id) {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form.id = id;
        $scope.form.type = 'disapprove';
        $('#vendor_reg_disapprove_modal').modal('show');
    }
    $scope.approveVendor = function (id) {
        console.log(id);
        showHideLoad();

        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id };
            var url = 'api/vendor/updateVendorApproval';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'api/vendor/updateVendorDisapprove';
        }
        $http({
            method: method,
            params: paramtr,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        if ($scope.form.type == 'approve') {
            $('#vendor_reg_approve_modal').modal('hide');
        } else {
            $('#vendor_reg_disapprove_modal').modal('hide');
        }
        autoVendorApprovalListFetch();
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
        console.log(response);
        showHideLoad(true);
        if ($scope.form.type == 'approve') {
            $('#vendor_reg_approve_modal').modal('hide');
        } else {
            $('#vendor_reg_disapprove_modal').modal('hide');
        }
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

    $scope.VisbilityState = function (type) {
        if (type == 'reject') {
            $('#formReason').show();
            $('#RejectButton').hide();
            $('#RejectFormButton').show();
            $('#approveFormButton').hide();
            $('#cancelbutton').show();
        } else {
            $('#formReason').hide();
            $('#RejectButton').hide();
            $('#RejectFormButton').hide();
            $('#approveFormButton').show();
            $('#cancelbutton').show();
        }
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.ViewEditRegistartionForm = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Vendor Product module controller 
app.controller("VendorProductController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoVendorProductListFetch();
    function autoVendorProductListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.products = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddVendorProduct = function () {
        $scope.fetchBrands();
        // $scope.fetchManufacturers();
        $scope.fetchUOM();
        $scope.fetchVendors();
        $scope.fetchSchedules();
        $scope.form = {};
        $scope.form.tempDetails = [];
        $scope.changeView('add');
    }

    $scope.addSaveVendorProduct = function () {
        showHideLoad();
        console.log($scope.form);
        var method = "POST";
        var url = 'api/vendor/postVendorProduct';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };


    function _success(response) {
        showHideLoad(true);
        autoVendorProductListFetch();
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
    $scope.openFetchItems = function (tableId) {
        $('#list_itms_modal').modal('show');
        $http({
            method: 'GET',
            url: 'api/master/getItem'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.items = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.selectItemId = function (id, division, faimly, class_n, subclass) {
        $('#list_itms_modal').modal('hide');
        $scope.form.itemId = id;
        $scope.form.item_name = division + '-' + faimly + '-' + class_n + '-' + subclass;
    }
    $scope.calculateFinalCost = function (mrp) {
        if ($scope.form.onInvMargin == undefined || $scope.form.onInvMargin == '') {
            Swal.fire(
                'Error!',
                'Please enter On Invoice Margin',
                'error'
            )
        } else {

            console.log(mrp);
            var finalCost = (mrp * ((100 - $scope.form.onInvMargin) / 100));
            $scope.form.tempDetails.finalCost = finalCost;
            console.log(finalCost);
        }
    }
    $scope.calculateGST = function (gst) {
        console.log(gst);
        var basic_cost = ($scope.form.tempDetails.finalCost * ((100 - gst) / 100));
        $scope.form.tempDetails.basicCost = basic_cost;
        console.log(basic_cost);
    }

    $scope.fetchBrands = function () {
        $http({
            method: 'GET',
            url: 'api/master/getBrand'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.brands = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchManufacturers = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getManufactureByVendorId'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.manufacturers = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.setInvoiceMarginValue = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getManufacturerById'

        }).then(function successCallback(response) {
            console.log(response);
            // $scope.form.onInvMargin = response.data.onInvoice;
            $scope.form.offInvMargin = response.data.offInvoice;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchUOM = function () {
        $http({
            method: 'GET',
            url: 'api/master/getUOMDetails'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.uoms = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchVendors = function () {
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchSchedules = function () {
        $http({
            method: 'GET',
            url: 'api/master/getSchedule'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.schedules = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchAmountsForedit = function (detailArray) {
        var detailArrayDecode = JSON.parse(detailArray);
        console.log(detailArrayDecode);
        $scope.form.tempDetails.finalCost = detailArrayDecode.finalCost;
        $scope.form.tempDetails.gstPer = detailArrayDecode.gstPer;
        $scope.form.tempDetails.basicCost = detailArrayDecode.basicCost;
        $scope.form.tempDetails.mrp = detailArrayDecode.mrp;
        $scope.form.tempDetails.id = detailArrayDecode.id;
    }
    $scope.editVendorProduct = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorProductId'

        }).then(function successCallback(response) {
            $scope.fetchBrands();
            $scope.fetchManufacturers(response.data.vendorId);
            $scope.fetchUOM();
            $scope.fetchVendors();
            $scope.fetchSchedules();
            $scope.form = response.data;
            // $scope.form.tempDetails = response.data.details;
            $scope.form.item_name = response.data.pro_division + '-' + response.data.pro_family + '-' + response.data.pro_class + '-' + response.data.pro_sub_class;
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveVendorProduct = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'api/vendor/updateVendorProduct';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };

    $scope.uploadAnyFile = function (fileup) {
        var form_data = new FormData;
        form_data.append('module', 'product');
        form_data.append("file", fileup);
        //console.log(form_data);

        var config = {
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        var url = "api/upload/addFile";

        $http.post(url, form_data, config).then(
            // Success
            function (response) {
                console.log(response);
                $scope.form.productPic = response.data;
                Swal.fire({
                    text: "File Uploaded",
                    icon: "success",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
            },
            // Error
            function (response) {
                Swal.fire({
                    text: "File Upload failed! Please Upload Again",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
                console.log(response);
            }
        );
    }
    $scope.downloadFile = function (link) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'link': link },
            responseType: 'arraybuffer',
            url: 'api/upload/getFile'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = link;
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
        }, function errorCallback(response) {
            Swal.fire({
                text: "File Not Found! Please Upload Again",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
            showHideLoad(true);
        });
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

//Vendor Product Approval  module controller 
app.controller("VendorProductApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoVendorProductApprovalListFetch();
    function autoVendorProductApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorsProductForApprove'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.products = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }


    $scope.SaveStatusOfProduct = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorProductId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            // $scope.form.type = 'approve';

            $('#formReason').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();

            $('#vendor_prod_approve_modal').modal('show');
            $scope.product = response.data;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.addSaveProductState = function (id) {
        console.log($scope.form);
        showHideLoad();
        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id };
            var url = 'api/vendor/updateVendorProductApproval';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'api/vendor/updateVendorProductDisapprove';
        }
        $http({
            method: method,
            params: paramtr,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#vendor_prod_approve_modal').modal('hide');
        autoVendorProductApprovalListFetch();
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
        console.log(response);
        showHideLoad(true);
        $('#vendor_prod_approve_modal').modal('hide');
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

    $scope.VisbilityState = function (type) {
        if (type == 'reject') {
            $scope.form.type = 'reject';
            $('#formReason').show();
            $('#RejectButton').hide();
            $('#RejectFormButton').show();
            $('#approveFormButton').hide();
            $('#cancelbutton').show();
        } else {
            $scope.form.type = 'approve';
            $('#formReason').hide();
            $('#RejectButton').hide();
            $('#RejectFormButton').hide();
            $('#approveFormButton').show();
            $('#cancelbutton').show();
        }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.ViewEditProductForm = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Shipping Notification module controller 
app.controller("ShippingNotificationController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoShippingNotifListFetch();
    function autoShippingNotifListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getShippingNotification'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.shippings = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddShippingNotif = function () {
        $scope.form = {};
        $scope.fetchVendors();
        $('#add_shipping_notif_modal').modal('show');
    }

    $scope.addSaveShippingNotif = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/vendor/addShippingNotification';
        } else {
            var method = "PUT";
            var url = 'api/vendor/updateShippingNotification';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_shipping_notif_modal').modal('hide');
        autoShippingNotifListFetch();
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
        console.log(response);
        showHideLoad(true);
        $('#add_shipping_notif_modal').modal('hide');
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
    $scope.fetchVendors = function () {
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    // $scope.fetchWarehouse = function (id) {
    //     showHideLoad();
    //     $http({
    //         method: 'GET',
    //         params: { 'id': id },
    //         url: 'api/master/getWarehouse'

    //     }).then(function successCallback(response) {
    //         console.log(response.data);
    //         $scope.warehouses = response.data;
    //         showHideLoad(true);
    //         $('#add_shipping_notif_modal').modal('show');
    //     }, function errorCallback(response) {
    //         console.log(response.statusText);
    //     });
    // }
    // $scope.fetchStore = function (id) {
    //     showHideLoad();
    //     $http({
    //         method: 'GET',
    //         params: { 'id': id },
    //         url: 'api/master/getStore'

    //     }).then(function successCallback(response) {
    //         console.log(response.data);
    //         $scope.stores = response.data;
    //         showHideLoad(true);
    //         $('#add_shipping_notif_modal').modal('show');
    //     }, function errorCallback(response) {
    //         console.log(response.statusText);
    //     });
    // }
    $scope.fetchStoreData = function (type) {
        showHideLoad();
        if (type == 'S') {
            var url = 'api/master/getStore';
        } else {
            var url = 'api/master/getWarehouse';

        }
        $http({
            method: 'GET',
            url: url

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.stores = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editShipping = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getShippingNotificationByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchVendors();
            $scope.fetchStoreData('S');
            $scope.form = response.data;
            $scope.form.store_type = 'W';
            showHideLoad(true);
            $('#add_shipping_notif_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteShipping = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Shipping Notification!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/vendor/deleteShippingNotification';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoShippingNotifListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.uploadAnyFile = function (fileup) {
        var form_data = new FormData;
        form_data.append('module', 'vendor-shippment');
        form_data.append("file", fileup);
        //console.log(form_data);

        var config = {
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        var url = "api/upload/addFile";

        $http.post(url, form_data, config).then(
            // Success
            function (response) {
                console.log(response);
                $scope.form.docLink = response.data;
                Swal.fire({
                    text: "File Uploaded",
                    icon: "success",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
            },
            // Error
            function (response) {
                Swal.fire({
                    text: "File Upload failed! Please Upload Again",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
                console.log(response);
            }
        );
    }
    $scope.downloadFile = function (link) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'link': link },
            responseType: 'arraybuffer',
            url: 'api/upload/getFile'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = link;
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
        }, function errorCallback(response) {
            Swal.fire({
                text: "File Not Found! Please Upload Again",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
            showHideLoad(true);
        });
    }
    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Vendor Register Onboarding module controller 
app.controller("VendorRegisterOnboardingController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
            $scope.form.invitee = $scope.invitee;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchLocationsDetailsById = function (id) {
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getLocationById'

        }).then(function successCallback(response) {
            console.log(response);
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
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
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
        console.log(response);
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
        console.log(username);
        if (username != undefined) {

            showHideLoad();
            $http({
                method: 'GET',
                params: { 'username': username },
                url: 'CheckUsername'

            }).then(function successCallback(response) {

                $scope.username_status = response.data;
                console.log($scope.username_status);
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

// Vendor Promo module controller
app.controller("VendorPromoController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPromoListFetch();
    function autoPromoListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getPromoForVendor'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.promos = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddPromo = function () {
        $scope.fetchLocations();
        $scope.fetchVendors();
        $scope.changeView('add');

    }
    $scope.addSaveVendorPromo = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postPromoForVendor';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPromoListFetch();
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
        console.log(response);
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
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchVendors = function () {
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchArticles = function (id) {
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorProductByVendorId'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.articles = response.data;

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchClusterStore = function (type) {
        if (type == 'S') {
            var url = "api/master/getStore";
        } else {
            var url = "api/master/getCluster";
        }
        $http({
            method: 'GET',
            url: url

        }).then(function successCallback(response) {
            console.log(response);
            $scope.artclusData = response.data;

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchMrpData = function (id) {
        console.log(id);
        angular.forEach($scope.articles, function (itm) {
            if (id == itm.id) {
                $scope.details_mrp = itm.details;
            }
        });

        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getArticleDetailsForPromo'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.priceBase = response.data.priceBase;
            $scope.form.gst = response.data.gst;
            var mrp_array = response.data.mrp;
            $scope.form.mrp = mrp_array.toString();

            $scope.form.total_amount_data = ($scope.form.venOfferPrice * ((100 + $scope.form.gst) / 100)).toFixed(2);

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.calculateTotalAmount = function (amt) {
        console.log($scope.form.gst);
        $scope.form.total_amount = (amt * ((100 + $scope.form.gst) / 100)).toFixed(2);
        $scope.form.total_amount_data = (amt * ((100 + $scope.form.gst) / 100)).toFixed(2);
    }
    $scope.editPromoVendor = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPromoByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchLocations();
            $scope.fetchVendors();
            $scope.fetchArticles(response.data.vendorId);
            $scope.details_mrp = response.data.details;
            $scope.form = response.data;
            $scope.fetchMrpData($scope.form.articleId);
            if ($scope.form.outletId != null) {
                $scope.form.asset_type = 'S';
            } else {
                $scope.form.asset_type = 'C';
            }
            $scope.fetchClusterStore($scope.form.asset_type);


            showHideLoad(true);
            $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSavePromoVendor = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updatePromoForVendor';
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
    $scope.deletePromoVendor = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Promo Vendor!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deletePromo';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoPromoListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
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

//Link cluster module controller 
app.controller("VendorManufactureMappingController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoManufactureMappingListFetch();
    function autoManufactureMappingListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getApprovedVendors'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.linkManufacture = function (id) {

        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVenManufData'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#link_manufacture_modal').modal('show');
            $scope.form = response.data;
            angular.forEach(response.data.manufactureId, function (val, key) {
                $scope.manufactureId[val] = true;
            });
            $scope.getManufactureList();
            $scope.form.vendorId = id;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.manufactureId = [];
    $scope.addSaveManufactureLinking = function () {
        showHideLoad();
        var i = 0;
        console.log($scope.manufactureId);
        $scope.form.manufactureId = [];
        angular.forEach($scope.manufactureId, function (val, key) {
            if (val == true) {
                $scope.form.manufactureId[i] = key;
                i = i + 1;
            }
        });
        var method = "POST";
        var url = 'api/vendor/postVenManufMapping';

        console.log($scope.form);
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };

    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#link_manufacture_modal').modal('hide');
        autoManufactureMappingListFetch();
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
        console.log(response);
        showHideLoad(true);
        $('#link_manufacture_modal').modal('hide');
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

    $scope.getManufactureList = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getManufacturer'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.manufactures = response.data;
            $('#link_manufacture_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});
//vendor replacement module controller 
app.controller("VendorReplacementController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoReplaceMentListFetch();
    function autoReplaceMentListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getApprovedVendors'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendors = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.addSaveVendorReplacement = function () {
        Swal.fire({
            title: 'Are you sure?',
            text: "Do you want to replace vendor?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();

                var method = "POST";
                var url = 'api/vendor/vendorReplacement';

                console.log($scope.form);
                $http({
                    method: method,
                    params: { 'oldId': $scope.form.oldId, 'newId': $scope.form.newId },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(_success, _error);
            }
        })
    };

    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoReplaceMentListFetch();
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
        console.log(response);
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



    $scope.changeView = function (view) {
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});