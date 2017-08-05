package com.baili.springboot.core.util.http;

import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

import java.util.Locale;

/**
 * Created by wxl on 2016/7/22.
 */
public class ResponseResult {
    private Header[] headers;       //响应头
    private StatusLine statusLine;  //状态行
    private byte[] byteContent; //响应字节内容
    private String stringContent;//响应字符串内容
    private Locale locale;
    private ProtocolVersion protocolVersion;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public byte[] getByteContent() {
        return byteContent;
    }

    public void setByteContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Header getFirstHeader(final String name) {
        // HTTPCORE-361 : we don't use the for-each syntax, i.e.
        //     for (Header header : headers)
        // as that creates an Iterator that needs to be garbage-collected
        for (int i = 0; i < this.headers.length; i++) {
            final Header header = this.headers[i];
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }
}
