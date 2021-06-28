$(function () {
    $('#idGatherCanalChannelStatus').linkbutton(
        {
            iconCls: 'icon-search',
        }
    );
    gatherCanalChannelStatus();
});

function gatherCanalChannelStatus() {
    $("#gatherStatusTable").datagrid(
        {
            method: 'post',
            loadMsg: "正在加载数据。。。",
            url: 'get/getCanalChannel',
        }
    );
}