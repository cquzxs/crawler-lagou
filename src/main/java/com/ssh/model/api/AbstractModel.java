package com.ssh.model.api;

import com.ssh.model.ModelType;

import java.net.URI;

/**
 * 这是请求uri后的响应内容的抽象化
 */
public interface AbstractModel{
    URI getURI();

    ModelType getModelType();

    Object getSourceEntity();
}
