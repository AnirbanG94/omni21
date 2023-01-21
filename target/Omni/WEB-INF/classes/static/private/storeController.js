var app = angular.module("storeManagment", ['ui.bootstrap', 'datatables']);

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

// Warehouse Module - GRN controller 
app.controller("GrnController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    // var new_date = new Date();
    // var current_date = new_date.toLocaleDateString();
    // console.log(current_date);

    let yourDate = new Date();
    let current_date = yourDate.toISOString().split('T')[0];
    console.log(current_date);

    autoGRNListFetch();
    function autoGRNListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getGRN'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.grn_details = response.data;
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

    $scope.AddGrn = function () {
        $scope.fetchStore();
        $scope.changeView('add');

    }

    $scope.fetchPODetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPOArticleGRN'

        }).then(function successCallback(response) {

            $scope.po_details = response.data;
            $('#po_details_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.selectPoDetailsForGrn = function (id) {

        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPODetailForGRN'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.poNo = response.data.poNo;
            $scope.form.poDt = response.data.poDt;
            $scope.form.outletName = response.data.outletName;
            $scope.form.vendorName = response.data.vendorName;
            $scope.form.outletId = response.data.outletId;
            $scope.form.details = response.data.details;
            $scope.form.vendorId = response.data.vendorId;
            $scope.form.totBasicAmt = response.data.totBasicAmt;
            $scope.form.totCgstAmt = response.data.totCgstAmt;
            $scope.form.totSgstAmt = response.data.totSgstAmt;
            $scope.form.totIgstAmt = response.data.totIgstAmt;
            $scope.form.totNetAmt = response.data.totNetAmt;
            $scope.form.grnDate = current_date;
            $('#po_details_modal').modal('hide');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchStore = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getStore'

        }).then(function successCallback(response) {

            $scope.warehouses = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.addSaveGrn = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postGRN';

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
        autoGRNListFetch();
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

    $scope.editGRNDetail = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getGRNById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchStore();
            $scope.form = response.data;
            showHideLoad(true);
            $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editSaveGrn = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updateGRN';
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


    $scope.deleteGrn = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this GRN!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'deleteGrn';
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
                    autoGRNListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.calculateQuantityAmount = function (quantity, kee, cost, tax) {
        showHideLoad();
        console.log(quantity);
        console.log(cost);
        $scope.form.details[kee].basicAmt = cost * quantity;
        // if ($scope.form.state_id == $scope.form.ship_state_id) {

        $scope.form.details[kee].sgstAmt = ((tax / 2) / 100) * $scope.form.details[kee].basicAmt;
        $scope.form.details[kee].cgstAmt = ((tax / 2) / 100) * $scope.form.details[kee].basicAmt;
        $scope.form.details[kee].netAmt = $scope.form.details[kee].sgstAmt + $scope.form.details[kee].cgstAmt + $scope.form.details[kee].basicAmt;
        // } else {
        $scope.form.details[kee].igstAmt = (tax / 100) * $scope.form.details[kee].basicAmt;

        // $scope.form.details[kee].netAmt = $scope.form.details[kee].igstAmt + $scope.form.details[kee].basicAmt;
        // }
        // console.log($scope.form.details);
        var basic_cost_var = 0;
        var totSgstAmt_var = 0;
        var totCgstAmt_var = 0;
        var totIgstAmt_var = 0;
        var totNetAmt_var = 0;
        angular.forEach($scope.form.details, function (val, key) {
            // console.log(val.basic_cost);

            basic_cost_var = basic_cost_var + val.basicAmt;
            // if ($scope.form.state_id == $scope.form.ship_state_id) {
            totSgstAmt_var = totSgstAmt_var + val.sgstAmt;
            totCgstAmt_var = totCgstAmt_var + val.cgstAmt;
            // } else {
            totIgstAmt_var = totIgstAmt_var + val.igstAmt;
            // }
            totNetAmt_var = totNetAmt_var + val.netAmt;
        });
        // console.log(basic_cost_var);
        $scope.form.totBasicAmt = basic_cost_var;
        // if ($scope.form.state_id == $scope.form.ship_state_id) {
        $scope.form.totSgstAmt = totSgstAmt_var;
        $scope.form.totCgstAmt = totCgstAmt_var;
        // } else {
        $scope.form.totIgstAmt = totIgstAmt_var;
        // }
        $scope.form.totNetAmt = totNetAmt_var;
        showHideLoad(true);

    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.details.splice(index, 1);
    }

    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views.billEntry = false;
        $scope.views[view] = true;
    }

});

