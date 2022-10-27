package br.com.jssfernandes.validator.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import java.io.Serializable;

/**
 * Anotar com {@link Log} ou {@link Autowired}
 * <p/>
 * {@code Log} is a bean produced by {@link Log}
 * <p>
 * <
 * pre> @Log private Log logger;}
 </pre>
 *
 * @see Log
 */
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Logger logback;
    private LogType type;
    private String clazzName;

    private Log() {
    }

    public Log(Class<?> clazz, LogType logType) {
        this.clazzName = clazz.getName();
        this.type = logType;
        this.logback = (Logger) LoggerFactory.getLogger(clazz);
    }

    public Log(String className, LogType logType) {
        this.clazzName = className;
        this.type = logType;
        this.logback = (Logger) LoggerFactory.getLogger(clazzName);
    }

    public Log(org.slf4j.Logger logger, LogType logType) {
        this.logback = (Logger) logger;
        this.type = logType;
        this.clazzName = logger.getName();
    }

    private LoggerModel getLoggerModel(String message, Level level) {
        return new LoggerModel(this.clazzName, this.type, message, level);
    }

    public void info(String message) {
        LoggerModel log = getLoggerModel(message, Level.INFO);
        log.info(this.logback);
    }

    public void info(LogRequest logRequest) {
        LoggerModel log = getLoggerModel(logRequest.getMessage(), Level.INFO);
        log.info(this.logback);
    }

    public void info(String message, Throwable ex) {
        LoggerModel log = getLoggerModel(message + " - " + ex.getMessage(), Level.INFO);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.info(this.logback);
    }

    public void warn(String message) {
        LoggerModel log = getLoggerModel(message, Level.WARN);
        log.warn(this.logback);
    }

    public void warn(LogRequest logRequest) {
        LoggerModel log = getLoggerModel(logRequest.getMessage(), Level.WARN);
        log.warn(this.logback);
    }

    public void warn(String message, Throwable ex) {
        LoggerModel log = getLoggerModel(message + " - " + ex.getMessage(), Level.WARN);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.warn(this.logback);
    }

    public void warn(Throwable ex) {
        LoggerModel log = getLoggerModel(ex.getMessage(), Level.WARN);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.warn(this.logback);
    }

    public void error(String message) {
        LoggerModel log = getLoggerModel(message, Level.ERROR);
        log.error(this.logback);
    }

    public void error(LogRequest logRequest) {
        LoggerModel log = getLoggerModel(logRequest.getMessage(), Level.ERROR);
        log.error(this.logback);
    }

    public void error(String message, Throwable ex) {
        LoggerModel log = getLoggerModel(message + " - " + ex.getMessage(), Level.ERROR);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.error(this.logback);
    }

    public void error(Throwable ex) {
        LoggerModel log = getLoggerModel(ex.getMessage(), Level.ERROR);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.error(this.logback);
    }

    public void error(LogRequest logRequest, Throwable ex) {
        LoggerModel log = getLoggerModel(logRequest.getMessage() + " - " + ex.getMessage(), Level.ERROR);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.error(this.logback);
    }

    public void debug(String message) {
        LoggerModel log = getLoggerModel(message, Level.DEBUG);
        log.debug(this.logback);
    }

    public void debug(String message, Throwable ex) {
        LoggerModel log = getLoggerModel(message, Level.DEBUG);
        log.setStackTrace(ExceptionUtils.getStackTrace(ex));
        log.debug(this.logback);
    }

    public void printContextStatus() {
        StatusPrinter.print(this.logback.getLoggerContext());
    }

    public boolean isErrorEnabled() {
        return logback.isErrorEnabled();
    }

    public boolean isWarnEnabled() {
        return logback.isWarnEnabled();
    }

    public boolean isInfoEnabled() {
        return logback.isInfoEnabled();
    }

    public boolean isDebugEnabled() {
        return logback.isDebugEnabled();
    }

    public boolean isTraceEnabled() {
        return logback.isTraceEnabled();
    }
}
