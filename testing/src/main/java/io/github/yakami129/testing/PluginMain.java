package io.github.yakami129.testing;

import io.github.yakami129.pf4j.api.GetHttp;
import io.github.yakami129.pf4j.api.dto.GetHttpRequestDTO;
import io.github.yakami129.testing.plugin.PluginManagerScanner;
import io.github.yakami129.testing.plugin.PluginManagerService;
import io.github.yakami129.testing.plugin.PluginProperties;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginManager;

/**
 * Created by alan on 2023/5/17.
 */
@Slf4j
public class PluginMain {

    public static void main(String[] args) {

        // 初始化plugin的配置
        PluginProperties pluginProperties = new PluginProperties();
        final String pluginsUrl = PluginMain.class.getResource("/plugins").toString();
        log.info("pluginsUrl:{}", pluginsUrl);
        pluginProperties.setPluginPath(pluginsUrl);

        // 扫描plugin包，获取插件管理器
        PluginManagerScanner pluginManagerScanner = new PluginManagerScanner(pluginProperties);
        final PluginManager pluginManager = pluginManagerScanner.getPluginManager();

        // 初始化插件管理器服务，用于业务操作
        PluginManagerService pluginManagerService = new PluginManagerService(pluginManager);

        // 执行GetHttp插件
        final GetHttp getHttp = pluginManagerService.getExtensions(GetHttp.class, "gethttp-plugin");
        final String responseBody = getHttp.get(new GetHttpRequestDTO("www.baidu.com"));
        log.info("responseBody:{}", responseBody);
    }

}
