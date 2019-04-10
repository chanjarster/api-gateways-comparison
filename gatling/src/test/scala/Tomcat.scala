import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class Tomcat extends Simulation {

  val tomcatConf = http.baseUrl("http://tomcat:9090")

  val book = during(BenchmarkConfig.totalDuring seconds) {
    exec(
      http("book")
        .get("/examples/jsp/jsp2/simpletag/book.jsp")
        .check(status.is(200))
    )
  }

  val scnTomcat = scenario("Tomcat").exec(book)

  setUp(
    scnTomcat.inject(
      rampUsersPerSec(BenchmarkConfig.rampFrom) to BenchmarkConfig.rampTo during (BenchmarkConfig.rampDuring seconds),
    ).protocols(tomcatConf),
  )
}
