import request from "../request"
export async function getAllRoles() {
    return request.get('/api/role')
}

export async function updateRole(payload) {
    return request.put('/api/role',payload)
}

export async function addRole(payload) {
    return request.post('/api/role',payload)
}

export async function deleteRole(roleId) {
    return request.delete(`/api/role/${roleId}`)
}

export async function getResourceByRole(roleId) {
    return request.get(`/api/role/${roleId}/resourceTree`)
}

export async function saveResourceByRole(roleId, payload) {
    return request.post(`/api/role/${roleId}/resource`, payload)
}