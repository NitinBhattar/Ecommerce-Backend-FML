package com.ecommercefml.payment.util;

import java.util.UUID;

public class TransactionGenerator {
    public static String generate() {
        return "TXN-" + UUID.randomUUID();
    };
}
