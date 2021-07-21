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
            //console.log(batchId)
            setonTimeBatchInfo(batchId);
            setonTimeGatherInfo(batchId);
        },
        textFormatter: function (value, row, index) {
            if (row.key === 'ERROR') {
                return "<span style='color: red'>" + value + "</span>";
            } else {
                return value;
            }
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
        },
        columns: [[
            {field: 'gatherBatchID', title: "批次", width: 100},
            {
                field: 'gatherStatus', title: "采集状态", width: 100, formatter: function (value, rowData, rowIndex) {
                    if (value == 'ERROR') {
                        return "<span style='color: red'>" + value + "</span>";
                    } else {
                        return value;
                    }
                }
            },
            {field: 'analyseStatus', width: 100, title: "分析状态"},
            {field: 'impalaToMysqlStatus', width: 100, title: "导入mysql状态"},
            {field: 'analyseStatusUpdateTime', width: 150, title: "分析时间"},
            {field: 'createTime', width: 150, title: "创建时间"},
            {field: 'updateTime', width: 150, title: "修改时间"},
        ]],
        rowStyler: function (index, row) {
            if (row.gatherStatus === 'ERROR' || row.analyseStatus === 'ERROR') {
                return 'background-color:pink;color:red;font-weight:bold;';
            }
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
        columns: [[
            {field: 'taskId', width: 100, title: "taskId"},
            {field: 'startGather', width: 150, title: "startGather"},
            {field: 'endGather', width: 150, title: "endGather"},
            {field: 'batchId', width: 100, title: "批次"},
            {field: 'taskStatus', width: 100, title: "taskStatus"},
            {field: 'runStart', width: 150, title: "runStart"},
            {field: 'runEnd', width: 150, title: "runEnd"},
            {field: 'updateTime', width: 150, title: "updateTime"},
            {field: 'description', width: 100, title: "description"},
            {field: 'selectCnt', width: 100, title: "selectCnt"},
            {field: 'insertCnt', width: 100, title: "insertCnt"},
        ]],
        rowStyler: function (index, row) {
            if (row.taskStatus === 'ERROR') {
                return 'background-color:pink;color:red;font-weight:bold;';
            }
        }
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