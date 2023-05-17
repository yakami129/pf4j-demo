package io.github.yakami129.pf4j.api.dto;

/**
 * Created by alan on 2023/5/16.
 */
public class GetHttpRequestDTO {

    private String url;

    public GetHttpRequestDTO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
