package com.kv.wc.common.enums;

import com.kv.wc.common.exception.IErrorCode;

import lombok.Getter;
import lombok.ToString;

/**
 * 返回枚举类
 *
 */
@ToString
@Getter
public enum ResultStatusEnum implements IErrorCode {

    /**
     *
     */
    SUCCESS(200, "成功"), OPERATE_SUCCESS(200, "操作成功"), CERTIFICATION_SUCCESS(200, "认证成功"), FAIL(-1, "失败"),
    UNKNOWN(100, "系统异常，请稍后再试。"), SCREEN_PARAM_NULL(400, "参数缺失"), DEFAULT_ERROR(400, "操作失败"),
    ACCESS_TOKEN_EXPIRE(401, "身份信息token已过期或不存在，请重新登录！"), ACCESS_TOKEN_NULL(401, "缺少身份认证信息，请重新登录！"),
    LOCAL_USER_NOT_EXIST(401, "您不是数仓平台应用的有效用户,请联系数仓平台应用管理员添加"), INVALIDATE_CODE(401, "无效CODE！"),
    AUTH_SERVER_FAIL(401, "获取认证服务失败！"), AUTHENTICATE_CHECK_ERROR(401, "token校验不通过"),
    AUTHENTICATE_EXPIRED(401, "授权码已过期"), AUTH_BLANK_TOKEN(401, "获取到无效的token！"), PARAM_ERROR(402, "参数错误"),
    ILLEGAL_ACTION(403, "非法的操作"), ACCESS_FORBIDDEN(403, "资源没有权限访问，请联系管理员！"), NOT_ALLOW(403, "不允许的操作"),
    PARAMETER_FORMAT_EXCEPTION(405, "参数格式异常"), METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    HTTP_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"), FILE_SIZE_MORE_THAN(415, "上传文件大小超过限制"),
    EXPORT_DATA_IS_EMPTY(455, "导出数据为空"), TO_MARCH_REQUEST_ERROR(500, "当前接口访问过大，请稍后再试！"),
    SYSTEM_ERROR(500, "系统异常，请稍后再试。"), DB_ERROR(501, "db error!"), BAD_GATEWAY(502, "gateway error!"),
    AUTHORITY_LACK(503, "权限不足!"), ILLEGAL_TENANT(402, "权限错误,租户信息错误,请核实"), TENANT_NOT_EXIST(402, "不存在或无权限的租户,请核实"),
    USER_NOT_EXIST(402, "用户不存在,请核实"), PROJECT_NOT_EXIST(402, "项目不存在,请核实"), JOB_NOT_EXIST(402, "任务不存在,请核实"),
    DATASOURCE_NOT_EXIST(402, "数据源不存在,请核实"),;

    private final Integer code;
    private final String message;

    ResultStatusEnum(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.message;
    }
}
