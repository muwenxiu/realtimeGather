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
                $.ajax({
                    url: '/onTimeGatherBatch',
                    type: 'POST',
                    success: function (data) {
                        $("#center").html(data);
                    },
                })
            } else if (node.text == "采集批次删除") {
                $.ajax({
                    url: '/onTimeGatherBatchDel',
                    type: 'POST',
                    success: function (data) {
                        $("#center").html(data);
                    },
                })
            }
            else if (node.text == "分析进度") {
                $.ajax({
                    url: '/analyseTemplate',
                    type: 'POST',
                    success: function (data) {
                        $("#center").html(data);
                    },
                })
            }
            else if (node.text == "分析历史进度删除") {
                $.ajax({
                    url: '/analyseHistoryDel',
                    type: 'POST',
                    success: function (data) {
                        $("#center").html(data);
                    },
                })
            }
        }
    });
});