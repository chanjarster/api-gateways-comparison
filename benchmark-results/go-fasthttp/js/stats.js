var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "2552029",
        "ok": "2551348",
        "ko": "681"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "3001"
    },
    "maxResponseTime": {
        "total": "4980",
        "ok": "4980",
        "ko": "3600"
    },
    "meanResponseTime": {
        "total": "106",
        "ok": "105",
        "ko": "3209"
    },
    "standardDeviation": {
        "total": "126",
        "ok": "115",
        "ko": "165"
    },
    "percentiles1": {
        "total": "78",
        "ok": "78",
        "ko": "3186"
    },
    "percentiles2": {
        "total": "193",
        "ok": "192",
        "ko": "3485"
    },
    "percentiles3": {
        "total": "547",
        "ok": "544",
        "ko": "3532"
    },
    "percentiles4": {
        "total": "1384",
        "ok": "1304",
        "ko": "3558"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 2542608,
        "percentage": 100
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 8269,
        "percentage": 0
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 471,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 681,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "17013.527",
        "ok": "17008.987",
        "ko": "4.54"
    }
},
contents: {
"req_servlet-a0c5c": {
        type: "REQUEST",
        name: "servlet",
path: "servlet",
pathFormatted: "req_servlet-a0c5c",
stats: {
    "name": "servlet",
    "numberOfRequests": {
        "total": "2552029",
        "ok": "2551348",
        "ko": "681"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "3001"
    },
    "maxResponseTime": {
        "total": "4980",
        "ok": "4980",
        "ko": "3600"
    },
    "meanResponseTime": {
        "total": "106",
        "ok": "105",
        "ko": "3209"
    },
    "standardDeviation": {
        "total": "126",
        "ok": "115",
        "ko": "165"
    },
    "percentiles1": {
        "total": "78",
        "ok": "78",
        "ko": "3186"
    },
    "percentiles2": {
        "total": "193",
        "ok": "192",
        "ko": "3485"
    },
    "percentiles3": {
        "total": "547",
        "ok": "544",
        "ko": "3532"
    },
    "percentiles4": {
        "total": "1382",
        "ok": "1304",
        "ko": "3558"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 2542608,
        "percentage": 100
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 8269,
        "percentage": 0
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 471,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 681,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "17013.527",
        "ok": "17008.987",
        "ko": "4.54"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
