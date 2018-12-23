package com.oj.judge.common;

/**
 * 返回给View的 Json Object
 * Created by Ming on 2018/3/16.
 */
public class ServerResult {

    private static final int SUCCESS_STATE = 200;

    private static final int FAIL_STATE = 500;

    /**
     * 状态码  200 成功 500失败 .....
     */
    private int state;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object Data;

    public ServerResult() {
    }

    public ServerResult(int state, String msg, Object data) {
        this.state = state;
        this.msg = msg;
        Data = data;
    }



    /**
     * 返回成功json
     *
     * @return
     */
    public static ServerResult success() {
        ServerResult jsonResultVO = new ServerResult();
        jsonResultVO.setState(SUCCESS_STATE);
        return jsonResultVO;
    }

    /**
     * 返回成功json
     *
     * @param msg
     * @return
     */
    public static ServerResult success(String msg) {
        ServerResult jsonResultVO = new ServerResult();
        jsonResultVO.setState(SUCCESS_STATE);
        jsonResultVO.setMsg(msg);
        return jsonResultVO;
    }

    /**
     * 返回成功json + message
     *
     * @param msg
     * @param data
     * @return
     */
    public static ServerResult success(String msg, Object data) {
        ServerResult jsonResultVO = new ServerResult();
        jsonResultVO.setState(SUCCESS_STATE);
        jsonResultVO.setData(data);
        jsonResultVO.setMsg(msg);
        return jsonResultVO;
    }

    /**
     * 返回失败Json
     *
     * @param msg
     * @return
     */
    public static ServerResult fail(String msg) {
        ServerResult jsonResultVO = new ServerResult();
        jsonResultVO.setState(FAIL_STATE);
        jsonResultVO.setMsg(msg);
        return jsonResultVO;
    }

    /**
     * 返回失败Json
     *
     * @return
     */
    public static ServerResult fail() {
        ServerResult jsonResultVO = new ServerResult();
        jsonResultVO.setState(FAIL_STATE);
        return jsonResultVO;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "ServerResult{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
