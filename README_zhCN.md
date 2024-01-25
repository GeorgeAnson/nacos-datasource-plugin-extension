# [English](./README.md) | 简体中文

<p align="center">
<img src="https://raw.githubusercontent.com/GeorgeAnson/nacos-datasource-plugin-extension/2.2.0/others/images/logo.jpg" alt="nacos-datasource-plugin-extension" title="nacos-datasource-plugin-extension" width="557"/>
</p>


<p align="center">
  <a href="https://search.maven.org/search?q=g:io.github.georgeanson%20a:nacos-datasource-plugin-extension">
    <img alt="maven" src="https://img.shields.io/maven-central/v/io.github.georgeanson/nacos-datasource-plugin-extension.svg?style=flat-square">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

nacos-datasource-plugin的扩展插件，支持nacos-datasource-plugin-extension使用postgresql数据库和DB2数据库。

**注：** 仅对原有插件功能进行了增强，没有做任何改动。


## 优点

- **无侵入**：`nacos-datasource-plugin-extension`在`nacos-datasource-plugin`的基础上进行扩展，只增强不改变，引入`nacos-datasource-plugin-extension`不会对您现有的`nacos`构架产生任何影响，而且无需新增任何非原生`nacos`配置
- **依赖少**：仅仅依赖`nacos-datasource-plugin`以及`naccos-common`，且在引用时不会引入依赖


## 使用

-   添加 `nacos-datasource-plugin-extension` 依赖
    - 最新版本: [![Maven Central](https://img.shields.io/maven-central/v/io.github.georgeanson/nacos-datasource-plugin-extension.svg)](https://search.maven.org/search?q=g:io.github.georgeanson%20a:nacos-datasource-plugin-extension)
    - Maven:
      ```xml
      <dependency>
          <groupId>io.github.georgeanson</groupId>
          <artifactId>nacos-datasource-plugin-extension</artifactId>
          <version>Latest Version</version>
      </dependency>
      ```

-    Gradle
      ```groovy
      compile group: 'io.github.georgeansonu', name: 'nacos-datasource-plugin-extension', version: 'Latest Version'
      ```

-    Yaml配置。 **以下是一个示例配配置，使用 `db2` 数据库作为外部存储（本项目不新增任何其他非`nacos`原生配置）**
     ```yaml
     spring:
        sql:
          init:
          platform: db2 #Choose one: db2,mysql,postgresql
     db:
       num: 1
       url.0: jdbc:db2://${DB2_HOST}:${DB2_PORT}/${DB2_INSTANCE}
       user: ${DB2_USER}
       password: ${DB2_PWD}
       pool:
         config:
         driver-class-name: com.ibm.db2.jcc.DB2Driver
         connection-test-query: select 1 FROM SYSIBM.SYSDUMMY1
         schema: ${DB2_SCHEMA}
     ```


## 相关链接

- [Nacos](https://github.com/alibaba/nacos)


## 最新版本: [![Maven Central](https://img.shields.io/maven-central/v/io.github.georgeanson/nacos-datasource-plugin-extension.svg)](https://search.maven.org/search?q=g:io.github.georgeanson%20a:nacos-datasource-plugin-extension)


## 其他
* 开源许可证：Apache License, Version 2.0
* 任何有兴趣更多地参与 nacos-datasource-plugin-extension 的开发人员都可以做出[贡献](https://github.com/GeorgeAnson/nacos-datasource-plugin-extension/pulls)！
* 通过电子邮件 `georgeanson.gm@gmail.com` 与我联系。如有任何问题或疑问，欢迎在[问题](https://github.com/GeorgeAnson/nacos-datasource-plugin-extension/issues)上提出。
* 期待您的意见。回复可能会迟到，但不会被拒绝。