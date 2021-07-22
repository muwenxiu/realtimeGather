//# sourceURL=dynamicScript.js
$(function () {
        //$('#serverWarning').attr("width", "500");
        //console.log($('#serverWarning').attr("width"))
        $('#serverWarning').datagrid({
            loadMsg: "正在加载数据。。。",
            url: 'get/getBackstageService',
            //fitColumns:true,//宽度自适应
            singleSelect: true,
            columns: [[
                {field: "backstageServiceName", title: "服务名称", width: 200},
                {
                    field: "backstageServiceStatus",
                    title: "状态",
                    width: 200,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == 'ERROR') {
                            return "<span style='color: red'> " + value + "</span>";
                        } else {
                            return value;
                        }
                    },
                },
                {
                    field: "serverOper", title: "操作", width: 50, formatter: function (value, rowData, rowIndex) {
                        var str = '<a href="#" name="opera" class="easyui-linkbutton" onclick="restartService(' + rowIndex + ')">重启</a>';
                        // str = '<a href="javascript:void(0)" onclick="showUser(' + rowIndex + ')">重启</a>  ';
                        return str
                    }
                },
                {field: "backstageServiceStartScript", title: "操作", hidden: true},
            ]],
            method: 'post'
        });
        $('#onTimeGatherWarning').datagrid({
            //fitColumns:true,//宽度自适应
            singleSelect: true,
            columns: [[
                {field: 'errorBatch', width: 200, title: "报错批次"},
                {field: 'errorTaskID', width: 200, title: "报错任务ID"},
            ]]
        });
    }
);

function restartService(index) {
    $('#serverWarning').datagrid("selectRow", index);
    var rows = $("#serverWarning").datagrid("getSelections");
    if (rows.length <= 0) {
        return
    }
    $.messager.confirm("服务重启", "选中的服务是否重启?", function (r) {
        if (r) {
            var row = rows[0];
            console.log(row.backstageServiceName)
            $.ajax(
                {
                    url: 'get/getRestartService',
                    data: {serviceName: row.backstageServiceName, serviceStartScript: row.backstageServiceStartScript},
                    success: function (data) {
                        if (data) {
                            $.messager.alert("成功", "重启服务成功");
                        } else {
                            $.messager.alert("告警", "重启服务失败");
                        }
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        $.messager.alert("告警", "重启服务失败");
                    },
                }
            );
        }
    })
}