import request from '../request'
import {login_url} from '../constants/ApiURL'


export async function doLogin(param) {
    return request.post(login_url, param)
    .then(res => {
        localStorage.setItem("login_token",res)
        return true;
    }, err => {
        return false;
    })

}