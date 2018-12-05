package com.lu.library.http;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.http
 * @description: ${TODO}{ 类注释}
 * @date: 2018/8/7 0007
 */
public class HttpException extends Exception {
    public int errorCode;
    public HttpException(){

    }
    public HttpException(String message) {
        super(message);
    }
    public HttpException(Throwable e){
        super(e);
    }
}
