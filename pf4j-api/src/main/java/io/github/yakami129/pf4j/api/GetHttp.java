package io.github.yakami129.pf4j.api;

import io.github.yakami129.pf4j.api.dto.GetHttpRequestDTO;
import org.pf4j.ExtensionPoint;

/**
 * Created by alan on 2023/5/16.
 * GET 请求的插件接口
 */
public interface GetHttp extends ExtensionPoint {

    /**
     * 发送Get请求
     *
     * @param getHttpRequestDTO 请求信息
     * @return 请求结果
     */
    String get(GetHttpRequestDTO getHttpRequestDTO);

}
