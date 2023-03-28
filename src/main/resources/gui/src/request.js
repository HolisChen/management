import axios from "axios";

let instance = axios.create({
    timeout: 10000
})
instance.interceptors.response.use(res => {
    if (res.status === 200) {
        const { data } = res;
        const code = data.code;
        if (code === 200) { 
            return Promise.resolve(data.data)
        } else {
            console.log('业务异常', data.msg)
        }

    } else {
        return Promise.reject(res)
    }
}, err => {
    return Promise.reject(err);
})

export default instance