var app = angular.module("PurchaseManagment", ['ui.bootstrap', 'datatables']);

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


// Purchase Order module controller
app.controller("PurchaseOrderController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPurchaseOrderListFetch();
    function autoPurchaseOrderListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getPurchaseOrder'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.orders = response.data;
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

    $scope.AddPurchase = function () {
        $scope.fetchVendors();
        $scope.fetchBilling();
        $scope.fetchShippings();
        $scope.changeView('add');

    }
    $scope.productId = [];
    $scope.selectArticleModal = function (id) {
        $scope.productId = [];
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorFinalProductByVendorIdForPo'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.products = response.data;
            angular.forEach($scope.form.product_details, function (val, key) {
                $scope.productId[val.id] = true;
            });
            $('#link_article_modal').modal('show');
            $scope.form.product_details = [];
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.product_details.splice(index, 1);
    }
    $scope.selectShowProducts = function (check, product, index) {
        // showHideLoad();
        var i = 0;
        console.log(index);
        if (check == false) {
            $scope.form.product_details.push(product);
        }
        // $scope.form.productId = [];
        // $scope.form.product_details = [];
        // angular.forEach($scope.productId, function (val, key) {
        //     if (val == true) {
        //         $scope.form.productId[i] = key;

        //         i = i + 1;
        //     }
        // });
        // if ($scope.form.productId.length == 0) {
        //     $scope.form.productId = 0;
        // }
        // $http({
        //     method: 'GET',
        //     params: { 'id': $scope.form.productId },
        //     url: 'api/vendor/getVendorForProductInBulk'

        // }).then(function successCallback(response) {
        //     console.log(response);
        //     $scope.form.product_details = response.data;
        //     showHideLoad(true);
        // }, function errorCallback(response) {
        //     console.log(response.statusText);
        // });
        // var method = "PUT";
        // var url = 'api/master/clusterMappingForArticle';
        // $('#link_article_modal').modal('hide');
        // // $scope.form.product_details = $scope.form.productId;
        // console.log($scope.form);
        // $http({
        //     method: method,
        //     url: url,
        //     data: angular.toJson($scope.form),
        //     headers: {
        //         'Content-Type': 'application/json'
        //     },
        //     transformResponse: angular.identity
        // }).then(_success, _error);
    };
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
            $scope.form.paymTerms = response.data.credit_days;

            var date = new Date();

            // var day = date.getDate();
            // var month = date.getMonth() + 1;
            // var year = date.getFullYear();

            var next_date = new Date();
            next_date.setDate(date.getDate() + response.data.lead_time);
            var day = next_date.getDate();
            var month = next_date.getMonth() + 1;
            var year = next_date.getFullYear();
            $scope.form.exp_dt = year + '-' + month + '-' + day;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
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
    $scope.fetchBilling = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/admin/getCompany'

        }).then(function successCallback(response) {

            $scope.billings = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchShippings = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getOutlet'

        }).then(function successCallback(response) {

            $scope.shippings = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.calculateAmounts = function (mrp_id, kee, details) {
        showHideLoad();
        // console.log(mrp_id);
        // console.log(kee);
        angular.forEach(details, function (val, key) {

            $scope.form.product_details[kee].cost = val.finalCost;
            $scope.form.product_details[kee].gst_tax = val.gstPer;
        });
        showHideLoad(true);

    }
    $scope.calculateQuantityAmount = function (quantity, kee, cost, tax) {
        showHideLoad();
        console.log(quantity);
        console.log(cost);
        $scope.form.product_details[kee].basic_cost = cost * quantity;
        if ($scope.form.state_id == $scope.form.ship_state_id) {

            $scope.form.product_details[kee].sgst = ((tax / 2) / 100) * $scope.form.product_details[kee].basic_cost;
            $scope.form.product_details[kee].cgst = ((tax / 2) / 100) * $scope.form.product_details[kee].basic_cost;
            $scope.form.product_details[kee].net_amount = $scope.form.product_details[kee].sgst + $scope.form.product_details[kee].cgst + $scope.form.product_details[kee].basic_cost;
        } else {
            $scope.form.product_details[kee].igst = (tax / 100) * $scope.form.product_details[kee].basic_cost;

            $scope.form.product_details[kee].net_amount = $scope.form.product_details[kee].igst + $scope.form.product_details[kee].basic_cost;
        }
        // console.log($scope.form.product_details);
        var basic_cost_var = 0;
        var totSgstAmt_var = 0;
        var totCgstAmt_var = 0;
        var totIgstAmt_var = 0;
        var totNetAmt_var = 0;
        angular.forEach($scope.form.product_details, function (val, key) {
            // console.log(val.basic_cost);

            basic_cost_var = basic_cost_var + val.basic_cost;
            if ($scope.form.state_id == $scope.form.ship_state_id) {
                totSgstAmt_var = totSgstAmt_var + val.sgst;
                totCgstAmt_var = totCgstAmt_var + val.cgst;
            } else {
                totIgstAmt_var = totIgstAmt_var + val.igst;
            }
            totNetAmt_var = totNetAmt_var + val.net_amount;
        });
        // console.log(basic_cost_var);
        $scope.form.totBasicAmt = basic_cost_var;
        if ($scope.form.state_id == $scope.form.ship_state_id) {
            $scope.form.totSgstAmt = totSgstAmt_var;
            $scope.form.totCgstAmt = totCgstAmt_var;
        } else {
            $scope.form.totIgstAmt = totIgstAmt_var;
        }
        $scope.form.totNetAmt = totNetAmt_var;
        showHideLoad(true);

    }

    $scope.addSavePurchase = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'postPurchaseOrder';

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
        autoPurchaseOrderListFetch();
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
    $scope.editPurchase = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPurchaseOrderByid'

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
            $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSavePurchaceOrder = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'updatePurchaseOrder';
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
    $scope.deletePurchase = function (id) {
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
                var url = 'deletePurchaseOrder';
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
                    autoPurchaseOrderListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }
    $scope.ViewPurchase = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPurchaseOrderByidForView'

        }).then(function successCallback(response) {
            console.log(response.data);
            // $scope.fetchVendors();
            // $scope.fetchBilling();
            // $scope.fetchShippings();
            // $scope.viewVendorDetails(response.data.vendorId);
            // $scope.viewBillingDetails(response.data.billing_id);
            // $scope.viewShippmentDetails(response.data.ship_id);
            $scope.purchase_order = response.data;

            showHideLoad(true);
            $('#purchase_order_view_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.BillEntryView = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPurchaseOrderByid'

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
    $scope.downloadPurchaseReport = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            responseType: 'arraybuffer',
            url: 'api/report/poReport'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = 'po-report.pdf';
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
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
        $scope.views.billEntry = false;
        $scope.views[view] = true;
    }
});

