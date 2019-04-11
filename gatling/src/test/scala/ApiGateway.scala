import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class ApiGateway extends Simulation {

  val springCloudGatewayConf = http.baseUrl("http://api-gateway:9090")


  val servlet = during(BenchmarkConfig.totalDuring seconds) {
    exec(
      http("servlet")
        .get(BenchmarkConfig.uri)
        .check(status.is(200))
    )
  }

  val scnSpringCloudGateway = scenario("API Gateway").exec(servlet)

  setUp(
    scnSpringCloudGateway.inject(
      rampUsersPerSec(BenchmarkConfig.rampFrom) to BenchmarkConfig.rampTo during (BenchmarkConfig.rampDuring seconds),
    ).protocols(springCloudGatewayConf)

  )
}
