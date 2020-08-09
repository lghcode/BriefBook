package com.lghcode.briefbook.util;

/**
 * @author lghcode@qq.com
 * @date 2019/9/21 14:41
 */
public class ResultJson {

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static ResultJson success(String msg){
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(200);
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static ResultJson error(String msg){
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(500);
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static ResultJson success(String msg,Object data){
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(200);
        resultJson.setMsg(msg);
        resultJson.setData(data);
        return resultJson;
    }
    public static ResultJson errorTokenMsg(String msg) {
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(502);
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static ResultJson error(String msg,Object data){
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(500);
        resultJson.setMsg(msg);
        resultJson.setData(data);
        return resultJson;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