//Purchase order Approval module controller 
app.controller("PurchaseOrderApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPurchaseOrderApprovalListFetch();
    function autoPurchaseOrderApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getPurchaseOrderForApproval'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.orders = response.data;
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

    $scope.PurchaseOrderApprovalForm = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPurchaseOrderByidForView'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            $scope.form.type = 'approve';

            $('#formReason').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();

            $('#purchase_order_approve_modal').modal('show');
            $scope.purchase_order = response.data;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.approvePurchaseOrder = function (id) {
        console.log(id);
        showHideLoad();

        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id };
            var url = 'approvePurchaseOrder';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'disapprovePurchaseOrder';
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
        $('#purchase_order_approve_modal').modal('hide');
        autoPurchaseOrderApprovalListFetch();
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
        $('#purchase_order_approve_modal').modal('hide');
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

// Purchase Bill module controller
app.controller("PurchaseBillController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPurchaseBillListFetch();
    function autoPurchaseBillListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'getDetailsPurchaseBill'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.bills = response.data;
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

    $scope.AddPurchaseBill = function () {
        $scope.changeView('add');

    }
    $scope.ViewPurchaseBill = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'getPurchaseBillByidForView'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.purchase_bill = response.data;

            showHideLoad(true);
            $('#purchase_order_view_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePurchaseBill = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this purchase bill!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'deletePurchaseBill';
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
                    autoPurchaseBillListFetch();
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

