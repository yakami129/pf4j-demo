package io.github.yakami129.plugin;

import io.github.yakami129.pf4j.api.GetHttp;
import io.github.yakami129.pf4j.api.dto.GetHttpRequestDTO;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alan on 2023/5/16.
 */
public class GetHttpPlugin extends Plugin {

    private static final Logger logger = LoggerFactory.getLogger(GetHttpPlugin.class);

    public GetHttpPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        logger.info("[PLUGIN] start GetHttpPlugin");
    }

    @Override
    public void stop() {
        logger.info("[PLUGIN] stop GetHttpPlugin");
    }

    @Extension
    public static class GetHttpPluginPluginImpl implements GetHttp {

        @Override
        public String get(GetHttpRequestDTO getHttpRequestDTO) {
            return null;
        }
    }

}
