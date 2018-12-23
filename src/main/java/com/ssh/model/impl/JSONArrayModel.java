package com.ssh.model.impl;

import com.ssh.model.ModelType;
import com.ssh.model.api.AbstractModel;
import org.json.JSONArray;

import java.net.URI;

/**
 * 请求uri后的响应内容为JSON数组
 */
public class JSONArrayModel implements AbstractModel {
    private URI uri;
    private ModelType modelType;
    private JSONArray jsonArray;

    public JSONArrayModel(URI uri, JSONArray jsonArray) {
        this.modelType = ModelType.JSONArray;
        this.uri = uri;
        this.jsonArray = jsonArray;
    }

    public URI getURI() {
        return this.uri;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public Object getSourceEntity() {
        return this.jsonArray;
    }

    @Override
    public String toString() {
        return "JSONArrayModel{" +
                "uri=" + uri +
                ", modelType=" + modelType +
                ", jsonArray=" + jsonArray +
                '}';
    }
}

