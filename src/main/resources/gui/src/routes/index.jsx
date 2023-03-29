import { Navigate } from 'react-router-dom'
import Home from '../pages/Home';
import User from '../pages/User';
import Role from '../pages/Role';
import Menu from '../pages/Menu';
import Dashboard from '../pages/Dashboard';
import Login from '../pages/Login';

const routes = [
    {
        path: '/',
        element: <Navigate to={'/dashboard'}></Navigate>
    },
    {
        path:'/login',
        element: <Login />
    },
    {
        path: "/",
        element: <Home />,
        children: [
            {
                path:"dashboard",
                element: <Dashboard/>,
            },
            {
                path:"systemSetting",
                children: [
                    {
                        path: "user",
                        element: <User/>,
                    },
                    {
                        path: "role",
                        element: <Role/>,
                    },
                    {
                        path: "menu",
                        element: <Menu/>,
                    }
                ]
            }
        ]
    }
]

export default routes;