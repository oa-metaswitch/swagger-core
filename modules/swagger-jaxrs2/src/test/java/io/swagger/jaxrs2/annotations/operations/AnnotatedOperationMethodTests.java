package io.swagger.jaxrs2.annotations.operations;

import io.swagger.jaxrs2.annotations.AbstractAnnotationTest;
import io.swagger.jaxrs2.resources.PetResource;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.ExampleObject;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
import org.testng.annotations.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static org.testng.Assert.assertEquals;

public class AnnotatedOperationMethodTests extends AbstractAnnotationTest {

    @Test
    public void testSimpleGetOperation() {
        String openApiYAML = readIntoYaml(SimpleGetOperationTest.class);
        int start = openApiYAML.indexOf("get:");
        int end = openApiYAML.length() - 1;

        String expectedYAML = "get:\n" +
                "      summary: \"Simple get operation\"\n" +
                "      description: \"Defines a simple get operation with no inputs and a complex\"\n" +
                "      operationId: \"getWithNoParameters\"\n" +
                "      responses:\n" +
                "        200:\n" +
                "          description: \"voila!\"\n" +
                "      deprecated: true";
        String extractedYAML = openApiYAML.substring(start, end);

        assertEquals(extractedYAML, expectedYAML);
    }

    static class SimpleGetOperationTest {
        @Operation(
                summary = "Simple get operation",
                description = "Defines a simple get operation with no inputs and a complex",
                operationId = "getWithNoParameters",
                deprecated = true,
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "voila!")
                }
        )
        @GET
        @Path("/path")
        public void simpleGet() {
        }
    }

    @Test
    public void testGetOperationWithResponsePayloadAndAlternateCodes() {
        String openApiYAML = readIntoYaml(GetOperationWithResponsePayloadAndAlternateCodes.class);
        int start = openApiYAML.indexOf("get:");
        int end = openApiYAML.length() - 1;
        String extractedYAML = openApiYAML.substring(start, end);
        String expectedYAML = "get:\n" +
                "      summary: \"Simple get operation\"\n" +
                "      description: \"Defines a simple get operation with no inputs and a complex\"\n" +
                "      operationId: \"getWithPayloadResponse\"\n" +
                "      responses:\n" +
                "        200:\n" +
                "          description: \"voila!\"\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                type: \"object\"\n" +
                "                properties:\n" +
                "                  id:\n" +
                "                    type: \"string\"\n" +
                "                    description: \"the user id\"\n" +
                "        default:\n" +
                "          description: \"boo\"\n" +
                "          content:\n" +
                "            '*/*':\n" +
                "              schema:\n" +
                "                type: \"object\"\n" +
                "      deprecated: true";

        assertEquals(extractedYAML, expectedYAML);
    }

    static class GetOperationWithResponsePayloadAndAlternateCodes {
        @Operation(
                summary = "Simple get operation",
                description = "Defines a simple get operation with no inputs and a complex",
                operationId = "getWithPayloadResponse",
                deprecated = true,
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "voila!",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = SampleResponseSchema.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "default",
                                description = "boo",
                                content = @Content(
                                        mediaType = "*/*",
                                        schema = @Schema(implementation = GenericError.class)
                                )
                        )
                }
        )
        @Path("/path")
        @GET
        public void simpleGet() {
        }
    }

    static class SampleResponseSchema {
        @Schema(description = "the user id")
        public String id;
    }

    static class GenericError {
        private int code;
        private String message;
    }

    @Test(description = "reads an operation with response examples defined")
    public void testOperationWithResponseExamples() {
        String openApiYAML = readIntoYaml(GetOperationWithResponseExamples.class);
        int start = openApiYAML.indexOf("get:");
        int end = openApiYAML.length() - 1;
        String extractedYAML = openApiYAML.substring(start, end);
        String expectedYAML = "get:\n" +
                "      summary: \"Simple get operation\"\n" +
                "      description: \"Defines a simple get operation with no inputs and a complex output\"\n" +
                "      operationId: \"getWithPayloadResponse\"\n" +
                "      responses:\n" +
                "        200:\n" +
                "          description: \"voila!\"\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                type: \"object\"\n" +
                "                properties:\n" +
                "                  id:\n" +
                "                    type: \"string\"\n" +
                "                    description: \"the user id\"\n" +
                "              examples:\n" +
                "                basic:\n" +
                "                  summary: \"shows a basic example\"\n" +
                "                  description: \"basic\"\n" +
                "                  value: \"{\\\"id\\\": 19877734}\"\n" +
                "      deprecated: true";
        assertEquals(extractedYAML, expectedYAML);
    }

    static class GetOperationWithResponseExamples {
        @Operation(
                summary = "Simple get operation",
                description = "Defines a simple get operation with no inputs and a complex output",
                operationId = "getWithPayloadResponse",
                deprecated = true,
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "voila!",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = SampleResponseSchema.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "basic",
                                                        summary = "shows a basic example",
                                                        value = "{\"id\": 19877734}")
                                        }
                                )
                        )
                }
        )
        @GET
        @Path("/path")
        public void simpleGet() {
        }
    }

    @Test(description = "reads an operation from sample")
    public void testCompleteOperation() {
        String openApiYAML = readIntoYaml(PetResource.class);
        int start = 0;
        int end = openApiYAML.length() - 1;
        String extractedYAML = openApiYAML.substring(start, end);
        String expectedYAML = "get:\n" +
                "      summary: \"Simple get operation\"\n" +
                "      description: \"Defines a simple get operation with no inputs and a complex output\"\n" +
                "      operationId: \"getWithPayloadResponse\"\n" +
                "      responses:\n" +
                "        200:\n" +
                "          description: \"voila!\"\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                type: \"object\"\n" +
                "                properties:\n" +
                "                  id:\n" +
                "                    type: \"string\"\n" +
                "                    description: \"the user id\"\n" +
                "              examples:\n" +
                "                basic:\n" +
                "                  summary: \"shows a basic example\"\n" +
                "                  description: \"basic\"\n" +
                "                  value: \"{\\\"id\\\": 19877734}\"\n" +
                "      deprecated: true";
        assertEquals(extractedYAML, expectedYAML);
    }
}
