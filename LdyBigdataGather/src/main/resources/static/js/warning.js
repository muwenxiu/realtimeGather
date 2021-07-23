//# sourceURL=dynamicScript.js
$(function () {
        //$('#serverWarning').attr("width", "500");
        //console.log($('#serverWarning').attr("width"))
        $('#serverWarning').datagrid({
            height: 290,
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
            width: 500,
            height: 290,
            //fitColumns:true,//宽度自适应
            loadMsg: "正在加载数据。。。",
            url: 'get/getOnTimeGatherWarning',
            singleSelect: true,
            columns: [[
                {field: 'errorBatch', width: 200, title: "报错批次"},
                {field: 'errorTaskID', width: 200, title: "报错任务ID"},
                {field: 'msg', width: 100, title: "报错任务ID"},

            ]],
            rowStyler: function (index, row) {
                if (row.msg === 'ERROR') {
                    return 'background-color:pink;color:red;font-weight:bold;'
                }
            },
        });

        $('#dataQualityOdsWarning').datagrid(
            {
                height: 500,
                width: 600,
                url: 'get/getDataQualityOdsWarning',
                singleSelect: true,
                columns: [[
                    {field: "tableName", title: "表名", width: 300},
                    {field: "srcTableCount", title: "Mysql数据量", width: 100},
                    {field: "tarTableCount", title: "Kudu数据量", width: 100},
                    {field: "diff", title: "数据量差", width: 100},
                ]],
                method: 'post',
                rowStyler: function (index, row) {
                    //if (row.msg === 'ERROR') {
                    //  return 'background-color:pink;color:red;font-weight:bold;'
                    //}
                },
            }
        );
        $('#dataQualityDwdWarning').datagrid(
            {
                height: 500,
                width: 560,
                url: 'get/getDataQualityDwdWarning',
                singleSelect: true,
                columns: [[
                    {field: "tableName", title: "表名", width: 300},
                    {field: "srcTableCount", title: "Ods数据量", width: 100},
                    {field: "tarTableCount", title: "Dwd数据量", width: 100},
                    {field: "diff", title: "数据量差", width: 60},
                ]],
                method: 'post',
                rowStyler: function (index, row) {
                    //if (row.msg === 'ERROR') {
                    //  return 'background-color:pink;color:red;font-weight:bold;'
                    //}
                },
            }
        );

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
                            $('#serverWarning').datagrid({
                                loadMsg: "正在加载数据。。。",
                                url: 'get/getBackstageService',
                                method: 'post'
                            });
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