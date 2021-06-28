$(function () {
    $('#btnSearchTable').linkbutton({
        iconCls: 'icon-search',
    });
    $('#cmbTableName').combobox({
        url:'',
        valueField:'id',
        textField:'text'
    });
    selectGatherTable();
});

function selectGatherTable() {
    $('#mysqlTableInfo').datagrid({
        method: 'post',
        loadMsg: "正在加载数据。。。",
        url:'',
    });

}

