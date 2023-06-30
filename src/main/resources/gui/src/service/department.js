import request from '../request'
export async function getAllDepartmentList() {
    return request.get('/api/department/list')
}


export async function getDepartmentTree() {
    return request.get('/api/department/tree')
}