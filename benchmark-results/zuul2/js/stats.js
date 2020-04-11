var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "1749321",
        "ok": "1749252",
        "ko": "69"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "10"
    },
    "maxResponseTime": {
        "total": "7523",
        "ok": "7523",
        "ko": "6004"
    },
    "meanResponseTime": {
        "total": "154",
        "ok": "154",
        "ko": "3939"
    },
    "standardDeviation": {
        "total": "209",
        "ok": "207",
        "ko": "2203"
    },
    "percentiles1": {
        "total": "80",
        "ok": "80",
        "ko": "5072"
    },
    "percentiles2": {
        "total": "337",
        "ok": "337",
        "ko": "5367"
    },
    "percentiles3": {
        "total": "1024",
        "ok": "1023",
        "ko": "5718"
    },
    "percentiles4": {
        "total": "2075",
        "ok": "2067",
        "ko": "5975"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 1729120,
        "percentage": 99
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 17831,
        "percentage": 1
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 2301,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 69,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "11662.14",
        "ok": "11661.68",
        "ko": "0.46"
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
        "total": "1749321",
        "ok": "1749252",
        "ko": "69"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "10"
    },
    "maxResponseTime": {
        "total": "7523",
        "ok": "7523",
        "ko": "6004"
    },
    "meanResponseTime": {
        "total": "154",
        "ok": "154",
        "ko": "3939"
    },
    "standardDeviation": {
        "total": "209",
        "ok": "207",
        "ko": "2203"
    },
    "percentiles1": {
        "total": "81",
        "ok": "80",
        "ko": "5072"
    },
    "percentiles2": {
        "total": "337",
        "ok": "337",
        "ko": "5367"
    },
    "percentiles3": {
        "total": "1024",
        "ok": "1023",
        "ko": "5718"
    },
    "percentiles4": {
        "total": "2075",
        "ok": "2067",
        "ko": "5975"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 1729120,
        "percentage": 99
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 17831,
        "percentage": 1
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 2301,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 69,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "11662.14",
        "ok": "11661.68",
        "ko": "0.46"
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