// Warehouse Module - GDN controller 
app.controller("GDNController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;


    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var day_new = day.toString();
    var month_new = month.toString();
    var year_new = year.toString();

    var currentDate = day_new + '-' + month_new + '-' + year_new;

    console.log($scope.form.gdnDt);

    //===================article wise data fetch========================//
    $scope.getAssetByArticleForPickList = function (productId) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'productId': productId },
            url: 'getAssetByArticleForPickList'

        }).then(function successCallback(response) {
            console.log(response.data);
            showHideLoad(true);
            $scope.form.assetsno = response.data[0].assetsno;

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePickListDetails = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this PickListDetails!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deletePickListDetails';
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
                    autoPickListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }


    //
    //    
    //       autoPickListFetch();
    //    function autoPickListFetch() {
    //
    //        showHideLoad();
    //        $http({
    //            method: 'GET',
    //            url: 'autoPickListFetch'
    //
    //        }).then(function successCallback(response) {
    //            console.log(response);
    //            $scope.Pick_List_view = response.data;
    //            $scope.changeView('list');
    //            showHideLoad(true);
    //        }, function errorCallback(response) {
    //            console.log(response.statusText);
    //        });
    //    }

    $scope.fetchVendors = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {

            $scope.vendors = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }



    $scope.fetchPoDetails = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'getPoDetailsForGDN'

        }).then(function successCallback(response) {
            console.log(response);
            //$scope.po_gdn = response.data;
            $scope.po_gdn = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.addSaveGDN = function () {
        console.log($scope.form);
        showHideLoad();

        var method = "POST";
        var url = 'postGdn';

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


    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPickListFetch();
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



    $scope.fetchWareHouseForGDN = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getWarehouse'

        }).then(function successCallback(response) {
            $scope.form.gdnDt = currentDate;
            console.log(response);
            $scope.outlet_gdn = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.getGDNListForEditByid = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getGDNListForEditByid'

        }).then(function successCallback(response) {
            $scope.form.gdnDt = currentDate;
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchVendors();
            $scope.fetchWareHouseForGDN();
            $scope.fetchPoDetails();
            $scope.viewVendorDetails();
            //fetchReasonsForGdn();
            //$scope.getGRNDetailsByArticleId();
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.viewVendorDetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorRegistrationById'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.address1 = response.data.add1;
            $scope.form.city = response.data.locationname;
            $scope.form.state = response.data.statename;
            $scope.form.country = response.data.countryname;
            $scope.form.pin = response.data.pin;
            $scope.form.state_id = response.data.stateid;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }




    autoGDNListFetch();
    function autoGDNListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'autoGDNListFetch'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.gdn_details_view = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    fetchReasonsForGdn();
    function fetchReasonsForGdn() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getReason'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.reasons = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.orders = [];

    showHideLoad(true);


    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddGDN = function () {
        $scope.fetchWareHouseForGDN();
        $scope.fetchVendors();
        $scope.form.gdnDt = currentDate;
        $scope.fetchPoDetails();
        $scope.changeView('add');

    }
    $scope.productId = [];

    $scope.selectArticleModal = function (poNo) {
        $scope.productId = [];

        $http({
            method: 'GET',
            params: { 'poNo': poNo },
            url: 'getGDNDetailsByPoNo'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.products = response.data[0].details;
            angular.forEach($scope.form.product_details, function (val, key) {
                $scope.productId[val.poNo] = true;
            });
            $('#assets_item_modal').modal('show');
            $scope.form.product_details = [];
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }

    $scope.getGRNDetailsByArticleId = function (check, articleId) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'articleId': articleId },
            url: 'getGRNDetailsByArticleId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.product_details = response.data;
            showHideLoad(true);
            $('#assets_item_modal').modal('hide');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.product_details.splice(index, 1);
    }
    $scope.selectShowProducts = function (check, x, index) {
        showHideLoad();
        var i = 0;
        console.log(index);
        if (check == false) {
            $scope.form.product_details.push(x);
        }

    };



    $scope.fetchGrnDetailsByPoNo = function (poNo) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'poNo': poNo },
            url: 'getGrnDetailsByPoNo'

        }).then(function successCallback(response) {
            console.log(response);
            //$scope.po_gdn = response.data;
            $scope.form = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.editSaveGdn = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updateGdn';
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
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        // autoPickListFetch();
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




    $scope.deleteGDN = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this delete GDN!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deleteGDN';
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
                    autoPickListFetch();
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
        $scope.views.billEntry = false;
        $scope.views[view] = true;
    }


    $scope.appendNewitem = function () {
        var counNew_item_list = 0;
        if (typeof $scope.form.product_details === "undefined") {
            counNew_item_list = 0
        } else {
            counNew_item_list = $scope.form.product_details.length;
        }
        if (counNew_item_list > 0) {
            $scope.form.product_details.push({ 'name': '', 'grnNo': '', 'mrp': '', 'cp': '', 'rej_qty': '', 'rsn': [] });
        } else {
            $scope.form.product_details = [];
            $scope.form.product_details.push({ 'name': '', 'grnNo': '', 'mrp': '', 'cp': '', 'rej_qty': '', 'rsn': [] });
        }
    }

});

