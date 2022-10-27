package br.com.jssfernandes.validator.logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class LogRequest extends LoggerModel{
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );
    private static final String HEADER_X_TRANSACTION_ID = "X-Transaction-Id";
    private static final String HEADER_X_FORWARD_FOR = "X-Forwarded-For";

    private String baseUri;
    private String resourceURI;
    private String resourceAction;
    private String body;
    private String entity;
    private String exception;
    private int statusHttpCode;
    private String statusCode;
    private int requestBytes;
    private int responseBytes;

    @JsonIgnore
    private long startTime;
    @JsonIgnore
    private long endTime;

    private long responseTime;
    private Map<String, String[]> params;
    private Map<String, String> header;
    private Map<String, String> pathParams;
    private String src;
    private String transactionId;
    private String response;

    /**
     *
     */
    public LogRequest() {
    }

    /**
     * LogRequest registra o conteudo do request e response no log, calculando o
     * tempo inicial e final
     *
     * @param requestContext Request
     * @param responseContext Response
     * @param startTime tempo inicial
     * @param endTime tempo final
     */
    public LogRequest(ContentCachingRequestWrapper requestContext, ContentCachingResponseWrapper responseContext, long startTime, long endTime) {

        if (requestContext != null) {
            this.transactionId = requestContext.getHeader(HEADER_X_TRANSACTION_ID);

            this.params = requestContext.getParameterMap();

            Map<String, String> headers = Collections.list(requestContext.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(h -> h, requestContext::getHeader));

            this.header = headers;
            this.requestBytes = requestContext.getContentLength();
            String contextPath = requestContext.getContextPath();
            this.resourceURI = requestContext.getRequestURI().substring(contextPath.length());
            this.baseUri = contextPath;
            this.resourceAction = requestContext.getMethod();

            Map<String, String> pathVariables = (Map<String, String>) requestContext
                    .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            this.pathParams = pathVariables;

            /**
             * ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(requestContext);
             */
            try {
                this.entity = requestContext.getContentType();
                this.body = new String(requestContext.getContentAsByteArray(), requestContext.getCharacterEncoding());
            } catch (UnsupportedEncodingException ex) {
                this.entity = "";
            }

            String ipHeader = requestContext.getHeader(HEADER_X_FORWARD_FOR);
            this.src = (ipHeader == null) ? requestContext.getRemoteAddr() : ipHeader;
        }

        extracted(responseContext);

        this.startTime = startTime;
        this.endTime = endTime;
        this.responseTime = TimeUnit.MILLISECONDS.convert(this.endTime - this.startTime, TimeUnit.MILLISECONDS);
    }

    private void extracted(ContentCachingResponseWrapper responseContext) {
        if (responseContext != null) {
            byte[] responseContent = responseContext.getContentAsByteArray();
            this.responseBytes = responseContent.length;
            if (this.responseBytes > 0) {
                MediaType mediaType = MediaType.valueOf(responseContext.getContentType());
                boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
                if (visible) {
                    try {
                        this.response = new String(responseContent, responseContext.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                        this.response = "";
                    }
                }
            }
            this.statusHttpCode = responseContext.getStatus();

            if(this.statusHttpCode == 0){
                this.statusCode = "none";
            } else {
                this.statusCode = HttpStatus.valueOf(this.statusHttpCode).name();
            }
        }
    }

    /**
     * *
     * LogRequest encapsulando {@code HttpServletRequest} para
     * {@code ContentCachingRequestWrapper}
     *
     * @param requestContext
     * @param responseContext
     * @param startTime
     * @param endTime
     */
    public LogRequest(HttpServletRequest requestContext, HttpServletResponse responseContext, long startTime, long endTime) {
        this(wrapRequest(requestContext), wrapResponse(responseContext), startTime, endTime);
    }

    /**
     * LogRequest para filtro de Request
     *
     * @param requestContext deve ser um objeto {@code ServletWebRequest}
     * @param status {@code HttpStatus}
     * @throws IOException
     */
    public LogRequest(WebRequest requestContext, HttpStatus status) {
        this(wrapRequest(((ServletWebRequest) requestContext).getRequest()),
                wrapResponse(((ServletWebRequest) requestContext).getResponse()), 0, 0);
        if (status != null) {
            this.statusCode = status.name();
            this.statusHttpCode = status.value();
        }
    }

//    /**
//     * *
//     * LogRequest para Feign request
//     *
//     * @param request
//     */
//    public LogRequest(RequestTemplate request) {
//        this.message = "FEIGN";
//        if (request != null) {
//            if (request.headers().get(HEADER_X_TRANSACTION_ID) != null) {
//                this.transactionId = request.headers().get(HEADER_X_TRANSACTION_ID).stream().findFirst().orElse("");
//            }
//            this.header = new HashMap<>();
//            request.headers().entrySet().forEach(a -> this.header.put(a.getKey(), a.getValue().stream().collect(Collectors.joining("; "))));
//            this.requestBytes = request.requestBody().length();
//
//            this.body = (request.requestBody() != null) ? request.requestBody().asString() : "";
//
//            this.baseUri = request.url();
//            this.resourceURI = request.path();
//            this.resourceAction = request.method();
//
//            this.entity = request.bodyTemplate();
//
//            if (request.headers().containsKey(HEADER_X_FORWARD_FOR)) {
//                this.src = request.headers().get(HEADER_X_FORWARD_FOR).stream().collect(Collectors.joining("; "));
//            }
//        }
//    }


//    /**
//     * LogRequest para resposta de erro de requisição FEIGN
//     *
//     * @param methodKey requested Verb
//     * @param response  response from requested
//     */
//    public LogRequest(String methodKey, Response response) {
//        this.statusHttpCode = response.status();
//        HttpStatus httpStatus = HttpStatus.resolve(response.status());
//        if (httpStatus != null) {
//            this.statusCode = httpStatus.name();
//        }
//
//        this.header = new HashMap<>();
//        response.request().headers().entrySet().forEach(a -> this.header.put(a.getKey(), a.getValue().stream().collect(Collectors.joining("; "))));
//        this.resourceURI = response.request().url();
//        this.resourceAction = methodKey;
//        String stringResourceURI = this.resourceURI.substring(7);
//        if (stringResourceURI.indexOf('/') >= 0) {
//            stringResourceURI = this.resourceURI.substring(8);
//            this.baseUri = this.resourceURI.substring(0, stringResourceURI.indexOf('/'));
//        } else {
//            this.baseUri = this.resourceURI;
//        }
//        this.response = JsonUtil.toJson(response.request().requestBody());
//
//        try {
//            this.body = CharStreams.toString(response.body().asReader());
//        } catch (IOException|NullPointerException ex) {
//            this.body = "body:null";
//        }
//
//    }

    void fromLoggerModel(LoggerModel loggerModel) {
        if (loggerModel != null) {
            this.appName = loggerModel.getAppName();
            this.component = loggerModel.getComponent();
            this.logType = loggerModel.getLogType();
            this.env = loggerModel.getEnv();
            this.eventTime = loggerModel.getEventTime();
            this.logLevel = loggerModel.getLogLevel();
            if (StringUtils.isEmpty(loggerModel.getMessage())) {
                this.message = loggerModel.getMessage();
            }
            this.stackTrace = loggerModel.getStackTrace();
        }
    }



    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public String getResourceAction() {
        return resourceAction;
    }

    public String getEntity() {
        return entity;
    }

    public String getException() {
        return exception;
    }

    public int getStatusHttpCode() {
        return statusHttpCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public int getRequestBytes() {
        return requestBytes;
    }

    public int getResponseBytes() {
        return responseBytes;
    }

    public String getResponse() {
        return response;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public String getHeaderXForwardedFor() {
        return this.src;
    }

    public String gettransactionId() {
        return transactionId;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public String getSrc() {
        return src;
    }



    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response == null) {
            return null;
        }
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}
