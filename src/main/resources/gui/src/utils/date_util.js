export function formatDate(dateStr) {
    try{
        return new Date(dateStr).toLocaleString()
    }catch(err) {
        console.error(err)
    }
    return dateStr
}