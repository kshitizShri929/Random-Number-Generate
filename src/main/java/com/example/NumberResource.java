package com.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/hello")
public class NumberResource {

    @Inject
    NumberService service;
   
    @GET
    @Path("/valley")
    public Set<String> getValley() {
        return service.getValley();
    }
    @POST
    @Path("/generate")
    public String generate(@QueryParam("count") int count) {
        service.generateRandomNumbers(count);
        return count + " random numbers generated and stored in the database.";
    }
    @POST
    @Path("/load")
    public String loadIntoVString(){
        service.loadNumbersIntoValley();
        return "Number loaded into valley";

    }
    @POST
    @Path("/insert")
    public String instertIntovallay (@QueryParam("number")String number){
        try{
            service.insertIntoValley(number);
            return "Number insterted into valley: " +number;
        } catch (IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    
}
