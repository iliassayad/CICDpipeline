package net.ayad.firstcicdpipeline.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Test")
class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleProductNotFoundException_whenHandleProductNotFoundExceptionIsThrown_shouldReturnApiErrorResponse() {

        //Arrange
        Long productId = 1L;
        ProductNotFoundException exception = new ProductNotFoundException(productId);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/products/" + productId);
        //Act
        var responseEntity = globalExceptionHandler.handleProductNotFoundException(exception, request);
        //Assert
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals("PRODUCT_NOT_FOUND", responseEntity.getBody().getError());
        assertEquals("Product not found with ID: " + productId, responseEntity.getBody().getMessage());
        assertEquals("/api/products/" + productId, responseEntity.getBody().getPath());
    }

}