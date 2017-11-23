package com.edeliverables.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base58Test {
    private static Logger logger = LoggerFactory.getLogger(Base58Test.class);

    @Test
    public void miniToWif() throws Exception {
        String mini = "S6c56bnXQiBjk9mqSYE7ykVQ7NzrRy";
        logger.info("mini: " + mini);
        logger.info("wif: " + Casascius.miniToWif(mini));
    }
}