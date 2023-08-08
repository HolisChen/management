import Dashboad from '../pages/Dashboard'
import User from '../pages/User'
import Menu from '../pages/Menu'
import Role from '../pages/Role'
import LogQuery from '../pages/LogQuery'
import Department from '../pages/Department'
import AppConfig from '../pages/AppConfig'
export default {
    '/dashboard': <Dashboad />,
    '/systemSetting/user': <User />,
    '/systemSetting/role': <Role />,
    '/systemSetting/menu': <Menu />,
    '/systemSetting/dept': <Department />,
    '/systemSetting/config': <AppConfig />,
    '/logQuery': <LogQuery />
}