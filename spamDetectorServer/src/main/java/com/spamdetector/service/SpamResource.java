package com.spamdetector.service;

import com.spamdetector.domain.TestFile;
import com.spamdetector.util.SpamDetector;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.io.File;
import java.util.List;

import jakarta.ws.rs.core.Response;

@Path("/spam")
public class SpamResource {

    // your SpamDetector Class responsible for all the SpamDetecting logic
    SpamDetector detector = new SpamDetector();

    SpamResource(){
        // load resources, train and test to improve performance on the endpoint calls
        System.out.print("Training and testing the model, please wait");
        this.trainAndTest();
    }

    @GET
    @Produces("application/json")
    public Response getSpamResults() {
        // return the test results list of TestFile, return in a Response object
        List<TestFile> testResults = detector.getTestResults();
        return Response.ok(testResults).build();
    }

    @GET
    @Path("/accuracy")
    @Produces("application/json")
    public Response getAccuracy() {
        // return the accuracy of the detector, return in a Response object
        double accuracy = detector.getAccuracy();
        return Response.ok("{\"val\": " + accuracy + "}").build();
    }

    @GET
    @Path("/precision")
    @Produces("application/json")
    public Response getPrecision() {
        // return the precision of the detector, return in a Response object
        double precision = detector.getPrecision();
        return Response.ok("{\"val\": " + precision + "}").build();
    }

    private void trainAndTest() {
        if (this.detector==null){
            this.detector = new SpamDetector();
        }
        // load the main directory "data" here from the Resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File mainDirectory = new File(classLoader.getResource("resources/data").getFile());
        this.detector.trainAndTest(mainDirectory);
    }
}