// Warehouse Module - pick list controller 
app.controller("PickListController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;


    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var day_new = day.toString();
    var month_new = month.toString();
    var year_new = year.toString();

    var currentDate = day_new + '-' + month_new + '-' + year_new;

    console.log($scope.form.trDt);

    //===================article wise data fetch========================//
    $scope.getAssetByArticleForPickList = function (productId) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'productId': productId },
            url: 'getAssetByArticleForPickList'

        }).then(function successCallback(response) {
            console.log(response.data);
            showHideLoad(true);
            $scope.form.assetsno = response.data[0].assetsno;

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePickListDetails = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this PickListDetails!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deletePickListDetails';
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
                    autoPickListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })

    }



    $scope.getUOMByArticleForPickList = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getUOMByArticleForPickList'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.name = response.data[0].name;
            $http({
                method: 'GET',
                params: { 'id': id },
                url: 'api/master/getArticlesById'
            }).then(function successCallback(res) {
                console.log(res);
                $scope.article_by_id = res.data;
                $scope.getAssetByArticleForPickList($scope.article_by_id.productId);

            }, function errorCallback(res) {
                console.log(res.statusText);
            });
            showHideLoad(true);

            // $scope.getAssetByArticleForPickList();
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }



    autoPickListFetch();
    function autoPickListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'autoPickListFetch'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.Pick_List_view = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    autoArticleListFetch();
    function autoArticleListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getArticles'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.articles = response.data;

            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.fetchOutletForPick = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getOutlet'

        }).then(function successCallback(response) {
            $scope.form.trDt = currentDate;
            console.log(response);
            $scope.outlet_pick = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.getPickListForEditByid = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPickListForEditByid'

        }).then(function successCallback(response) {
            $scope.form.trDt = currentDate;
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchOutletForPick();
            //$scope.fetchLocations();
            //$scope.fetchState($scope.form.countrycode);
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    autoGRNListFetch();
    function autoGRNListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'autoGRNListFetch'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.grn_details_view = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }



    $scope.orders = [];

    showHideLoad(true);


    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddPickList = function () {
        $scope.fetchOutletForPick();
        // $scope.fetchOutletForDrop(); 
        $scope.form.trDt = currentDate;

        $scope.changeView('add');

    }
    $scope.productId = [];
    $scope.selectArticleModal = function (id) {
        $scope.productId = [];
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getDetailsGrn'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.products = response.data;
            angular.forEach($scope.form.product_details, function (val, key) {
                $scope.productId[val.id] = true;
            });
            $('#assets_item_modal').modal('show');
            $scope.form.product_details = [];
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.product_details.splice(index, 1);
    }
    $scope.selectShowProducts = function (check, x, index) {
        // showHideLoad();
        var i = 0;
        console.log(index);
        if (check == false) {
            $scope.form.product_details.push(x);
        }

    };

    $scope.viewBillingDetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/admin/getCompanyByid'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.bill_add = response.data.addr1;
            $scope.form.bill_city = response.data.locationname;
            $scope.form.bill_state = response.data.statename;
            $scope.form.bill_country = response.data.countryname;
            $scope.form.bill_pin = response.data.pincode;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
    $scope.viewShippmentDetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getOutletByid'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.ship_add = response.data.add1;
            $scope.form.ship_city = response.data.locationname;
            $scope.form.ship_state = response.data.statename;
            $scope.form.ship_country = response.data.countryname;
            $scope.form.ship_pin = response.data.pin;
            $scope.form.ship_state_id = response.data.stateid;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
    $scope.fetchVendors = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {

            $scope.vendors = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }




    $scope.addSavePickList = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.outletFr != $scope.form.outletTo) {
            var method = "POST";
            var url = 'postPickList';

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
            alert("Both Outlets Can Not Be The Same");
        }
    }


    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPickListFetch();
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

    $scope.editSavePickList = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updatePickList';
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
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPickListFetch();
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




    $scope.deleteGrn = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this purchase order!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'deleteGrn';
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
                    autoGrnListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.BillEntryView = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getGrnByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchVendors();
            $scope.fetchBilling();
            $scope.fetchShippings();
            $scope.viewVendorDetails(response.data.vendorId);
            $scope.viewBillingDetails(response.data.billing_id);
            $scope.viewShippmentDetails(response.data.ship_id);
            $scope.form = response.data;

            showHideLoad(true);
            $scope.changeView('billEntry');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSavePurchaceBillEntry = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postPurchaseBill';

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
    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views.billEntry = false;
        $scope.views[view] = true;
    }

});

