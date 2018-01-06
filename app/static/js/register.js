
/****************************全局变量区域*************************/
var inputFlag = false;      //判断用户是否输入了合法的登陆账号
var passwordFlag = false;   //判断用户是否输入了合法的密码
var inputType;  //输入类型

// 手机号flag
var cellFlag = false;
//邮箱flag
var emailFlag = false;
//密码flag
var passwordFlag = false;
//用户名flag
var usernameFlag = false;
//切换tab事件
var tags = document.getElementsByTagName("nav")[0].getElementsByTagName("a");
change(tags[0]);
function change(value) {
    var content = document.getElementsByClassName("tabTarget");
    document.getElementById("phone").value = "";
    document.getElementById("email").value = "";
    document.getElementById("confirmButton").style.disabled = true;

    for (var i = 0; i < tags.length; i++) {
        if (tags[i] === value) {
            tags[i].classList.add("selected");
            tags[i].style.fontSize = "1rem";
            tags[i].style.width = tags[i].style.width + 130 + "px";
            tags[i].style.height = tags[i].style.height + 50 + "px";
            content[i].style.display = "block";
            tags[i].style.color = "black";
        } else {
            tags[i].classList.remove("selected");
            tags[i].style.fontSize = "0.8rem";
            tags[i].style.color = "#7a7e89";
            tags[i].style.width = tags[i].style.width - 130 + "px";
            tags[i].style.height = tags[i].style.height - 50 + "px";
            content[i].style.display = "none";
        }
    }
}

//模拟输入框聚焦事件，失焦事件在相应名称的函数中
function addBorderStyle(a) {
    a.classList.add("input");
}

//用户名输入检测
function username() {
    //用户名输入错误信息
    var usernameError = document.getElementById("usernameError");
    //用户名节点
    var username = document.getElementById("username");
    //失去焦点
    username.parentNode.classList.remove("input");
    //正则表达式
    // var patt = /^[\u4E00-\u9FA5A-Za-z0-9_@!#$]+$/;    //用户名（中文，数字，包括下划线）

    var patt = /^[\u4e00-\u9fa5a-zA-Z0-9\w]{1,20}$/;    //可以包含中文、大小写字母、数字及下划线
    //用户名
    var username = username.value.replace(/\s+/g, "");

    if (username == "") {
        usernameFlag = false;
        // usernameError.innerText = " ✘请输入用户名";
    } else if (!patt.test(username)) {
        usernameFlag = false;
        usernameError.innerText = " ✘用户名只能包含中文、大小写字母、数字及下划线";
    } else {
        usernameError.innerHTML = "<p>&nbsp;</p>";
        usernameFlag = true;
    }
    // usernameError.style.paddingBottom = "0px";
    changeCue(usernameFlag, usernameError);
}
//检验用户输入的密码
function password(a) {
    var flag = true;
    var password = document.getElementById("password");
    password.parentNode.classList.remove("input");  //当失去焦点时去除边框样式

    password = password.value.replace(/\s+/g, "");

    var passwordError = document.getElementById("passwordError");

    var patt1 = /^[a-zA-Z0-9_]{6,40}$/;    //可以包含六位以上的字母数字@和下划线

    if (patt1.test(password)) {
        passwordError.innerHTML = "<p>&nbsp;</p>";
        passwordFlag = true;
    } else {
        passwordFlag = false;
        passwordError.innerHTML = "✘密码应包含六位以上的字母数字及下划线";
    }

    // passwordError.style.paddingBottom = "4px";
    changeCue(passwordFlag, passwordError);
}
//检验重新输入密码
function repassword() {
    //重新输入密码DOM
    var repassword = document.getElementById("repassword");
    //失去焦点样式
    repassword.parentNode.classList.remove("input");

    //获取密码
    var password = document.getElementById("password").value.replace(/\s+/g, "");
    //获取重新输入的密码
    var repassword = repassword.value.replace(/\s+/g, "");
    //错误提示DOM
    var repasswordError = document.getElementById("repasswordError");

    var x = (repassword === password);
    // alert(x);
    if (!x) {
        repasswordFlag = false;
        repasswordError.innerHTML = "✘密码不一致，请重新输入";
    } else {
        repasswordFlag = true;
        repasswordError.innerHTML = "<p>&nbsp;</p>";
    }

    // repasswordError.style.paddingBottom = "4px";
    changeCue(repasswordFlag, repasswordError);
}

//onblur失去输入框聚焦样式
function loseBorder(obj){
    // var obj = document.getElementById(obj);
    obj.parentNode.classList.remove("input");
}

//输入手机号或者邮箱格式正确时
function inputRight(){
    var send = document.getElementById("confirmButton");
    send.classList.add("clock");
    send.removeAttribute("disabled");
}

