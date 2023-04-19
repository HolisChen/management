import request from "../request"
export async function getAllRoles() {
    return request.get('/api/role')
}

export async function updateRole(payload) {
    return request.put('/api/role',payload)
}