// Warehouse Module - transferout controller 
app.controller("TransferOutController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;
    $scope.stores = {};



    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var day_new = day.toString();
    var month_new = month.toString();
    var year_new = year.toString();

    var currentDate = day_new + '-' + month_new + '-' + year_new;

    console.log($scope.form.todt);

    //===================article wise data fetch========================//
    $scope.getAssetByArticleForPickList = function (productId) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'productId': productId },
            url: 'getAssetByArticleForPickList'

        }).then(function successCallback(response) {
            console.log(response.data);
            showHideLoad(true);
            $scope.form.assetsno = response.data[0].assetsno;

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePickListDetails = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this PickListDetails!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'deletePickListDetails';
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
                    autoPickListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }



    $scope.getUOMByArticleForPickList = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getUOMByArticleForPickList'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.name = response.data[0].name;
            $http({
                method: 'GET',
                params: { 'id': id },
                url: 'api/master/getArticlesById'
            }).then(function successCallback(res) {
                console.log(res);
                $scope.article_by_id = res.data;
                $scope.getAssetByArticleForPickList($scope.article_by_id.productId);

            }, function errorCallback(res) {
                console.log(res.statusText);
            });
            showHideLoad(true);

            // $scope.getAssetByArticleForPickList();
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }



    autoPickListFetch();
    function autoPickListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'autoPickListFetch'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.Pick_List_view = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    autoArticleListFetch();
    function autoArticleListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getArticles'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.articles = response.data;

            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.fetchOutletForPick = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getOutlet'

        }).then(function successCallback(response) {
            $scope.form.todt = currentDate;
            console.log(response);
            $scope.outlet_pick = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.getPickListForEditByid = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPickListForEditByid'

        }).then(function successCallback(response) {
            $scope.form.todt = currentDate;
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchOutletForPick();
            //$scope.fetchLocations();
            //$scope.fetchState($scope.form.countrycode);
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    autoGRNListFetch();
    function autoGRNListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'autoGRNListFetch'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.grn_details_view = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }



    $scope.orders = [];

    showHideLoad(true);


    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddPickList = function () {
        $scope.fetchOutletForPick();
        // $scope.fetchOutletForDrop(); 
        $scope.form.todt = currentDate;

        $scope.changeView('add');

    }
    $scope.productId = [];
    $scope.selectArticleModal = function (id) {
        $scope.productId = [];
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getDetailsGrn'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.products = response.data;
            angular.forEach($scope.form.product_details, function (val, key) {
                $scope.productId[val.id] = true;
            });
            $('#assets_item_modal').modal('show');
            $scope.form.product_details = [];
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.product_details.splice(index, 1);
    }
    $scope.selectShowProducts = function (check, x, index) {
        // showHideLoad();
        var i = 0;
        console.log(index);
        if (check == false) {
            $scope.form.product_details.push(x);
        }

    };

    $scope.viewBillingDetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/admin/getCompanyByid'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.bill_add = response.data.addr1;
            $scope.form.bill_city = response.data.locationname;
            $scope.form.bill_state = response.data.statename;
            $scope.form.bill_country = response.data.countryname;
            $scope.form.bill_pin = response.data.pincode;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
    $scope.viewShippmentDetails = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getOutletByid'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.ship_add = response.data.add1;
            $scope.form.ship_city = response.data.locationname;
            $scope.form.ship_state = response.data.statename;
            $scope.form.ship_country = response.data.countryname;
            $scope.form.ship_pin = response.data.pin;
            $scope.form.ship_state_id = response.data.stateid;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
    $scope.fetchVendors = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {

            $scope.vendors = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }




    $scope.addSavePickList = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.outletFr != $scope.form.outletTo) {
            var method = "POST";
            var url = 'postPickList';

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
            alert("Both Outlets Can Not Be The Same");
        }
    }


    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPickListFetch();
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

    $scope.editSavePickList = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updatePickList';
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
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoPickListFetch();
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




    $scope.deleteGrn = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this purchase order!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'deleteGrn';
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
                    autoGrnListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.BillEntryView = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getGrnByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchVendors();
            $scope.fetchBilling();
            $scope.fetchShippings();
            $scope.viewVendorDetails(response.data.vendorId);
            $scope.viewBillingDetails(response.data.billing_id);
            $scope.viewShippmentDetails(response.data.ship_id);
            $scope.form = response.data;

            showHideLoad(true);
            $scope.changeView('billEntry');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSavePurchaceBillEntry = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postPurchaseBill';

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
    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views.billEntry = false;
        $scope.views[view] = true;
    }
    $scope.arti = {};
    $scope.selectArticleModal = function () {
        $scope.productId = [];
        $('#link_article_modal').modal('show');
        $http({
            method: 'GET',
            url: 'PickListForToti'
        }).then(function successCallback(response) {
            //console.log(response);
            $scope.transfer_out = response.data;
            // console.log(response.data[0].articleList);
            //$scope.arti=response.data[].articleList;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }





    $scope.fetchStore = function (type) {
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


});
