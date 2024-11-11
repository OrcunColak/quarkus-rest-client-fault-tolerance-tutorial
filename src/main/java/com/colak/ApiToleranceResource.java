package com.colak;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api")
public class ApiToleranceResource {

    @RestClient
    @Inject
    ApiToleranceClient apiToleranceClient;

    // http://localhost:8080/api
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDataFromSlowAPI() {
        return apiToleranceClient.getDataFromSlowAPI();
    }
}
