package com.ssh.model.impl;

import com.ssh.model.ModelType;
import com.ssh.model.api.AbstractModel;
import org.jsoup.nodes.Document;

import java.net.URI;

/**
 * 请求uri后的响应内容为html页面
 */
public class HtmlModel implements AbstractModel {
    private URI uri;
    private ModelType modelType;
    private Document html;

    public HtmlModel(URI uri, Document html) {
        this.modelType = ModelType.HTML;
        this.uri = uri;
        this.html = html;
    }

    public URI getURI() {
        return this.uri;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public Object getSourceEntity() {
        return this.html;
    }

    @Override
    public String toString() {
        return "HtmlModel{" +
                "uri=" + uri +
                ", modelType=" + modelType +
                ", html=" + html +
                '}';
    }
}

