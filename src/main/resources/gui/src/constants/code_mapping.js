import {convertAsStringKey, convertFromMap} from "../utils/select_options_util";

export const RESOURCE_TYPES = {1: "功能", 2: "菜单", 3: "集合"}
export const RESOURCE_OPTIONS = convertFromMap(RESOURCE_TYPES)

export const USER_STATUS = {0: "禁用", 1: "启用"}
export const USER_STATUS_OPTIONS = convertFromMap(USER_STATUS)

export const SUCCESS_FLAG = {1: "成功", 0: "失败"}
export const SUCCESS_FLAG_OPTIONS = convertFromMap(SUCCESS_FLAG)

export const OPERATION_TYPES = {
    CREATE:'创建',
    UPDATE:'修改',
    DELETE:'删除',
    UPLOAD:'上传',
    QUERY:'查询',
    SAVE:'保存'
}

export const OPERATION_TYPES_OPTIONS = convertAsStringKey(OPERATION_TYPES)