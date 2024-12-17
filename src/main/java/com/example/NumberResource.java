
package com.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/numbers")
public class NumberResource {

    @Inject
    NumberService service;

    // Read: Get all numbers stored in the database
    @GET
    @Path("/all")
    public List<String> getAllNumbers() {
        return service.getAllNumbers();
    }

    // Create: Generate random numbers and store them in the database
    @POST
    @Path("/generate")
    public String generate(@QueryParam("count") int count) {
        service.generateRandomNumbers(count);
        return count + " random numbers generated and stored in the database.";
    }

    // Create: Insert a new number into the database, ensuring no duplicates
    @POST
    @Path("/insert")
    public String insertNumber(@QueryParam("number") String number) {
        try {
            service.insertNumber(number);
            return "Number inserted into the database: " + number;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    // Update: Update a number in the database
    @PUT
    @Path("/update/{oldNumber}")
    public String updateNumber(@PathParam("oldNumber") String oldNumber, @QueryParam("newNumber") String newNumber) {
        try {
            service.updateNumber(oldNumber, newNumber);
            return "Number updated from " + oldNumber + " to " + newNumber;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    // Delete: Delete a number from the database
    @DELETE
    @Path("/delete")
    public String deleteNumber(@QueryParam("number") String number) {
        try {
            service.deleteNumber(number);
            return "Number deleted from the database: " + number;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    // Verify if a number exists in the database
    @GET
    @Path("/exists")
    public boolean numberExists(@QueryParam("number") String number) {
        return service.existsInDb(number);
    }
}
