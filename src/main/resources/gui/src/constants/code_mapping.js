import {convertFromMap} from "../utils/select_options_util";

export const RESOURCE_TYPES = {1: "功能", 2: "菜单", 3: "集合"}
export const RESOURCE_OPTIONS = convertFromMap(RESOURCE_TYPES)

export const USER_STATUS = {0: "禁用", 1: "启用"}
export const USER_STATUS_OPTIONS = convertFromMap(USER_STATUS)