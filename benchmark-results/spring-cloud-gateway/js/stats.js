var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "1613710",
        "ok": "1613710",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "20106",
        "ok": "20106",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "167",
        "ok": "167",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "233",
        "ok": "233",
        "ko": "-"
    },
    "percentiles1": {
        "total": "83",
        "ok": "83",
        "ko": "-"
    },
    "percentiles2": {
        "total": "377",
        "ok": "377",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1033",
        "ok": "1033",
        "ko": "-"
    },
    "percentiles4": {
        "total": "2067",
        "ok": "2067",
        "ko": "-"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 1593474,
        "percentage": 99
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 18110,
        "percentage": 1
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 2126,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "10758.067",
        "ok": "10758.067",
        "ko": "-"
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
        "total": "1613710",
        "ok": "1613710",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "20106",
        "ok": "20106",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "167",
        "ok": "167",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "233",
        "ok": "233",
        "ko": "-"
    },
    "percentiles1": {
        "total": "83",
        "ok": "83",
        "ko": "-"
    },
    "percentiles2": {
        "total": "377",
        "ok": "377",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1033",
        "ok": "1033",
        "ko": "-"
    },
    "percentiles4": {
        "total": "2067",
        "ok": "2067",
        "ko": "-"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 1593474,
        "percentage": 99
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 18110,
        "percentage": 1
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 2126,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "10758.067",
        "ok": "10758.067",
        "ko": "-"
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
