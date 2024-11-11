package com.colak;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.temporal.ChronoUnit;

@RegisterRestClient(baseUri = "http://localhost:8080/api/slow")
public interface ApiToleranceClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    // after 2 seconds the application will return a TimeoutException
    @Timeout(unit = ChronoUnit.SECONDS, value = 2)

    // maxRetries indicate the number of retries our client API will try to call the server API before launching an Exception
    // delay indicates the time between these attempts.
    @Retry(delayUnit = ChronoUnit.SECONDS, maxRetries = 2, delay = 1)

    // By default, we can configure what we call a fallbackMethod which defines a method that will be called when we get an error,
    // in our case, the TimeoutException
    @Fallback(fallbackMethod = "defaultFallback")

    // requestVolumeThreshold indicates the number of requests that we will analyze in order to be sure that we have a problem.
    // In our example, we set 4 as the value, which means that we will analyze 4 requests before affirming that we have a problem.

    // failureRation indicates the percentage of failures that we will consider to affirm that we are indeed having a problem.
    // We set .75 as the value, which means if 75% of the requests (3 of 4) have a problem, then we have a problem in fact.

    // delay indicates the time between these attempts.

    // successThreshold indicates how many requests made successfully we need to confirm that the problem has been resolved,
    // and we can turn on the light switch again (close our open circuit).
    @CircuitBreaker(delayUnit = ChronoUnit.SECONDS,
            requestVolumeThreshold = 4,
            failureRatio = .75,
            delay = 3,
            successThreshold = 2
    )
    String getDataFromSlowAPI();

    default String defaultFallback() {
        return "It seems that the server is down! We need to define a better response to the final user \n";
    }

}
