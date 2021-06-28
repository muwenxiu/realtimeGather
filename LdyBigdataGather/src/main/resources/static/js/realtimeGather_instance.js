$(function () {
    selectInstance();
});

function selectInstance() {
    $("#mysqlDatabaseTable").datagrid({
        method: 'post',
        loadMsg: "正在加载数据。。。",
        url: '/get/mysqlDatabase',
        onLoadSuccess: function (data) {
            console.log(data);
        },
    });
};