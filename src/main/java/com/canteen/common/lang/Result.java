package com.canteen.common.lang;


import lombok.Data;

@Data
public class Result {
    private int code;//200正藏，非200表示异常
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result result=new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    public static Result fail(String msg){
        return fail(400,msg,null);
    }

    public static Result fail(String msg,Object data){
       return fail(400,msg,data);
    }
    public static Result fail(int code,String msg,Object data){
        Result result=new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
