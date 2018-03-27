var usernameFlag = true;
var passwordFlag = true;
var repasswordFlag = false;
var phonenumberFlag = true;
var realnameFlag = true;
var qqFlag = true;
var emailFlag = true;

function rightPoint(obj) {
    obj.promptBox.hide();
    obj.inputBox.removeClass("error");
    obj.inputBox.addClass("right");
}

function errorPoint(obj) {
    obj.promptBox.show();
    obj.promptBox.text(obj.hint);
    obj.inputBox.removeClass("right");
    obj.inputBox.addClass("error");
}

$("#username").bind("input", function () {
    var username = $("#username").val();
    var patt = /^[\u4e00-\u9fa5a-zA-Z0-9\w]{1,12}$/;
    username = username.replace(/\s+/g, "");
    var obj = {
        promptBox: $("#usernameError"),
        inputBox: $("#username"),
    }

    if (username === "") {
        obj.hint = "昵称不能为空！";
        errorPoint(obj);
        usernameFlag = false;
    } else if (!patt.test(username)) {
        if (username.length > 12) {
            obj.hint = "最多12个字符";
            errorPoint(obj);
        } else {
            obj.hint = "✘用户名只能包含中文、大小写字母、数字及下划线";
            errorPoint(obj);
        }
        usernameFlag = false;
    } else {
        var url = "http://47.92.48.100:8099/iot/api/user/" + username + "/check"
        $.get(url, function (res) {
            if (res.code == 100) {
                rightPoint(obj);
                usernameFlag = true;
            } else {
                obj.hint = "用户名已存在！";
                errorPoint(obj);
                usernameFlag = false;
            }
        });
    }
    showButton();
});

$("#qq").bind("change", function () {
    var qq = $("#qq").val();
    var patt = /^[1-9][0-9]{4,}$/;
    var obj = {
        promptBox: $("#qqError"),
        inputBox: $("#qq"),
    }

    if (!patt.test(qq) && qq != "") {
        obj.hint = "QQ号格式不正确！";
        errorPoint(obj);
        qqFlag = false;
    } else {
        rightPoint(obj);
        qqFlag = true;
    }
})


$("#password").bind("change", function password() {
    var password = $("#password").val();
    password = password.replace(/\s+/g, "");
    var obj = {
        promptBox: $("#passwordError"),
        inputBox: $("#password"),
    }

    var patt1 = /^[a-zA-Z0-9_]{6,40}$/;    //可以包含六位以上的字母数字@和下划线
    var patt2 = /^[a-zA-Z]{1,}$/;
    var patt3 = /^[0-9]{1,}$/;

    if (password == "") {
        obj.hint = "密码不能为空";
        errorPoint(obj);
        passwordFlag = false;
    }

    else if (!patt1.test(password)) {
        obj.hint = "密码只能包含六位以上的字母数字和下划线";
        errorPoint(obj);
        passwordFlag = false;
    }
    else {
        rightPoint(obj);
        passwordFlag = true;
    }
    $("#repassword").val("");    
    showButton();
})
$("#repassword").bind("input", function repassword() {
    var repassword = $("#repassword").val().replace(/\s+/g, "");
    var password = $("#password").val().replace(/\s+/g, "");

    obj = {
        promptBox: $("#repasswordError"),
        inputBox: $("#repassword"),
    }
    var x = (repassword === password);
    if (!x) {
        repasswordFlag = false;
        obj.hint = "✘密码不一致，请重新输入";
        errorPoint(obj);
        repasswordFlag = false;
    } else {
        rightPoint(obj);
        repasswordFlag = true;
    }
    showButton();
})

$("#phonenumber").bind("change", function phone(obj) {
    var phone = $("#phonenumber").val().replace(/\s+/g, "");
    var patt = /^1[3|5|7|8]\d{9}$/g;
    obj = {
        promptBox: $("#phonenumberError"),
        inputBox: $("#phonenumber"),
    }
    if (!patt.test(phone)) {
        if (phone == "") {
            obj.hint = "手机号不能为空";
            errorPoint(obj);
            phonenumberFlag = false;
        } else {
            obj.hint = "格式不正确";
            errorPoint(obj);
            phonenumberFlag = false;
        }
    } else {
        rightPoint(obj);
        phonenumberFlag = true;
    }
    showButton();
})

$("#email").change(function email() {
    var email = $("#email").val();
    var patt = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g;
    obj = {
        promptBox: $("#emailError"),
        inputBox: $("#email"),
    }

    if (patt.test(email)) {
        emailFlag = "true";
        rightPoint(obj);
    } else {
        emailFlag = "false";
        obj.hint = "请输入正确的邮箱！";
        errorPoint(obj);
    }
})
function showButton() {
    var flag = usernameFlag && passwordFlag && repasswordFlag && phonenumberFlag && realnameFlag && qqFlag && emailFlag;
    if (flag) {
        $("#register").removeAttr("disabled");
        $("#register").removeClass("buttonNotAllow")
        $("#register").addClass("buttonAllow");
    } else {
        $("#register").attr("disabled","disabled");
        $("#register").addClass("buttonNotAllow")
        $("#register").removeClass("buttonAllow");
    }
}
$("#register").bind("click",function () {
    var username = $("#username").val().replace(/\s+/g, "");
    var realname = $("#realname").val();
    var qq = $("#qq").val();
    var password = $("#password").val().replace(/\s+/g, "");
    var phone = $("#phonenumber").val().replace(/\s+/g, "");
    var email = $("#email").val();
    var workplace = $("#province").find("option:selected").text() + $("#city").find("option:selected").text() + $("#area").find("option:selected").text();
    var personalProfile = $("#personalProfile").val();
    var flag = usernameFlag && passwordFlag && repasswordFlag && phonenumberFlag && realnameFlag && qqFlag && emailFlag;

    if (flag) {
        $.ajax({
            type: 'POST',
            async: false,
            dataType: "json",
            url: "http://47.92.48.100:8099/iot/api/user/doRegister", //要访问的后台地址
            data: {
                "username": username,
                "realName": realname,
                "qq": qq,
                "password": password,
                "bindPhone": phone,
                "bindEmail": email,
                "location": workplace,
                "personalProfile": personalProfile
            },
            success: function (data) {
                alert(data.message);
            },
            error: function (data) {
                alert('error');
                alert(data);
                alert(data.status);
                alert(data.readyState);
            }
        });
    } else {
        if (!usernameFlag) {
            alert("用户名有误！");
        } else if (!passwordFlag) {
            alert("密码格式有误！");
        } else if (!repasswordFlag) {
            alert("密码不一致");
        } else if (!phonenumberFlag) {
            alert("电话号码格式不正确！");
        } else if (!qqFlag) {
            alert("QQ错误！");
        } else if (!emailFlag) {
            alert("邮箱错误！");
        } else {
            alert("有误！");
        }
    }
})


