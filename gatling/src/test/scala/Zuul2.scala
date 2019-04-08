import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class Zuul2 extends Simulation {

  val zuul2Conf = http.baseUrl("http://localhost:8081")

  val book = during(BenchmarkConfig.totalDuring seconds) {
    exec(
      http("book")
        .get("/examples/jsp/jsp2/simpletag/book.jsp")
        .check(status.is(200))
    )
  }

  val scnZuul2 = scenario("Zuul2").exec(book)

  setUp(
    scnZuul2.inject(
      rampUsersPerSec(BenchmarkConfig.rampFrom) to BenchmarkConfig.rampTo during (BenchmarkConfig.rampDuring seconds),
    ).protocols(zuul2Conf),
  )
}
