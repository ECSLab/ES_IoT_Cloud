var deviceCount; //设备数量
var apiKey;

$(document).ready(function () {
  //获取项目信息
  $.get("http://47.92.48.100:8099/iot/api/project/38/detail", function (
    res1,
    status
  ) {
    deviceCount = res1.data.deviceCount;
    apiKey = res1.data.apiKey;
    $("#deviceCount").text(deviceCount);
    $("#APIKEY").text(apiKey);
  });

  getList();
});

function getList() {
  $.get("http://47.92.48.100:8099/iot/api/device/26/38/deviceList", function (res2, status) {
    var len = res2.data.length;
    $("#DM_devices").empty();
    for (var i = len - 1; i >= 0; i--) {
      data = res2.data[i];
      $("#DM_devices").append(
        '<tr><td class="basicInformation"><svg class="deviceImg" aria-hidden="true"><use xlink:href="#icon-equipment-info"></use></svg><div class="deviceMsg"><h6 class="deviceName">' +
        data.name +
        '</h6><p class="deviceId">设备id:<span>' +
        data.id +
        '</span></p><p class="createTime">创建时间：<span>' +
        data.createTime +
        '</span></p></div></td><td>私密</td><td><i class="iconfont icon-bianji3"></i><i class="iconfont icon-shanchu2"></i><i class="iconfont icon-xiangqing"></i><i class="iconfont icon-shoudongtianjia"></i><i class="iconfont icon-shujuguanli"></i><i class="iconfont icon-kaifatiaoshi"></i></td></tr>'
      );
    }
    // 删除设备
    for (var j = 0; j < len; j++) {
      !(function (j) {
        $(".icon-shanchu2")[j].addEventListener("click", function () {
          var id = res2.data[len - j - 1].id;
          var userId = 26;

          var url = "http://47.92.48.100:8099/iot/api/device/delete/device?userId=" + userId + "&deviceId=" + id + "&apiKey=68b72e7c9fde449594d7a286b48e8c2e";
          // 弹出框
          event.stopPropagation();
          $("#zl_delete").fadeIn(500);
          $("#bg").fadeIn(100);

          $("#deleteButton").click(function () {
            $.ajax({
              url: url,
              type: "delete",
              success: function (result) {
                console.log(result);
                if (result.code == 0) {
                  popUp("success", "删除成功！");
                  getList();
                } else {
                  popUp("error", "删除失败！");
                }
              }
            });
          });
        });
      })(j);
    }
  });
}

var clipboard = new Clipboard(".DM_copy", {
  target: function () {
    return document.querySelector("#APIKEY");
  }
});

clipboard.on("success", function (e) {
  popUp("success", "复制成功");
});

clipboard.on("error", function (e) {
  popUp("error", "你的浏览器暂不支持此功能，建议安装Chrome");
});

function popUp(flag, msg) {
  $("#popMsg").text(msg);
  $("#popup").fadeIn();
  setTimeout(function () {
    $("#popup").fadeOut();
    $("#popup")
      .removeClass("successPopup")
      .removeClass("errorPopup");
  }, 2000);
  if (flag === "success") {
    $("#DM_popImg")
      .removeClass("icon-wuneirong")
      .addClass("icon-success1");
    $("#popup")
      .removeClass("errorPopup")
      .addClass("successPopup");
  } else {
    $("#DM_popImg")
      .removeClass("icon-success1")
      .addClass("icon-wuneirong");
    $("#popup")
      .removeClass("successPopup")
      .addClass("errorPopup");
  }
}

$("#DM_mySearch").click(function () {
  var founded = $("#DM_search").val();
  //去除特殊字符
  founded = founded.split("");
  var patt = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1}$/;
  founded = founded.filter(function (item, index, array) {
    return patt.test(item);
  });
  founded = founded.join("");
  if (founded === "") {
    getList();
    return;
  }

  getDeviceList(founded);
});

// 根据关键词加载列表，嗯，就是搜索
var getDeviceList = function (search) {
  var patt1 = "/" + search + "/";
  $.get("http://47.92.48.100:8099/iot/api/device/7/4/deviceList", function (res, status) {
    var len = res.data.length;

    for (var i = len - 1; i >= 0; i--) {
      data = res.data[i];
      var searchName = search == data.name;
      var searchId = search == data.id;
      if (searchName || searchId) {
        $("#DM_devices").empty();
        $("#DM_devices").append(
          '<tr><td class="basicInformation"><svg class="deviceImg" aria-hidden="true"><use xlink:href="#icon-equipment-info"></use></svg><div class="deviceMsg"><h6 class="deviceName">' +
          data.name +
          '</h6><p class="deviceId">设备id:<span>' +
          data.id +
          '</span></p><p class="createTime">创建时间：<span>' +
          data.createTime +
          '</span></p></div></td><td>私密</td><td><i class="iconfont icon-bianji3"></i><i class="iconfont icon-shanchu2"></i><i class="iconfont icon-xiangqing"></i><i class="iconfont icon-shoudongtianjia"></i><i class="iconfont icon-shujuguanli"></i><i class="iconfont icon-kaifatiaoshi"></i></td></tr>'
        );
      }
    }
  });
};

$("#DM_dowloadDeviceMSG").click(function () {
  createTable();
  doExport();
})

function createTable() {
  $.ajax({
    async: false,
    type: 'GET',
    url: "http://47.92.48.100:8099/iot/api/device/7/4/deviceList",
    success: function (res2, status) {
      var len = res2.data.length;
      $("#table").empty();
      for (var i = len - 1; i >= 0; i--) {
        data = res2.data[i];
        $("#table").append(
          "<tr><td>" + data.name + "</td><td>" + data.id + "</td><td>" + data.createTime + "</td><td>" + data.dataType +
          "</td><td>" + data.dataType + "</td><td>" + data.number + "</td><td>" + data.privacy + "</td><td>" + data.projectId + "</td><td>" + data.protocolId + "</td></tr>"
        );
      }
    }
  })
}

function doExport() {
  $("#exportTable").tableExport({
    type: "excel",
    excelstyles: [
      "background-color",
      "color",
      "font-family",
      "font-size",
      "font-weight",
      "text-align"
    ]
  });
}

