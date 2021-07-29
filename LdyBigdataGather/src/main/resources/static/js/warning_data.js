//# sourceURL=warning_data.js
var windowHeight = 0;
var idTop = 0;
var mysqlTableName = '';
var rowMonth;
$(function () {
    mysqlTableName = $('#mysqlTableName').text();
    window.onresize = function () {
        setResize();
    };
    $('#dataQuality_Warning_Correction_Month').datagrid({
        singleSelect: true,
        method: 'post',
        height: windowHeight - idTop,
        //width: 600,
        url: 'get/getTableDataQualityMonth',
        queryParams: {
            mysqlTableName: mysqlTableName,
        },
        columns: [[
            {field: "dataDate", title: "日期", width: 100},
            {field: "srcTableCount", title: "Mysql数据量", width: 150},
            {field: "tarTableCount", title: "Kudu数据量", width: 150},
            {
                field: "diff",
                title: "数据量差",
                width: 200,
                formatter: function (value, rowData, rowIndex) {
                    if (value != 0) {
                        return "<span style='color: red'> " + value + "</span>";
                    } else {
                        return value;
                    }
                },
            },
            {
                field: "reGather", title: "操作", width: 100, formatter: function (value, rowData, rowIndex) {
                    var str = '<a href="#" name="opera" class="easyui-linkbutton" onclick="reGatherData_month(' + rowIndex + ')">重新采集</a>';
                    return str
                }
            },
            {
                field: "recoverGather", title: "操作", width: 100, formatter: function (value, rowData, rowIndex) {
                    var str = '<a href="#" name="opera" class="easyui-linkbutton" onclick="recoverGather_month(' + rowIndex + ')">叠加采集</a>';
                    return str
                }
            },
        ]],
        onDblClickRow: function (rowIndex, rowData) {
            setDataQuality_Day(rowData, mysqlTableName);
        },
    });
    $('#dataQuality_Warning_Correction_Day').datagrid({
        singleSelect: true,
        //height: windowHeight - idTop,
        columns: [[
            {field: "dataDate", title: "日期", width: 100},
            {field: "srcTableCount", title: "Mysql数据量", width: 100},
            {field: "tarTableCount", title: "Kudu数据量", width: 100},
            {
                field: "diff",
                title: "数据量差",
                width: 200,
                formatter: function (value, rowData, rowIndex) {
                    if (value != 0) {
                        return "<span style='color: red'> " + value + "</span>";
                    } else {
                        return value;
                    }
                },
            },

            {
                field: "reGather", title: "操作", width: 100, formatter: function (value, rowData, rowIndex) {
                    var str = '<a href="#" name="opera" class="easyui-linkbutton" onclick="reGatherData_day(' + rowIndex + ')">重采集</a>';
                    return str
                }
            },
            {
                field: "recoverGather", title: "操作", width: 100, formatter: function (value, rowData, rowIndex) {
                    var str = '<a href="#" name="opera" class="easyui-linkbutton" onclick="recoverGather_day(' + rowIndex + ')">叠加采集</a>';
                    return str
                }
            },
        ]]

    });
    $('#dd_delete').textbox({
        width: 780,
        height: 200,
        multiline: true,
        editable: true,
    });
    $('#dd_insert').textbox({
        width: 780,
        height: 150,
        multiline: true,
        editable: true,
    });
    setResize();
})

function onClickGather_start() {
    alert("sssssaaass")
}

/**按照月重新采集**/
function reGatherData_month(rowIndex) {
    $('#dataQuality_Warning_Correction_Month').datagrid("selectRow", rowIndex);
    var rows = $("#dataQuality_Warning_Correction_Month").datagrid("getSelections");
    if (rows.length <= 0) {
        return;
    }
    $('#dd').dialog({
        title: '按照月重新采集',
        width: 800,
        height: 600,
        modal: true,
    });
    rowMonth = rows[0];
    $.ajax({
            url: 'get/reGatherData_month_deleteSql',
            data: {mysqlTableName: mysqlTableName, dataDate: rowMonth.dataDate},
            success: function (data) {
                $('#dd_delete').textbox("setText", data);
            }
        }
    );
    $.ajax({
            url: 'get/reGatherData_month_insertSql',
            data: {mysqlTableName: mysqlTableName, dataDate: rowMonth.dataDate},
            success: function (data) {
                $('#dd_insert').textbox("setText", data);
            }
        }
    );
    $.ajax({
            url: 'get/getGatherProgress',
            data: {mysqlTableName: mysqlTableName, dataDate: rowMonth.dataDate},
            success: function (data) {
                $('#gatherProgress').text(data);
            }
        }
    );
}
function getGatherProgress()
{
    $.ajax({
            url: 'get/getGatherProgress',
            data: {mysqlTableName: mysqlTableName, dataDate: rowMonth.dataDate},
            success: function (data) {
                $('#gatherProgress').text(data);
            }
        }
    );
}
/**按照月叠加采集**/
function recoverGather_month(rowIndex) {
    $('#dataQuality_Warning_Correction_Month').datagrid("selectRow", rowIndex);
    var rows = $("#dataQuality_Warning_Correction_Month").datagrid("getSelections");
    if (rows.length <= 0) {
        return;
    }
    $.messager.confirm("服务重启", "选中的服务是否重启?", function (r) {
        if (!r) {
            return;
        }
    });
    var row = rows[0];
    $.ajax({
        url: 'get/recoverGather_month',
        data: {mysqlTableName: mysqlTableName, dataDate: row.dataDate},
        success: function (data) {
            if (data) {
            }
        }
    });
}

/**按照日重新采集**/
function reGatherData_day(rowIndex) {
    $('#dataQuality_Warning_Correction_Day').datagrid("selectRow", rowIndex);
    var rows = $("#dataQuality_Warning_Correction_Day").datagrid("getSelections");
    if (rows.length <= 0) {
        return
    }
    $.messager.confirm("服务重启", "选中的服务是否重启?                                     s", function (r) {
        if (!r) {
            return;
        }
    });
    var row = rows[0];
    $.ajax({
        url: 'get/reGatherData_day',
        data: {mysqlTableName: mysqlTableName, dataDate: row.dataDate},
        success: function (data) {
            if (data) {
            }
        }
    });
}

/***按照日叠加采集***/
function recoverGather_day(rowIndex) {
    $('#dataQuality_Warning_Correction_Day').datagrid("selectRow", rowIndex);
    var rows = $("#dataQuality_Warning_Correction_Day").datagrid("getSelections");
    if (rows.length <= 0) {
        return;
    }
    $.messager.confirm("服务重启", "选中的服务是否重启?", function (r) {
        if (!r) {
            return;
        }
    });
    var row = rows[0];
    $.ajax({
        url: 'get/recoverGather_day',
        data: {mysqlTableName: mysqlTableName, dataDate: row.dataDate},
        success: function (data) {
            if (data) {
            }
        }
    });
}

function setResize() {
    windowHeight = $(document).height();
    idTop = $("#idTop").innerHeight();
    height = windowHeight - idTop - 20;
    //alert(height);
    $('#dataQuality_Warning_Correction_Month').datagrid("resize", {
        height: height,
    });
    $('#dataQuality_Warning_Correction_Day').datagrid("resize", {
        height: height,
    });
}

function setDataQuality_Day(rowData, mysqlTableName) {
    $('#dataQuality_Warning_Correction_Day').datagrid({
        method: 'post',
        url: 'get/getTableDataQualityDay',
        queryParams: {
            mysqlTableName: mysqlTableName,
            dataDate: rowData.dataDate,
        },
    });
}