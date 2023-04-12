import request from "../request"
export async function getAllRoles() {
    return request.get('/api/role')
}