//检验手机号
function phone(obj) {
    var phone = document.getElementById("phone").value.replace(/\s+/g, "");
    var phoneError = document.getElementById("phoneError");
    var patt = /^1[3|5|7|8]\d{9}$/g;

    if (!patt.test(phone)) {
        cellFlag = false;
    } else {
        cellFlag = true;
        //手机号输入正确时可以发送验证码
        inputRight();
    }
    // phoneError.style.paddingBottom = "4px";
    changeCue(cellFlag, phoneError);
}

//邮箱
function email() {
    //email节点
    var email = document.getElementById("email").value.replace(/\s+/g, "");
    var emailError = document.getElementById("emailError");
    var patt = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g;

    if (patt.test(email)) {
        emailError.innerHTML = "<p>&nbsp;</p>";
        cellFlag = true;
        //邮箱输入正确时可以发送验证码
        inputRight();
        
    } else {
        emailFlag = false;
        emailError.innerText = "✘请输入正确的邮箱！";
    }
    // emailError.style.paddingBottom = "4px";
    changeCue(emailFlag, emailError);
}


//倒计时
var count = 120;
function show(obj1) {
    if (count == 0) {
        obj1.value = "获取验证码";
        obj1.removeAttribute("disabled");
        obj1.classList.add("clock");
        count = 120;
        return;
    } else {
        obj1.classList.add("clock");
        obj1.setAttribute("disabled", true);
        obj1.classList.remove("clock");
        obj1.value = count + "秒";
        count--;
    }

    setTimeout(function () {
        show(obj1);
    }, 1000)
}

//发送验证码
// function sendConfirm(obj1) {
//     var clock = obj1;

//     var phone = document.getElementById("phone").value.replace(/\s+/g, "");
//     var email = document.getElementById("email").value.replace(/\s+/g, "");

//     // show(obj1);    //测试时钟
//     if (phone) {
//         // path = "/sendmes";
//         alert("this is phone");
//         $.ajax({
//             type: 'post',
//             async: true,
//             url: "/sendmes", //要访问的后台地址
//             data: {
//                 phone: phone
//             },
//             success: function (data) {
//                 if (data.code == 1) {
//                     alert("发送成功！");
//                     show(obj1)
//                     confirmFlag = true;
//                 } else {
//                     alert(data.msg);
//                 }
//             },
//             error: function (data) {
//                 alert('error');
//                 alert(data.status);
//                 alert(data.readyState);
//                 //alert(textStatus);
//             }
//         });
//     } else if (email) {
//         alert("this is email");
//         $.ajax({
//             type: 'post',
//             async: true,
//             url: "/sendemail", //要访问的后台地址
//             data: {
//                 email: email
//             },
//             success: function (data) {
//                 if (data.code == 1) {
//                     show(obj1);
//                     alert("发送成功！");
//                     confirmFlag = true;
//                 } else {
//                     alert(data.msg);
//                 }
//             },
//             error: function (data) {
//                 alert('error');
//                 alert(data.status);
//                 alert(data.readyState);
//                 //alert(textStatus);
//             }
//         });
//     } else {
//         alert("没有发送目标");
//     }

//     phone = document.getElementById("phone").value;
//     // alert("字符类型："+typeof(phone));


// }
/****************************************************************/

//改变提示语样式
function changeCue(flag, action) {
    action.style.visibility = "visible";
    if (flag) {
        action.className = "";
        action.className = "reminderPass";
    } else {
        action.className = "";
        action.className = "reminderError";
    }
}

//注册
function register() {

    // 用户名
    var username = document.getElementById("username").value.replace(/\s+/g, "");
    // 密码
    var password = document.getElementById("password").value.replace(/\s+/g, "");
    // 手机号
    var phone = document.getElementById("phone").value.replace(/\s+/g, "");
    //邮箱
    var email = document.getElementById("email").value.replace(/\s+/g, "");
    // 验证码
//  var confirm = document.getElementById("confirm").value.replace(/\s+/g, "");
    // alert(username);
    // alert(password);
    // alert(repassword);
    // alert(phone);
    // alert(confirm);

    //手机号登录
    var x = cellFlag && passwordFlag && usernameFlag;
    //邮箱登录
    var y = emailFlag && passwordFlag && usernameFlag;

    if (x || y) {
        $.ajax({
            type: 'POST',
            async: false,
            dataType:"json",
            url: "http://47.92.48.100:8099/iot/api/user/doRegister", //要访问的后台地址
            data: {
                "username": username,
                "password": password,
                "bindPhone": phone
            }, 
            success: function (data) {
                alert(data.message);
            },
            error: function (data) {
                alert('error');
                alert(data);
                alert(data.status);
                alert(data.readyState);
                //alert(textStatus);
            }
        });
    } else {
        alert("请输入正确的注册信息！");
    }


}
