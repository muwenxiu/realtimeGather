$(function () {
    $('#idGetCanalProgress').linkbutton({
        iconCls: 'icon-search',
    });
    getCanalProgress();
});

function getCanalProgress() {
    $("#realtimeGather_canalChannelProgress").datagrid(
        {
            method: 'post',
            loadMsg: "正在加载数据。。。",
            url: 'get/getCanalGatherProgress',
        }
    );
}