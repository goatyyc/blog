package com.learn.site.exception;

import com.learn.site.utils.APIResponse;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException{

    protected String errorCode;
    protected String[] errorMessageArguments;
    protected APIResponse apiResponse;

    protected BusinessException() { this(""); }

    public BusinessException(String message){
        super(message);
        this.errorCode="fail";
        this.errorMessageArguments=new String[0];
    }

    public BusinessException(String message, Throwable cause){
        super(message,cause);
        this.errorCode="fail";
        this.errorMessageArguments=new String[0];
    }

    public String getErrorCode(){ return this.errorCode; }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getErrorMessageArguments() {
        return errorMessageArguments;
    }

    public void setErrorMessageArguments(String[] errorMessageArguments) {
        this.errorMessageArguments = errorMessageArguments;
    }


    // 静态方法
    public static BusinessException withErrorCode(String errorCode){
        BusinessException businessException = new BusinessException();
        businessException.errorCode = errorCode;
        return businessException;
    }

    public static BusinessException fromAPIResponse(APIResponse apiResponse){
        BusinessException businessException = new BusinessException();
        if(null == apiResponse){
            apiResponse = APIResponse.fail("NULL");
        }
        businessException.apiResponse = apiResponse;
        return businessException;
    }

    public BusinessException withErrorMessageArguments(String... errorMessageArguments){
        if(errorMessageArguments != null){
            this.errorMessageArguments = errorMessageArguments;
        }
        return this;
    }

    public APIResponse response(){
        if(this.apiResponse != null){
            return this.apiResponse;
        }
        this.apiResponse = APIResponse.withCode(this.getErrorCode());
        if(this.getErrorMessageArguments() != null && this.getErrorMessageArguments().length>0){
            try {
                this.apiResponse.setMsg(MessageFormat.format(this.apiResponse.getMsg(), this.getErrorMessageArguments()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.apiResponse;
    }
}
