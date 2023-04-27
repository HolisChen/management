
import React from 'react'
import CRUD from '../components/CRUD'
import { Input } from 'antd'
import { addRole, deleteRole, getAllRoles, updateRole } from '../service/role'
import { formatDate } from '../utils/date_util'
export default function Role() {
    const columns = [
        {
            title: "角色编码",
            dataIndex: 'roleCode',
            editable: true,
            component: <Input />,
            rules: [
              {
                required: true,
                message:'角色编码必填'
              }
            ]
        },
        {
            title: "角色名称",
            dataIndex: 'roleName',
            editable: true,
            component: <Input />,
            rules: [
              {
                required: true,
                message:'角色名称必填'
              }
            ]
        },
        {
            title: "角色描述",
            dataIndex: 'roleDescription',
            editable: true,
            component: <Input />
        },
        {
            title: "创建时间",
            dataIndex: 'createAt',
            render: (text) => formatDate(text)
        },
    ]
    const queryConfig = {
        conditions: [
            {
                label: '角色编码',
                component: <Input />,
                name: 'roleCode',
                span: 6
            },
            {
                label: '角色名称',
                component: <Input />,
                name: 'roleName',
                span: 6
            }
        ],
        doQuery: (condition) => {
            return getAllRoles()
        },
    }
    const createConfig = {
        modalTitle: '新增角色',
        createFormItems: [
            {
                name: 'roleCode',
                label: '角色编码',
                rules: [
                    {
                        'required': true,
                        'message': '角色编码必填'
                    }
                ],
                component: <Input />
            },
            {
                name: 'roleName',
                label: '角色名称',
                rules: [
                    {
                        'required': true,
                        'message': '角色名称必填'
                    }
                ],
                component: <Input />
            },
            {
                name: 'roleDescription',
                label: '角色描述',
                rules: [
                ],
                component: <Input />
            }
        ],
        doCreate: (data) => {
            return addRole(data)
        }
    }
    const updateConfig = {
        doUpdate: (row, data) => {
            return updateRole({ ...data, id: row.id })
        },
    }
    const deleteConfig = {
        mode: 'row',
        doDelete: (rowKey) => {
            return deleteRole(rowKey)
        }
    }
    const paginationConfig = {
        totalName : 'total'
    }

    return (
        <div>
            <CRUD columns = {columns} 
            queryConfig = {queryConfig} 
            createConfig = {createConfig}
            updateConfig = {updateConfig}
            deleteConfig = {deleteConfig}
            ></CRUD>
        </div>
    )
}
