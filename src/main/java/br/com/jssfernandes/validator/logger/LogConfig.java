//package br.com.validator.logger;
//
//import ch.qos.logback.classic.Logger;
//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.Appender;
//import ch.qos.logback.core.rolling.RollingFileAppender;
//import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
//import ch.qos.logback.core.util.FileSize;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
///**
// * Configuração do logger
// *
// */
//@Configuration
//public class LogConfig {
//    static final String APPENDER_NAME = "Rolling";
//
//    private final LoggerContext context;
//
//    @Value("${spring.profiles.active:no-profile}")
//    private String activeProfile;
//
//    @Value("${spring.application.name:no-name}")
//    private String appName;
//
//    @Value("${spring.application.version:0}")
//    private String appVersion;
//
//    public LogConfig(Environment env) {
//        this.context = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//        Logger logback = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
//
//        if (logback.getAppender(LogConfig.APPENDER_NAME) == null) {
//            logback.addAppender(getRollingAppender(env));
//        }
//    }
//
//    private Appender<ILoggingEvent> getRollingAppender(Environment env) {
//
//        // get properties configuration
//        String path = "/opt/" + appName + "/logs/" + appName;
//
//        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<ILoggingEvent>();
//        appender.setAppend(true);
//        appender.setContext(context);
//        appender.setName(APPENDER_NAME);
//
//
//        SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new SizeAndTimeBasedRollingPolicy<>();
//        rollingPolicy.setContext(context);
//        rollingPolicy.setParent(appender);
//        rollingPolicy.setFileNamePattern(path + "-%d{yyyy-MM-dd}-%i.log");
//        String maxHistory = env.getProperty("log.max-history", "5");
//        rollingPolicy.setMaxHistory(Integer.valueOf(maxHistory));
//        String totalSizeCap = env.getProperty("log.total-size-cap", "500MB");
//        rollingPolicy.setTotalSizeCap(FileSize.valueOf(totalSizeCap));
//        String maxFileSize = env.getProperty("log.max-file-size", "5MB");
//        rollingPolicy.setMaxFileSize(FileSize.valueOf(maxFileSize));
//        rollingPolicy.start();
//
//        appender.setRollingPolicy(rollingPolicy);
//
//        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//        encoder.setPattern("%date %level %logger [%t] %message %n");
//        encoder.setContext(context);
//        encoder.start();
//
//        appender.setEncoder(encoder);
//
//        appender.start();
//
//        return appender;
//    }
//
//    public String getActiveProfile() {
//        return activeProfile;
//    }
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public String getAppVersion() {
//        return appVersion;
//    }
//}
