package com.ssh.model.impl;

import com.ssh.model.ModelType;
import com.ssh.model.api.AbstractModel;
import org.json.JSONObject;

import java.net.URI;

/**
 * 请求uri后的响应内容为JSON对象
 */
public class JSONObjectModel implements AbstractModel {
    private URI uri;
    private ModelType modelType;
    private JSONObject jsonObject;

    public JSONObjectModel(URI uri, JSONObject jsonObject) {
        this.modelType = ModelType.JSONObject;
        this.uri = uri;
        this.jsonObject = jsonObject;
    }

    public URI getURI() {
        return this.uri;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public Object getSourceEntity() {
        return this.jsonObject;
    }

    @Override
    public String toString() {
        return "JSONObjectModel{" +
                "uri=" + uri +
                ", modelType=" + modelType +
                ", jsonObject=" + jsonObject +
                '}';
    }
}

