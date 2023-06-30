/**
 * 构造下拉树数据结构
 * @param {*} dataList 
 * @param {*} valueKey 
 * @param {*} labelKey 
 * @param {*} childKey 
 * @returns 
 */
export function buildTreeSelectData(dataList, valueKey, labelKey, childKey = 'children') {
    if (dataList && dataList.length) {
        return dataList.map(data => {
            const treeData = {
                value : data[valueKey],
                title : data[labelKey]
            }
            const childList = data[childKey]
            if(childList && childList.length) {
                treeData.children = buildTreeSelectData(childList, valueKey, labelKey, childKey)
            }
            return treeData
        })
    }
    return [];
}