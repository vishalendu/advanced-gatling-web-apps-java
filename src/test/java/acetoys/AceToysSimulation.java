package acetoys;

import acetoys.pageobjects.*;
import acetoys.session.UserSession;
import acetoys.simulation.TestPopulation;
import acetoys.simulation.TestScenario;
import acetoys.simulation.UserJourney;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

//private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");
    private static final String TEST_TYPE = System.getenv("TEST_TYPE");

  private static final String DOMAIN = "acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-GB,en;q=0.9");

  {
      if (TEST_TYPE.equals( "INSTANT_USERS")) {
          setUp(TestPopulation.instantUsers).protocols(httpProtocol).assertions(
                  global().successfulRequests().percent().is(100.0)
                  //,global().responseTime().percentile(0.99).lt(100)
                  );
      } else if (TEST_TYPE.equals("RAMP_USERS")) {
          setUp(TestPopulation.rampUsers).protocols(httpProtocol).assertions(
                  global().successfulRequests().percent().is(100.0)
          );
      } else if (TEST_TYPE.equals("COMPLEX_INJECTION")) {
          setUp(TestPopulation.complexInjection).protocols(httpProtocol).assertions(
                  global().successfulRequests().percent().is(100.0)
          );
      } else if (TEST_TYPE.equals("CLOSED_MODEL")) {
          setUp(TestPopulation.closedModel).protocols(httpProtocol).assertions(
                  global().successfulRequests().percent().is(100.0)
          );
      } else {
          setUp(TestPopulation.instantUsers).protocols(httpProtocol).assertions(
                  global().successfulRequests().percent().is(100.0)
          );
      }
  }

}
