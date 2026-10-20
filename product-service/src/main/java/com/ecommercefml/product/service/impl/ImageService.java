package com.ecommercefml.product.service.impl;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface ImageService {
    Resource getImage(String fileName) throws IOException;
}
