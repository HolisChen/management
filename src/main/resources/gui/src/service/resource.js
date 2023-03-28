import request from "../request"

function buildMenu(res) {
    let result = []
    if (res.length ) {
        res.forEach( (item) => {
            const menu = {
                key : item.resourceUrl,
                label : item.resourceName,
                icon: item.icon,
                crumblabel: item.resourceName
            }
            if (item.children && item.children.length) {
                menu.children = buildMenu(item.children)
            }
            result.push( menu )
        })
    }
    return result;
}

export function getMenuList(setMenu) {
    request.get(`/api/resource/resourceTree?resourceTypes=2,3`)
    .then(res => {
        setMenu(buildMenu(res))
    })

}