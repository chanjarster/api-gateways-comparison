import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class ApiGateway extends Simulation {

  val springCloudGatewayConf = http.baseUrl("http://api-gateway:9090")


  val book = during(BenchmarkConfig.totalDuring seconds) {
    exec(
      http("book")
        .get("/examples/jsp/jsp2/simpletag/book.jsp")
        .check(status.is(200))
    )
  }

  val scnSpringCloudGateway = scenario("API Gateway").exec(book)

  setUp(
    scnSpringCloudGateway.inject(
      rampUsersPerSec(BenchmarkConfig.rampFrom) to BenchmarkConfig.rampTo during (BenchmarkConfig.rampDuring seconds),
    ).protocols(springCloudGatewayConf)

  )
}
