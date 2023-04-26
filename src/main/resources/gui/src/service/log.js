import request from '../request'
export async function getLogPage(payload) {
    return request.post("/api/log/page", payload)
}
