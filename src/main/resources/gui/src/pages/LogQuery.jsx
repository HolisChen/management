
import {useState, useEffect} from 'react'
import CRUD from '../components/CRUD'
import {USER_STATUS} from '../constants/code_mapping'
import { Tag, Select, Input } from 'antd'
import { getAllRoles } from '../service/role'
import { getUserPage } from '../service/user'
export default function LogQuery() {
    const [roleOptions, setRoleOptions] = useState([])
    useEffect(() => {
        getAllRoles().then(res => {
            const options = res.map(role => ({
                value: role.id,
                label: role.roleName
            }))
            setRoleOptions(options);
        })
    }, [])

    const columns = [
        {
            title: "登录ID",
            dataIndex: 'loginId',
        },
        {
            title: "用户名",
            dataIndex: 'username',
            editable: true,
            component: <Input />,
            rules:[
                {
                    required : true,
                    message :'用户名必填'
                }
            ]
        },
        {
            title: "状态",
            dataIndex: 'status',
            render: (text) => {
                return USER_STATUS[text]
            },
        },
        {
            title: "手机号",
            dataIndex: 'phoneNumber',
            editable: true,
            component: <Input />
        },
        {
            title: "邮箱",
            dataIndex: 'email',
            editable: true,
            component: <Input />
        },
        {
            title: "分配角色",
            dataIndex: 'roles',
            editable: true,
            render: (text, record, index) => {
                const { roles } = record;
                const tags = roles.map(item => (<Tag key={item.id} color="blue">
                    {item.roleName}
                </Tag>))
                return (
                    tags
                )
            },
            component: <Select mode="tags" options={roleOptions} />
        },
        {
            title: "创建时间",
            dataIndex: 'createAt',
        }
    ]

    const queryConfig = {
        doQuery: (conditions) => {
           return getUserPage(conditions)
        }
    }

    const paginationConfig = {

    }

    return (
        <CRUD columns= {columns} 
        queryConfig = {queryConfig}
        paginationConfig = {paginationConfig}
        >
            
        </CRUD>
    )
}
