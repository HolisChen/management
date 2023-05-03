import { useEffect, useState } from 'react'
import { useNavigate, Outlet, useLocation } from 'react-router-dom'
import { Layout, Menu, Breadcrumb, Tabs } from 'antd'
import { getMenuList } from '../service/resource'
import { getCurrentUser } from '../service/user'
import LoginUser from '../components/LoginUser'
import '../css/home.css'
import tabsRoutes from '../routes/tabs_routes'

import NotFound from '../components/404'

/**
 * 初始化面包屑Map
 * @param {} menus 
 * @returns 
 */
function initCrumbMap(menus) {
  const result = {}
  if (menus.length) {
    menus.forEach(item => {
      result[item.key] = item.crumblabel
      if (item.children && item.children.length) {
        const children = initCrumbMap(item.children)
        Object.keys(children).forEach(k => {
          result[k] = children[k]
        })
      }
    })
  }
  return result
}

/**
 * 创建面包屑
 */
function buildBreadcrumbItems(menus, location) {
  const crumbMap = initCrumbMap(menus)
  const pathSnippets = location.pathname.split('/').filter((i) => i);
  const extraBreadcrumbItems = pathSnippets.map((_, index) => {
    const url = `/${pathSnippets.slice(0, index + 1).join('/')}`
    return {
      key: url,
      title: <span className='crumbItem' to={url}>{crumbMap[url]}</span>,
    };
  });
  return [
    {
      title: <span className='crumbItem' to="/">首页</span>,
      key: 'home',
    },
  ].concat(extraBreadcrumbItems);
}

const { Header, Content, Sider } = Layout;

export default function Home() {
  const [menus, setMenus] = useState([])
  const [currentUser, setCurrentUser] = useState({})
  const [collapsed, setCollapsed] = useState(false)

  const [activeKey, setActiveKey] = useState('')
  const [tabItems, setTabItems] = useState([])
  const [menuMapping, setMenuMapping] = useState({})
  const [breadcrumbItems, setBreadcrumbItems] = useState([])

  const location = useLocation()

  useEffect(() => {
    async function fetchData() {
      const menu = await getMenuList();
      const loginUser = await getCurrentUser();
      setMenus(menu)
      setCurrentUser(loginUser)
      localStorage.setItem("current_user", JSON.stringify(loginUser))
    }
    fetchData();
  }, [])

  useEffect(() => {
    const { pathname } = location
    const menuName = menuMapping[pathname]
    if (menuName) {
      if (!tabItems.filter(item => item.key === pathname).length) {
        const newTabItems = [...tabItems, {
          key: pathname,
          label: menuName,
          children: tabsRoutes[pathname] || <NotFound />,
          closable: pathname !== '/dashboard'
        }]
        setTabItems(newTabItems)
      }
      setActiveKey(pathname)
    }
  }, [location, menuMapping])

  useEffect(() => {
    const menuNames = buildMenuMapping(menus)
    setMenuMapping(menuNames)
    const dashboardKey = "/dashboard"
    setTabItems([{
      key: dashboardKey,
      label: menuNames[dashboardKey] || '工作台',
      children: tabsRoutes[dashboardKey] || <NotFound />,
      closable: false
    }])
    setActiveKey(dashboardKey)
  }, [menus])

  useEffect(() => {
    setBreadcrumbItems(buildBreadcrumbItems(menus, location))
  },[menus, location])

  const navigate = useNavigate()
  const menuClick = (e) => {
    const { key } = e
    navigate(key)
  }
  const paths = location.pathname.split('/').filter(i => i)
  const expandKey = paths.length > 1 ? paths.filter((_, index) => index < paths.length - 1).map(path => `/${path}`) : false

  const buildMenuMapping = (menus) => {
    const result = {}
    if (menus.length) {
      menus.forEach(item => {
        result[item.key] = item.label
        if (item.children && item.children.length) {
          const children = buildMenuMapping(item.children, item.key)
          Object.keys(children).forEach(k => {
            result[k] = children[k]
          })
        }
      })
    }
    return result
  }

  const closeTab = (targetKey) => {
    const newTabItems = tabItems.filter(item => item.key !== targetKey)
    const navigateTo = newTabItems.length ? newTabItems[newTabItems.length - 1].key : "/"
    setTabItems(newTabItems)
    navigate(navigateTo)
  }
  return (
    <Layout style={{ minHeight: '100vh', }}>
      <Sider collapsible collapsed={collapsed} onCollapse={(val) => setCollapsed(val)}>
        <Menu items={menus} mode="inline" onClick={menuClick} theme='dark' defaultSelectedKeys={location.pathname} defaultOpenKeys={expandKey} />
      </Sider>
      <Layout>
        <Header className='header'>
          <Breadcrumb className='crumb' items={breadcrumbItems} />
          <LoginUser user={currentUser} />
        </Header>
        <Content className='main'>
          {/* <Outlet /> */}
          <Tabs
            type="editable-card"
            hideAdd
            size='small'
            onChange={(activeKey) => navigate(activeKey)}
            onEdit={closeTab}
            activeKey={activeKey}
            items={tabItems}
          />
        </Content>
        {/* <Footer style={{ textAlign: 'center', fontWeight: 'bold' }}>Ant Design</Footer> */}
      </Layout>
    </Layout>
  )
}
