/**
 * @(#)QueryResult.java, 2014年5月13日. 
 * 
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.data;

/**
 * 任何api或者dao方法的执行结果数据结构。
 * <p>
 * 包含执行是否成功的信息、错误提示和一个特定类型的返回值。不需要返回值的请使用{@link ExecInfo}
 * 
 * @author WANG Minghao
 */
public class ExecResult<T> {

    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    private static final ExecResult SUCC_EMPTY = new ExecResult(true, "", null);

    private boolean succ;

    private String msg;

    private T value;

    @SuppressWarnings("unchecked")
    public static <T> ExecResult<T> succEmpty() {
        return SUCC_EMPTY;
    }

    public static <T> ExecResult<T> succ(T value) {
        return new ExecResult<T>(true, "", value);
    }

    public static <T> ExecResult<T> fail(String message) {
        return new ExecResult<T>(false, message, null);
    }

    private ExecResult(boolean succ, String msg, T value) {
        this.succ = succ;
        this.msg = msg;
        this.value = value;
    }

    public boolean isSucc() {
        return succ;
    }

    public boolean isEmpty() {
        return null == value;
    }

    public String getMsg() {
        return msg;
    }

    public T getValue() {
        return value;
    }

}
