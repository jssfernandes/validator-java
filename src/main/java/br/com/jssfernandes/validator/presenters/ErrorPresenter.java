package br.com.jssfernandes.validator.presenters;

import br.com.jssfernandes.validator.models.ErrorDefault;
import br.com.jssfernandes.validator.utils.JsonUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ErrorPresenter implements Serializable {
    private List<ErrorDefault> errors = new ArrayList<>();
    private String status;
    private String timestamp;
    private String path;
    private String error;

    public ErrorPresenter() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public ErrorPresenter(ErrorDefault error) {
        this.errors.add(error);
    }

    public ErrorPresenter(List<ErrorDefault> errors) {
        this.errors = errors;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ErrorDefault> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDefault> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        String me = JsonUtil.toJson(this);
        if (me != null) {
            return me;
        } else {
            return super.toString();
        }
    }
}
