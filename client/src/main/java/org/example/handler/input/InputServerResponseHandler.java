package org.example.handler.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputServerResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(InputServerResponseHandler.class);

    public void handle(String message) {
        logger.info("Received from server: {}", message);
    }
}
