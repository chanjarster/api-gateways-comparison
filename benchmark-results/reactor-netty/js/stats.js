var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "2440396",
        "ok": "2440391",
        "ko": "5"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "10193"
    },
    "maxResponseTime": {
        "total": "10264",
        "ok": "7527",
        "ko": "10264"
    },
    "meanResponseTime": {
        "total": "111",
        "ok": "111",
        "ko": "10238"
    },
    "standardDeviation": {
        "total": "111",
        "ok": "110",
        "ko": "27"
    },
    "percentiles1": {
        "total": "82",
        "ok": "82",
        "ko": "10250"
    },
    "percentiles2": {
        "total": "216",
        "ok": "216",
        "ko": "10263"
    },
    "percentiles3": {
        "total": "532",
        "ok": "532",
        "ko": "10264"
    },
    "percentiles4": {
        "total": "1327",
        "ok": "1326",
        "ko": "10264"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 2434857,
        "percentage": 100
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 5317,
        "percentage": 0
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 217,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 5,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "16269.307",
        "ok": "16269.273",
        "ko": "0.033"
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
        "total": "2440396",
        "ok": "2440391",
        "ko": "5"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "10193"
    },
    "maxResponseTime": {
        "total": "10264",
        "ok": "7527",
        "ko": "10264"
    },
    "meanResponseTime": {
        "total": "111",
        "ok": "111",
        "ko": "10238"
    },
    "standardDeviation": {
        "total": "111",
        "ok": "110",
        "ko": "27"
    },
    "percentiles1": {
        "total": "82",
        "ok": "82",
        "ko": "10250"
    },
    "percentiles2": {
        "total": "216",
        "ok": "216",
        "ko": "10263"
    },
    "percentiles3": {
        "total": "532",
        "ok": "532",
        "ko": "10264"
    },
    "percentiles4": {
        "total": "1327",
        "ok": "1327",
        "ko": "10264"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 2434857,
        "percentage": 100
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 5317,
        "percentage": 0
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 217,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 5,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "16269.307",
        "ok": "16269.273",
        "ko": "0.033"
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
