var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "841308",
        "ok": "840672",
        "ko": "636"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "91"
    },
    "maxResponseTime": {
        "total": "60001",
        "ok": "22613",
        "ko": "60001"
    },
    "meanResponseTime": {
        "total": "321",
        "ok": "319",
        "ko": "3309"
    },
    "standardDeviation": {
        "total": "619",
        "ok": "609",
        "ko": "2976"
    },
    "percentiles1": {
        "total": "164",
        "ok": "164",
        "ko": "3245"
    },
    "percentiles2": {
        "total": "569",
        "ok": "564",
        "ko": "5297"
    },
    "percentiles3": {
        "total": "3586",
        "ok": "3551",
        "ko": "6509"
    },
    "percentiles4": {
        "total": "6442",
        "ok": "6440",
        "ko": "26064"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 792048,
        "percentage": 94
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 26308,
        "percentage": 3
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 22316,
        "percentage": 3
    },
    "group4": {
        "name": "failed",
        "count": 636,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "5427.794",
        "ok": "5423.69",
        "ko": "4.103"
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
        "total": "841308",
        "ok": "840672",
        "ko": "636"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "0",
        "ko": "91"
    },
    "maxResponseTime": {
        "total": "60001",
        "ok": "22613",
        "ko": "60001"
    },
    "meanResponseTime": {
        "total": "321",
        "ok": "319",
        "ko": "3309"
    },
    "standardDeviation": {
        "total": "619",
        "ok": "609",
        "ko": "2976"
    },
    "percentiles1": {
        "total": "164",
        "ok": "164",
        "ko": "3245"
    },
    "percentiles2": {
        "total": "567",
        "ok": "564",
        "ko": "5297"
    },
    "percentiles3": {
        "total": "3569",
        "ok": "3551",
        "ko": "6509"
    },
    "percentiles4": {
        "total": "6442",
        "ok": "6440",
        "ko": "26064"
    },
    "group1": {
        "name": "t < 1000 ms",
        "count": 792048,
        "percentage": 94
    },
    "group2": {
        "name": "1000 ms < t < 2000 ms",
        "count": 26308,
        "percentage": 3
    },
    "group3": {
        "name": "t > 2000 ms",
        "count": 22316,
        "percentage": 3
    },
    "group4": {
        "name": "failed",
        "count": 636,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "5427.794",
        "ok": "5423.69",
        "ko": "4.103"
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
