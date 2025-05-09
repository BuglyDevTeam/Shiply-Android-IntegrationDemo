# Shiply-Android-IntegrationDemo
ShiplyProAndroid使用Demo，Shiply访问地址：https://shiply.tds.qq.com/

[Shiply](https://shiply.tds.qq.com/)是TDS腾讯端服务（Tencent Device-oriented Service）旗下的一站式客户端发布平台，提供了一套规则灵活、发布安全、高效分发的终端基础通用发布系统，功能包括：Android灰度发布，Android热修复、配置与开关发布、资源发布，帮助产品和技术团队提高研效能力和决策力。


## ShiplyProAndroidDemo使用说明
本Demo主要演示如何快速接入ShiplySDK，更多SDK接入细节可以参考[Shiply配置开关SDK接入文档](https://shiply.tds.qq.com/document/remote-config/sdk-integration/android-sdk-integration/)。
SDK初始化代码位于` shiplyIntegrationDemo/src/main/java/com/example/shiplyIntegrationDemo/InitUtil.java `文件的` initShiplySDK `方法中，Shiply用户可以将对应的appId和appKey替换为自己的值，然后进行测试验证。


### 配置开关功能说明
主要包括远程配置拉取与本地配置查询两大块：

#### 远程配置拉取
- 点击「REQ_FULL」按钮，可以触发全量配置拉取请求；
- 输入单个配置KEY后，点击「REQ_SINGLE」按钮，可以触发单个配置拉取请求；
- 输入多个配置KEY后，点击「REQ_MULTI」按钮，可以触发批量配置拉取请求；

#### 本地配置查询
- 输入配置KEY后，点击「GET_SWITCH」按钮，可以查询对应配置KEY的开关值；
- 输入配置KEY后，点击「GET_CONFIG」按钮，可以查询对应配置KEY的配置值；
- 输入配置KEY后，点击「GET_DATA」按钮，可以查询对应配置KEY的开关值和配置值；


注意：
需要确保在Shiply前端已经创建了对应的升级任务，具体操作可以参考[如何使用 Shiply 远程配置开关](https://shiply.tds.qq.com/document/remote-config/quick-start/)。


### 资源制品功能说明
主要包括远端资源拉取和本地配置查询两块：

#### 异步获取资源
- 在文本框输入想要拉取的资源名称，点击「load」按钮，开始拉取资源，结果在下方文本框中显示。
- 在文本框输入想要拉取的资源名称，点击「loadLatest」按钮，开始拉取最新资源，结果在下方文本框中显示。

#### 同步获取资源
- 在文本框输入想要获取的资源名称，点击「get」即可获取资源相关信息，相关信息会显示在下面的文本框中。
- 在文本框输入想要获取的资源名称，点击「getLatest」即可获取最新资源相关信息，相关信息会显示在下面的文本框中。


注意：
需要确保在Shiply前端已经发布了对应的资源，具体操作可以参考[如何使用 Shiply 远程资源](https://shiply.tds.qq.com/document/remote-resource/quick-start/)；

### 应用内升级功能说明
后点击「手动检查更新」按钮，会弹出升级弹框：
点击弹框中「立即更新」按钮，会触发下载和安装。


注意：
需要确保在Shiply前端已经创建了对应的升级任务，具体操作可以参考[如何使用 Shiply Android 灰度发布](https://shiply.tds.qq.com/document/in-app-upgrade/quick-start/)；
需要确保远端升级任务中APK文件的versionCode大于本地测试APK文件的versionCode;


### 热修功能说明
在项目根目录下执行`./script/build_rfix_patch_auto.sh `即可触发应用和补丁的自动构建，构建成功后你可以在`app/RFix`下找到所有构建产物。
开发者可以将`old.apk`安装到手机，将`patch_tinker.apk`配置到Shiply的发布平台进行体验，具体操作可以参考[Shiply 热修复发布平台使用指引](https://shiply.tds.qq.com/document/hotfix/quick-start/)。

本demo提供了两种热修接入方式：SampleLikeApplication/SampleProxyApplication,采用哪种方式接入取决于AndroidManifest.xml中设置的application名字，
android:name=".app.SampleProxyApplication"  or  android:name=".app.SampleLikeApplication"



