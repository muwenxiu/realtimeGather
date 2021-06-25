$(function () {
    $("#idCellInfoTree").tree({
        onClick: function (node) {
            if (node.text == "采集mysql数据库") {
                alert("采集mysql数据库");
            } else if (node.text == "采集mysql实例") {
                //$("#center").attr("th:include", "modules/realtimeGather_instance :: table");
                //$("#center").load(location.href+" .dl");
            } else if (node.text == "采集的表") {
                //$("#center").attr("th:include", "modules/realtimeGather_Table :: table");

                //$("#center").load(location.href+" .dl");
                alert("采集的表");
            } else if (node.text == "采集通道") {
                alert("采集通道");
            } else if (node.text == "采集进度") {
                alert("采集进度");
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