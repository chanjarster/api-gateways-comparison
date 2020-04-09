import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class Tomcat extends Simulation {

  val tomcatConf = http.baseUrl("http://tomcat:8080")

  val servlet = during(BenchmarkConfig.totalDuring seconds) {
    exec(
      http("servlet")
        .get(BenchmarkConfig.uri)
        .check(status.is(200), substring("</html>"))
    )
  }

  val scnTomcat = scenario("Tomcat").exec(servlet)

  setUp(
    scnTomcat.inject(
      rampUsersPerSec(BenchmarkConfig.rampFrom) to BenchmarkConfig.rampTo during (BenchmarkConfig.rampDuring seconds),
    ).protocols(tomcatConf),
  )
}
