load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.5"
RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")
SPRING_BOOT_VERSION = "3.1.0"
SPRING_VERSION = "6.0.9"
maven_install(
    artifacts = [
        "junit:junit:4.12",
        "org.hamcrest:hamcrest-library:1.3",
        # 日志
        "org.slf4j:slf4j-api:2.0.7",
        "ch.qos.logback:logback-classic:1.4.6",
        # 前端
        "org.springframework.boot:spring-boot-starter-thymeleaf:%s" % SPRING_BOOT_VERSION,
        # spring
        "org.springframework.boot:spring-boot-autoconfigure:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-loader-tools:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-loader:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-configuration-processor:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-test-autoconfigure:%s" % SPRING_BOOT_VERSION,
#        "javax.servlet:javax.servlet-api:%s" % "3.1.0",
        "org.springframework.boot:spring-boot-starter-test:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-validation:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-test:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-web:%s" % SPRING_BOOT_VERSION,
        "org.springframework:spring-webmvc:%s" % SPRING_VERSION,
        "org.springframework:spring-beans:%s" % SPRING_VERSION,
        "org.springframework:spring-context:%s" % SPRING_VERSION ,
        "org.springframework:spring-test:%s" % SPRING_VERSION,
        "org.springframework:spring-web:%s" % SPRING_VERSION,
        "org.springframework:spring-core:%s" % SPRING_VERSION,
        "jakarta.servlet:jakarta.servlet-api:6.0.0",
        'javax.annotation:javax.annotation-api:1.3.2',
    ],
    repositories = [
        # Private repositories are supported through HTTP Basic auth
#        "http://username:password@localhost:8081/artifactory/my-repository",
#        "https://oss.sonatype.org/content/repositories/releases/",
        "https://maven.aliyun.com/repository/public",
#        "https://repo1.maven.org/maven2",
    ],
    maven_install_json = "@//:maven_install.json",
)

load("@maven//:defs.bzl", "pinned_maven_install")
pinned_maven_install()

http_archive(
    name = "rules_spring",
    sha256 = "fa067d7ed07437a3be3a211564f485648fc9f2ecc827a189d98b60dc5a078fa2",
    urls = [
        "https://github.com/salesforce/rules_spring/releases/download/2.3.0/rules-spring-2.3.0.zip",
    ],
)