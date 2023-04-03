export const RESOURCE_TYPES = { 1: "功能", 2: "菜单", 3: "集合" }
export const RESOURCE_OPTIONS = Object.keys(RESOURCE_TYPES).map(k => ({ label: RESOURCE_TYPES[k], value: k }))