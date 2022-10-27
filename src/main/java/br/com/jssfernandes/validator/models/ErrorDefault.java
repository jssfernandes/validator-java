package br.com.jssfernandes.validator.models;

import br.com.jssfernandes.validator.utils.JsonUtil;

import java.io.Serializable;

public class ErrorDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    private String category;
    private String message;
    private String informationCode;
    private String error;

    public ErrorDefault() {}

    public ErrorDefault(String category, String message) {
        this.message = message;
        this.category = category;
    }

    public ErrorDefault(String category, String message, String informationCode) {
        this.message = message;
        this.category = category;
        this.informationCode = informationCode;
    }

    public ErrorDefault(String category, String message, String informationCode, String error) {
        this.message = message;
        this.category = category;
        this.informationCode = informationCode;
        this.error = error;
    }

    public String getMessage() {
        if (message == null) {
            return error;
        }else {
            return message;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInformationCode() {
        return informationCode;
    }

    public void setInformationCode(String informationCode) {
        this.informationCode = informationCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        String me = JsonUtil.toJson(this);
        if(me!=null)
            return me;
        else
            return super.toString();
    }
}
