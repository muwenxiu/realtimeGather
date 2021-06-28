$(function () {
    $("#idCellInfoTree").tree({
        onClick: function (node) {
            if (node.text == "采集mysql数据库") {
                alert("采集mysql数据库");
            } else if (node.text == "采集mysql实例") {
                //$("#center").load("/realtimeGather_instance");
                //或者
                $.ajax({
                        url: '/realtimeGather_instance',
                        type: 'POST',
                        success: function (data) {
                            $("#center").html(data);
                        }
                    }
                )
            } else if (node.text == "采集的表") {
                $.ajax({
                        url: '/realtimeGather_Table',
                        type: 'POST',
                        success: function (data) {
                            $("#center").html(data);
                        }
                    }
                )
            } else if (node.text == "采集通道") {
                $("#center").load("/realtimeGather_canalChannel");
            } else if (node.text == "采集进度") {
               // $("#center").load("/realtimeGather_canalChannelProgress");
                $.ajax({
                        url: '/realtimeGather_canalChannelProgress',
                        type: 'POST',
                        success: function (data) {
                            $("#center").html(data);
                        }
                    }
                )

            } else if (node.text == "采集的批次") {
                alert("采集的批次");
            } else if (node.text == "分析进度") {
                alert("分析进度");
            } else {
                alert("ss");
            }
        }
    });

});

function clientSideInclude(id, url) {
    var req = false;

// Safari, Firefox, 及其他非微软浏览器
    if (window.XMLHttpRequest) {
        try {
            req = new XMLHttpRequest();
        } catch (e) {
            req = false;
        }
    } else if (window.ActiveXObject) {

// For Internet Explorer on Windows
        try {
            req = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                req = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
                req = false;
            }
        }
    }
    var element = document.getElementById(id);
    if (!element) {
        alert("函数clientSideInclude无法找到id " + id + "。" +
            "你的网页中必须有一个含有这个id的div 或 span 标签。");
        return;
    }
    if (req) {
        req.open('GET', url, false);
        req.send(null);
        element.innerHTML = req.responseText;
    } else {
        element.innerHTML =
            "对不起，你的浏览器不支持" +
            "XMLHTTPRequest 对象。这个网页的显示要求" +
            "Internet Explorer 5 以上版本, " +
            "或 Firefox 或 Safari 浏览器，也可能会有其他可兼容的浏览器存在。";
    }
}
