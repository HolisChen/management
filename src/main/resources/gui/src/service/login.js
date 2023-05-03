import request from '../request'


export async function doLogin(param) {
    return request.post('/api/login', param)
        .then(res => {
            localStorage.setItem("login_token", res)
            return true;
        }, err => {
            return false;
        })

}

export async function getCapture() {
    return request.get('/api/capture')
}