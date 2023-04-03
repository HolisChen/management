import request from '../request'
import { current_user_url } from '../constants/ApiURL'
export async function getCurrentUser() {
    return request.get(current_user_url)
        .then(res => {
            return res;
        }, err => Promise.reject(err))
}