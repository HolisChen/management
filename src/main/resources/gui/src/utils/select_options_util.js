/**
 * 把字典映射转换成标准select的options数据
 * @param mapping
 */
export function convertFromMap(mapping) {
    return Object.keys(mapping).map(key => ({label: mapping[key], value: Number(key)}))
}

export function convertAsStringKey(mapping) {
    return Object.keys(mapping).map(key => ({label: mapping[key], value: key}))
}

export function getSelectOptionData(list, valueKey, labelKey) {
    if (list && list.length) {
        return list.map(item => ({
            value: item[valueKey],
            label: item[labelKey]
        }))
    }
    return [];
}