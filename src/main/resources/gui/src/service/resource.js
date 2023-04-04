import request from "../request"
import { message } from "antd";

function buildMenu(res) {
    let result = []
    if (res && res.length) {
        res.forEach((item) => {
            const menu = {
                key: item.resourceUrl,
                label: item.resourceName,
                icon: item.icon,
                crumblabel: item.resourceName
            }
            if (item.children && item.children.length) {
                menu.children = buildMenu(item.children)
            }
            result.push(menu)
        })
    }
    return result;
}

export async function getMenuList() {
    return request.get(`/api/resource/menu`)
        .then(res => {
            return buildMenu(res)
        }, err => [])

}

export async function getResourceTree() {
    return request.get('/api/resource/resourceTree')
        .then(res => {
            const removeEmptyChild = (tree) => {
                if(tree && tree.children && tree.children.length) {
                    tree.children.forEach(child => removeEmptyChild(child))
                } else {
                    delete tree.children
                }
            }
            if(res && res.length) {
                res.forEach(tree => removeEmptyChild(tree))
            }
            return res;
        }, err => [])
}

export async function updateResource(payload) {
    return request.put("/api/resource",payload)
        .then(res => {
            message.success('保存成功')
        })
}

export async function addResource(payload) { 
    return request.post("/api/resource",payload)
        .then(res => {
            message.success('新建成功')
        })
}

export async function removeResource(payload) {
    return request.delete("/api/resource", {data :payload} )
        .then(res => {
            message.success('删除成功')
        })
}