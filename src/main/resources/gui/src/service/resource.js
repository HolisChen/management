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

export async function getMenuList() {
    return request.get(`/api/resource/menu`, {
        headers : {
            "x-header-token":"e6dd29154f944bbea28857375b500399"
        }
    })
    .then(res => {
        return buildMenu(res)
    })

}