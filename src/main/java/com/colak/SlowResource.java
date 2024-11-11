package com.colak;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/slow")
public class SlowResource {

    // http://localhost:8080/api/slow
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws InterruptedException {
        // this simulates a delay of 3s
        Thread.sleep(3000L);
        return "Hi there, I'm a slow API \n";
    }
}
