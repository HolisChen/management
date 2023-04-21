
import React from 'react'
import CRUD from '../components/CRUD'
import { Button, Input } from 'antd'
import { getAllRoles, updateRole } from '../service/role'
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
            modalTitle: '新建',
            createFormItems: [
                {
                    name: 'title',
                    label: '姓名',
                    rules: [
                        {
                            'required': true,
                            'message': '姓名必填'
                        }
                    ],
                    component: <Input />

                }
            ],
            doCreate: (data) => {
                console.log(data)
            }
        },
        update: {
            doUpdate: (row, data) => {
               return updateRole({...data,id: row.id})
            }
        },
        query: {
            conditions: [
                {
                    label: '姓名2',
                    component: <Input />,
                    name: 'name2',
                    span: 6
                },
                {
                    label: '姓名3',
                    component: <Input />,
                    name: 'name3',
                    span: 6
                }
            ],
            doQuery: (condition) => {
                return getAllRoles()
            },
        }

    }

    return (
        <div>
            <CRUD config={params}></CRUD>
        </div>
    )
}
