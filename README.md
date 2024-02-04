# English | [简体中文](./README_zhCN.md)

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

A extension plugin of nacos-datasource-plugin which support nacos-datasource-plugin to use postgresql database and DB2 database。

**Note:** Only the original plug-in functions have been enhanced without any changes.


## Features
- **No intrusion**: `nacos-datasource-plugin-extension` is extended on the basis of `nacos-datasource-plugin`. It only enhances but does not change. The introduction of `nacos-datasource-plugin-extension` will not have any impact on your existing `nacos` architecture. No impact, and no need to add any non-native `nacos` configuration
- **Less dependence**: Only relies on `nacos-datasource-plugin` and `naccos-common`, and does not introduce dependencies when referencing them


## Getting started

-   Add nacos-datasource-plugin-extension dependency
    - Latest Version: [![Maven Central](https://img.shields.io/maven-central/v/io.github.georgeanson/nacos-datasource-plugin-extension.svg)](https://search.maven.org/search?q=g:io.github.georgeanson%20a:nacos-datasource-plugin-extension)
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
      compile group: 'io.github.georgeanson', name: 'nacos-datasource-plugin-extension', version: 'Latest Version'
      ```
      
-    Use it. **Just use original configuration, eg: use `db2` as external storage**
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


## License

nacos-datasource-plugin-extension is released under Apache License 2.0. Please refer to [License](./LICENSE) for details.

## Links

- [Nacos](https://github.com/alibaba/nacos)


## Latest Version: [![Maven Central](https://img.shields.io/maven-central/v/io.github.georgeanson/nacos-datasource-plugin-extension.svg)](https://search.maven.org/search?q=g:io.github.georgeanson%20a:nacos-datasource-plugin-extension)


## Others

- Any developer interested in getting more involved in nacos-datasource-plugin-extension can make [contributions](https://github.com/GeorgeAnson/nacos-datasource-plugin-extension/pulls)!

- Reach out to me through email **georgeanson.gm@gmail.com**. Any issues or questions are welcomed on [Issues](https://github.com/GeorgeAnson/nacos-datasource-plugin-extension/issues).

- Look forward to your opinions. Response may be late but not denied.
