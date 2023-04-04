/**
 * 把字典映射转换成标准select的options数据
 * @param mapping
 */
export function convertFromMap(mapping) {
    return Object.keys(mapping).map(key => ({label: mapping[key], value: key}))
}