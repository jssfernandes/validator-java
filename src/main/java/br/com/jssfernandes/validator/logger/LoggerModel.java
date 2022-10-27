package br.com.jssfernandes.validator.logger;

import br.com.jssfernandes.validator.utils.JsonUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerModel implements Serializable {

    protected String message;
    protected String stackTrace;
    protected String appName;
    protected String appVersion;
    protected String component;
    protected LogType logType;
    protected LocalDateTime eventTime;
    protected String eventTimeString;
    protected String env;
    protected Level logLevel;
    protected Boolean soc = false;
    protected long threadId;
    protected String threadName;

//    @Autowired
//    private transient LogConfig logConfig;

    public LoggerModel() {}

    public LoggerModel(String component, LogType logType, String message, Level logLevel) {
//        if (logConfig != null) {
//            this.appName = logConfig.getAppName();
//            this.appVersion = logConfig.getAppVersion();
//            this.env = logConfig.getActiveProfile();
//        }
        this.component = component;
        this.logType = logType;
        this.message = message;
        this.eventTime = LocalDateTime.now();
        this.eventTimeString = eventTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        this.logLevel = logLevel;
        Thread ct = Thread.currentThread();
        this.threadId = ct.getId();
        this.threadName = ct.toString();//Name and group
    }

    public long getThreadId() {
        return threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public String getEventTimeString() {
        return eventTimeString;
    }

    public String getMessage() {
        return message;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getComponent() {
        return component;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getEnv() {
        return env;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public Boolean getSoc() {
        return soc;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void info(Logger logback) {
        String json = this.toJsonString();
        logback.info(json);
    }

    public void warn(Logger logback) {
        String json = this.toJsonString();
        logback.warn(json);
    }

    public void debug(Logger logback) {
        String json = this.toJsonString();
        logback.debug(json);
    }

    public void error(Logger logback) {
        String json = this.toJsonString();
        logback.error(json);
    }

    public String toJsonString() {
        String r = JsonUtil.toJson(this);
        if (r == null) {
            r = super.toString();
        }
        return r;
    }

}
