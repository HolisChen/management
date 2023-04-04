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