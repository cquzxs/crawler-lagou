package com.ssh.model.impl;

import com.ssh.model.ModelType;
import com.ssh.model.api.AbstractModel;

import java.net.URI;
import java.net.URISyntaxException;

public class CloseModel implements AbstractModel {
    private URI uri;
    private ModelType modelType;
    private Object close = null;

    public CloseModel() {
        try {
            this.uri = new URI("https://www.yun-kai.com");
            this.modelType = ModelType.CLOSE;
        } catch (URISyntaxException var2) {
            var2.printStackTrace();
        }

    }

    public URI getURI() {
        return this.uri;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public Object getSourceEntity() {
        return this.close;
    }
}

