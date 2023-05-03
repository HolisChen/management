import Dashboad from '../pages/Dashboard'
import User from '../pages/User'
import Menu from '../pages/Menu'
import Role from '../pages/Role'
import LogQuery from '../pages/LogQuery'
export default {
    '/dashboard': <Dashboad />,
    '/systemSetting/user': <User />,
    '/systemSetting/role': <Role />,
    '/systemSetting/menu': <Menu />,
    '/logQuery': <LogQuery />,
}