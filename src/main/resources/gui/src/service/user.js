import request from '../request'
export async function getCurrentUser() {
    return request.get('/api/user/currentUserInfo')
        .then(res => {
            return res;
        }, err => Promise.reject(err))
}

export async function getUserList() {
    return request.get('/api/user/list')
}

export async function getUserPage(payload) {
    return request.post('/api/user/page', payload)
}

export async function updateUser(payload) {
    return request.put("/api/user", payload)
} 


export async function disableUser(userId) {
    return request.put(`/api/user/${userId}/disable`)
}

export async function enableUser(userId) {
    return request.put(`/api/user/${userId}/enable`)
}