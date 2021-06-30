$(function () {
    $('#delOnTimeGatherLayout').layout({
            fit: true,
        }
    );
    $("#cmbBatchType").combobox({});
    $("#btnSearchBatch").linkbutton({
        iconCls: 'icon-search',
    });

    $("#onTimeGatherBatch").datalist({
        url: 'get/getGatherBatch',
        queryParams: {
            status: '',
        },
        checkbox: false,
        lines: true,
        valueField: 'key',
        textField: 'value',
        onSelect: function (rowIndex, rowData) {
            var batchId = rowData.value;
            console.log(batchId);
            $("#batchId").textbox('setValue', batchId);

        }
    });
    $("#batchId").textbox({
        //iconCls: 'icon-cancel',
    });

    $("#btnDelBatch").linkbutton({
        iconCls: 'icon-cancel',
    });

    setResize();

    window.onresize = function () {
        setResize();
    }
});

function del_SelectBatch() {
    var status = $("#cmbBatchType").combobox("getValue")
    $("#onTimeGatherBatch").datalist({
        url: 'get/getGatherBatch',
        queryParams: {
            status: status,
        }
    });
}


function delBatch() {
    var batchId = $("#batchId").textbox('getValue');
    console.log("删除ID："+batchId)
    $.ajax({
        type: 'POST',
        url: 'get/delGatherBatch',
        data: {batchId: batchId},
    });
    del_SelectBatch();
}


function setResize() {
    var windowCenterHeight = $("#layoutCenter").innerHeight();
    var windowCenterWidth = $("#layoutCenter").width();
    console.log("Width=" + windowCenterWidth + ",Height=" + windowCenterHeight);
    $("#delOnTimeGatherLayout").height(windowCenterHeight);
    $("#delOnTimeGatherLayout").width(windowCenterWidth);

    $("#delOnTimeLayoutWest").height(windowCenterHeight);
    $("#delOnTimeLayoutCenter").height(windowCenterHeight);

}