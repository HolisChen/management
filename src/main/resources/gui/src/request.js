import axios from "axios";
import { message } from "antd";

let instance = axios.create({
    timeout: 10000
})

instance.interceptors.response.use(res => {
    const { data } = res;
    const code = data.code;
    if (code === 200) {
        return Promise.resolve(data.data)
    } else {
        message.error(data.msg || '未知错误');
        return Promise.reject(data)
    }
}, err => {
    if (err.response.status === 401) {
        localStorage.removeItem('login_token')
        message.info('请先登录')
        //跳转到登录页
        const e = new CustomEvent('need_login', { bubbles: true, detail: {} })
        window.dispatchEvent(e)
    } else {
        message.error(err.message || err)
    }
    return Promise.reject(err);
})

instance.interceptors.request.use(config => {
    let token = localStorage.getItem("login_token")
    if (token) {
        config.headers["x-header-token"] = token;
    }
    return config
})

export default instance