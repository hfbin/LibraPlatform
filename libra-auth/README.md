# 多种登录方式接入（策列模式）
为了有更好的扩展多种登录方式，此模块使用策列模式的设计方式。可参考PwdCaptchaStrategy类写法，继承AbstractTokenGranter类重写grant方法逻辑。

AbstractTokenGranter类默认提三个方法：

1、公共校验publicCheck（参数校验、客户端校验、租户校验）

2、校验账号checkAccount

3、查询身份信息

4、创建token

