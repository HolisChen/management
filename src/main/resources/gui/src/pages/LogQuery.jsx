
import React from 'react'
import CRUD from '../components/CRUD'
import { Input } from 'antd'
import { addRole, deleteRole, getAllRoles, updateRole } from '../service/role'
export default function LogQuery() {
    const params = {
        rowKey: 'id',
        columns: [
            {
                title: "角色编码",
                dataIndex: 'roleCode',
                editable: true,
                component: <Input />
            },
            {
                title: "角色名称",
                dataIndex: 'roleName',
                editable: true,
                component: <Input />
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
            },
        ],
        create: {
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
        },
        update: {
            doUpdate: (row, data) => {
                return updateRole({ ...data, id: row.id })
            },
        },
        query: {
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
        },
        deleteRow: {
            mode: 'row',
            doDelete: (rowKey) => {
                return deleteRole(rowKey)
            }
        }

    }

    return (
        <div>
            <CRUD config={params}></CRUD>
        </div>
    )
}
