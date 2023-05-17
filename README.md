# pf4j-demo

这是一个集成pf4j插件框架demo

# 项目结构说明

- pf4j-api: 定义插件接口
- plugins: 插件具体实现
- testing: 测试插件方法，运行的插件需要存放在resources/plugins中，可以将testing中的代码copy到项目中，用于执行插件
- build: plugins中的插件build后，会产生插件的zip包，存放在该目录中

在使用的过程中，为了避免不必要的问题，请严格安装上述项目结构扩展。

# 快速开始

### （1）在pf4j-api中定义GetHttpPlugin插件接口

```java
public interface GetHttp extends ExtensionPoint {

    /**
     * 发送Get请求
     *
     * @param getHttpRequestDTO 请求信息
     * @return 请求结果
     */
    String get(GetHttpRequestDTO getHttpRequestDTO);

}
```

注意：

1. 所有的插件接口，必须继承ExtensionPoint
2. 根据需求可以定义多个方法

### （2）在plugins中实现getHttp-plugin插件
- 编写getHttp-plugin插件实现代码
```java
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
    public static class GetHttpPluginImpl implements GetHttp {
        @Override
        public String get(GetHttpRequestDTO getHttpRequestDTO) {
            final String responseBody = HttpUtil.get(getHttpRequestDTO.getUrl());
            return responseBody;
        }
    }
}
```
- 在gradle.properties设置插件信息，非常重要！！！
```
## 插件版本号
version=1.0.0
## 插件ID，在调用插件的时候会使用
pluginId=gethttp-plugin
## 插件的执行类
pluginClass=io.github.yakami129.plugin.GetHttpPlugin
## 插件的作者相关信息
pluginProvider=yakami129
pluginDependencies=
```
- 设置disabled.txt和enabled.txt
  - disabled.txt：用于控制哪些插件默认是关闭的
  - enabled.txt：用于控制哪些插件默认是开启的
    
###（3）在项目根目录打包插件

```shell
gradle build -x test
```

###（4）测试getHttp-plugin插件

- 将打包好的插件copy到testing中
```shell
cp build/plugins/gethttp-plugin-1.0.0.zip testing/src/main/resources/plugins
```

- 执行getHttp-plugin插件
```java
@Slf4j
public class PluginMain {

    public static void main(String[] args) {

        // 初始化plugin的配置
        PluginProperties pluginProperties = new PluginProperties();
        final String pluginsUrl =  ClassLoader.getSystemResource("plugins").getPath();
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
```