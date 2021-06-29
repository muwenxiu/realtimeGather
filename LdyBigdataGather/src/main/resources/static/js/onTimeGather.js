$(function () {
    $('#onTimeGatherLayout').layout({
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
            console.log(batchId)
            setonTimeBatchInfo(batchId);
            setonTimeGatherInfo(batchId);
        }
    });
    setonTimeBatchInfo(1234);
    setonTimeGatherInfo(1234);

    setResize();

    window.onresize = function () {
        setResize();
    }
});

function selectBatch() {
    var status = $("#cmbBatchType").combobox("getValue")
    $("#onTimeGatherBatch").datalist({
        url: 'get/getGatherBatch',
        queryParams: {
            status: status,
        }
    });
}

function setonTimeBatchInfo(batchId) {
    $("#onTimeBatchInfo").datagrid({
        method: 'post',
        showHeader: true,
        noheader: true,
        loadMsg: "正在加载数据。。。",
        url: 'get/getDataGatherBatchInfo',
        queryParams: {
            batch: batchId,
        }
    });
}

function setonTimeGatherInfo(batchId) {
    $("#onTimeGatherInfo").datagrid({
        method: 'post',
        showHeader: true,
        noheader: true,
        loadMsg: "正在加载数据。。。",
        url: 'get/getDataGatherInfo',
        queryParams: {
            batch: batchId,
        },
    });
}

function setResize() {
    var windowCenterHeight = $("#layoutCenter").innerHeight();
    var windowCenterWidth = $("#layoutCenter").width();
    console.log("Width=" + windowCenterWidth + ",Height=" + windowCenterHeight);
    $("#onTimeGatherLayout").height(windowCenterHeight);
    $("#onTimeGatherLayout").width(windowCenterWidth);

    $("#onTimeLayoutWest").height(windowCenterHeight);
    $("#onTimeLayoutCenter").height(windowCenterHeight);
    $("#onTimeBatchInfo").datagrid("resize", {
        height: 130,
    });
}