"use strict";
var KTCreateAccount = function () {
    var e, t, i, o, r, s, n = [];
    return {
        init: function () {
            (e = document.querySelector("#kt_modal_create_account")) && new bootstrap.Modal(e), (t = document.querySelector("#kt_create_account_stepper")) && (i = t.querySelector("#kt_create_account_form"), o = t.querySelector('[data-kt-stepper-action="submit"]'), r = t.querySelector('[data-kt-stepper-action="next"]'), (s = new KTStepper(t)).on("kt.stepper.changed", (function (e) {
                4 === s.getCurrentStepIndex() ? (o.classList.remove("d-none"), o.classList.add("d-inline-block"), r.classList.add("d-none")) : 5 === s.getCurrentStepIndex() ? (o.classList.add("d-none"), r.classList.add("d-none")) : (o.classList.remove("d-inline-block"), o.classList.remove("d-none"), r.classList.remove("d-none"))
            })), s.on("kt.stepper.next", (function (e) {
                console.log("stepper.next");
                var t = n[e.getCurrentStepIndex() - 1];
                t ? t.validate().then((function (t) {
                    console.log("validated!"), "Valid" == t ? (e.goNext(), KTUtil.scrollTop()) : Swal.fire({
                        text: "Sorry, looks like there are some errors detected, please try again.",
                        icon: "error",
                        buttonsStyling: !1,
                        confirmButtonText: "Ok, got it!",
                        customClass: {
                            confirmButton: "btn btn-light"
                        }
                    }).then((function () {
                        KTUtil.scrollTop()
                    }))
                })) : (e.goNext(), KTUtil.scrollTop())
            })), s.on("kt.stepper.previous", (function (e) {
                console.log("stepper.previous"), e.goPrevious(), KTUtil.scrollTop()
            })), n.push(FormValidation.formValidation(i, {
                fields: {
                    username: {
                        validators: {
                            notEmpty: {
                                message: "Username is required"
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: "Password is required"
                            },
                            stringLength: {
                                min: 6,
                                max: 10,
                                message: "Password must contain at-least 6 latter"
                            }
                        }
                    }
                },
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap: new FormValidation.plugins.Bootstrap5({
                        rowSelector: ".fv-row",
                        eleInvalidClass: "",
                        eleValidClass: ""
                    })
                }
            })), n.push(FormValidation.formValidation(i, {
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: "Name is required"
                            }
                        }
                    },
                    add1: {
                        validators: {
                            notEmpty: {
                                message: "Address is required"
                            }
                        }
                    },
                    location: {
                        validators: {
                            notEmpty: {
                                message: "Please Enter or Select your location"
                            }
                        }
                    },
                    countrycode: {
                        validators: {
                            notEmpty: {
                                message: "Please select your country"
                            }
                        }
                    },
                    stateid: {
                        validators: {
                            notEmpty: {
                                message: "Please Select your state"
                            }
                        }
                    },
                    pin: {
                        validators: {
                            notEmpty: {
                                message: "Please Enter your pin code"
                            }
                        }
                    },
                    mobile: {
                        validators: {
                            notEmpty: {
                                message: "Mobile number is required"
                            }
                        }
                    },
                    email1: {
                        validators: {
                            notEmpty: {
                                message: "Primary email is required"
                            },
                            emailAddress: {
                                message: "The value is not a valid email address"
                            }
                        }
                    },
                    email2: {
                        validators: {
                            notEmpty: {
                                message: "Finance SPOC email is required"
                            },
                            emailAddress: {
                                message: "The value is not a valid email address"
                            }
                        }
                    }
                },
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap: new FormValidation.plugins.Bootstrap5({
                        rowSelector: ".fv-row",
                        eleInvalidClass: "",
                        eleValidClass: ""
                    })
                }
            })), n.push(FormValidation.formValidation(i, {
                fields: {
                    gst_no: {
                        validators: {
                            notEmpty: {
                                message: "GST Number is required"
                            }
                        }
                    },
                    cont_pers: {
                        validators: {
                            notEmpty: {
                                message: "Contact Person is required"
                            }
                        }
                    },
                    cont_mobile: {
                        validators: {
                            notEmpty: {
                                message: "Contact Person mobile number is required"
                            }
                        }
                    },
                    business_description: {
                        validators: {
                            notEmpty: {
                                message: "Busines description is required"
                            }
                        }
                    },
                    business_email: {
                        validators: {
                            notEmpty: {
                                message: "Busines email is required"
                            },
                            emailAddress: {
                                message: "The value is not a valid email address"
                            }
                        }
                    }
                },
                plugins: {
                    trigger: new FormValidation.plugins.Trigger,
                    bootstrap: new FormValidation.plugins.Bootstrap5({
                        rowSelector: ".fv-row",
                        eleInvalidClass: "",
                        eleValidClass: ""
                    })
                }
            })), o.addEventListener("click", (function (e) {
                n[2].validate().then((function (t) {
                    console.log("validated!"), "Valid" == t ? (e.preventDefault(), o.disabled = !0, o.setAttribute("data-kt-indicator", "on"), setTimeout((function () {
                        o.removeAttribute("data-kt-indicator"), o.disabled = !1, i.hasAttribute("data-kt-redirect-url") ? Swal.fire({
                            text: "Your account has been successfully created.",
                            icon: "success",
                            buttonsStyling: !1,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn btn-primary"
                            }
                        }).then((function (e) {
                            e.isConfirmed && (location.href = i.getAttribute("data-kt-redirect-url"))
                        })) : s.goNext()
                    }), 2e3)) : Swal.fire({
                        text: "Sorry, looks like there are some errors detected, please try again.",
                        icon: "error",
                        buttonsStyling: !1,
                        confirmButtonText: "Ok, got it!",
                        customClass: {
                            confirmButton: "btn btn-light"
                        }
                    }).then((function () {
                        KTUtil.scrollTop()
                    }))
                }))
            })), $(i.querySelector('[name="card_expiry_month"]')).on("change", (function () {
                n[3].revalidateField("card_expiry_month")
            })), $(i.querySelector('[name="card_expiry_year"]')).on("change", (function () {
                n[3].revalidateField("card_expiry_year")
            })), $(i.querySelector('[name="business_type"]')).on("change", (function () {
                n[2].revalidateField("business_type")
            })))
        }
    }
}();
KTUtil.onDOMContentLoaded((function () {
    KTCreateAccount.init()
}));