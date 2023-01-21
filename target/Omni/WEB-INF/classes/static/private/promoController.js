var app = angular.module("PromoManagment", ['ui.bootstrap', 'datatables']);

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




// Buyer Promo module controller
app.controller("BuyerPromoController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoBuyerPromoListFetch();
    function autoBuyerPromoListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getPromoForCustomer'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.buyers = response.data;
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

    $scope.AddBuyerPromo = function () {
        $scope.fetchLocations();
        $scope.fetchVendors();
        $scope.changeView('add');

    }

    $scope.addSaveBuyerPromo = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postPromoForCustomer';

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
        autoBuyerPromoListFetch();
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
            $scope.form.gst_ven = response.data.gst;
            var mrp_array = response.data.mrp;
            $scope.form.mrp = mrp_array.toString();

            $scope.form.total_amount_data = ($scope.form.selfOfferPrice * ((100 + $scope.form.gst) / 100)).toFixed(2);
			$scope.form.total_amt_ven = ($scope.form.venOfferPrice * ((100 + $scope.form.gst_ven) / 100)).toFixed(2);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.calculateTotalAmount = function (amt) {
        console.log($scope.form.gst);
        $scope.form.total_amount = (amt * ((100 + $scope.form.gst) / 100)).toFixed(2);
        $scope.form.total_amount_data = (amt * ((100 + $scope.form.gst) / 100)).toFixed(2);
    }
    $scope.editBuyerPromo = function (id) {
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
			//$scope.form.gst_ven = response.data;
			
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
    $scope.editSaveBuyerPromo = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updatePromoForCustomer';
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
    $scope.deleteBuyerPromo = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Buyer Promo !",
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

//Vendor promo Approval module controller 
app.controller("VendorPromoApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoVendorPromoApprovalListFetch();
    function autoVendorPromoApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getVendorPromoForApproval'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendor_promo = response.data;
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

    $scope.vendorPromoApprovalForm = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPromoByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            $scope.form.type = 'approve';

            $('#formReason').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();

            $('#vendor_promo_approve_modal').modal('show');
            $scope.vendor_promo_apv = response.data;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.approveVendorPromo = function (id) {
        console.log(id);
        showHideLoad();

        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id };
            var url = 'promoVendorApproval';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'promoVendorDisapproval';
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
        $('#vendor_promo_approve_modal').modal('hide');
        autoVendorPromoApprovalListFetch();
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
        $('#vendor_promo_approve_modal').modal('hide');
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
        $scope.views.ViewEditRegistartionForm = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Buyer promo Approval module controller 
app.controller("BuyerPromoApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoBuyerPromoApprovalListFetch();
    function autoBuyerPromoApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getCustomerPromoForApproval'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.buyer_promo = response.data;
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

    $scope.buyerPromoApprovalForm = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPromoByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            $scope.form.type = 'approve';

            $('#formReason').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();

            $('#buyer_promo_approve_modal').modal('show');
            $scope.buyer_promo_apv = response.data;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.approveBuyerPromo = function (id) {
        console.log(id);
        showHideLoad();

        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id };
            var url = 'promoCustomerApproval';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'promoCustomerDisapproval';
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
        $('#buyer_promo_approve_modal').modal('hide');
        autoBuyerPromoApprovalListFetch();
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
        $('#buyer_promo_approve_modal').modal('hide');
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
        $scope.views.ViewEditRegistartionForm = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//General Promo module controller 
app.controller("GeneralPromoController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoGeneralPromoListFetch();
    function autoGeneralPromoListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getGeneralPromo'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.generals = response.data;
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

    $scope.AddGeneralPromo = function () {
        $scope.form = {};
        $('#add_general_promo_modal').modal('show');
    }
    $scope.addSaveGeneralPromo = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'postGeneralPromo';
        } else {
            var method = "PUT";
            var url = 'updateGeneralPromo';
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
        $('#add_general_promo_modal').modal('hide');
        autoGeneralPromoListFetch();
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
        $('#add_general_promo_modal').modal('hide');
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

    $scope.editGeneralPromo = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getGeneralPromoByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_general_promo_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteGeneralPromo = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this general promo!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deleteGeneralPromo';
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
                    autoGeneralPromoListFetch();
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
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});