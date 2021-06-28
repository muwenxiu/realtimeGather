$(function () {
    $('#btnSearchTable').linkbutton({
        iconCls: 'icon-search',
    });
    $('#cmbTableName').combobox({
        valueField:'key',
        textField:'value',
        url:'get/gatherTables',
    });
    selectGatherTable();
});

function selectGatherTable() {
    $('#mysqlTableInfo').datagrid({
        method: 'post',
        loadMsg: "正在加载数据。。。",
        url:'get/gatherTableColumns',
        queryParams:{
            mysqlTableName: $("#cmbTableName").combobox("getText"),
        }
    });

}

