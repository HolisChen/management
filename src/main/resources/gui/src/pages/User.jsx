
import { useState, useEffect } from 'react'
import CRUD from '../components/CRUD'
import { USER_STATUS, USER_STATUS_OPTIONS } from '../constants/code_mapping'
import { Tag, Select, Input, message } from 'antd'
import { getAllRoles } from '../service/role'
import { addUser, deleteUser, disableUser, enableUser, getUserPage, resetPassword, updateUser } from '../service/user'
import { formatDate } from '../utils/date_util'
export default function User() {
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
            rules: [
                {
                    required: true,
                    message: '用户名必填'
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
            title: "创建人",
            dataIndex: 'createByName',
        },
        {
            title: "创建时间",
            dataIndex: 'createAt',
            render: (text) => formatDate(text)
        }
    ]

    const queryConfig = {
        doQuery: (conditions) => {
            return getUserPage(conditions)
        },
        conditions: [
            {
                label: '登录ID',
                component: <Input />,
                name: 'loginId',
                span: 6
            },
            {
                label: '状态',
                component: <Select options={USER_STATUS_OPTIONS} allowClear />,
                name: 'status',
                span: 6
            },
        ]
    }

    const updateConfig = {
        initUpdateFieldsValue: (row) => ({ ...row, roles: row.roles.map(item => item.id) }),
        doUpdate: (record, data) => {
            const payload = { ...data, bindingRoles: data.roles, id: record.id }
            return updateUser(payload)
        }
    }

    const paginationConfig = {

    }

    const deleteConfig = {
        doDelete: (id) => {
            return deleteUser(id)
        }
    }

    const rowOperations = [
        {
            buttonName: (row) => row.status === 1 ? '禁用' : '启用',
            onClick: (row, rowList, setRowList) => {
                const tips = row.status === 1 ? '禁用成功' : '启用成功'
                const invokeFunction = row.status === 1 ? disableUser : enableUser
                invokeFunction(row.id).then((res) => {
                    const newUserList = rowList.map(user => {
                        if (user.id === row.id) {
                            user.status = 1 ^ row.status
                        }
                        return user;
                    })
                    setRowList(newUserList)
                    message.success(tips)
                }).catch(err => {
                })
            },
            confirmMsg: '确定吗？'
        },
        {
            buttonName:'重置密码',
            onClick: (row, rowList, setRowList) => {
                const {id:userId} = row
                resetPassword(userId)
                    .then(newPassword => {
                        message.success(`密码重置成功，新密码是：${newPassword}`)
                    }).catch(err => {

                    })

            },
            confirmMsg: '确定重置吗？'
        }
    ]

    const createConfig = {
        modalTitle: '新增用户',
        createFormItems: [
            {
                name: 'loginId',
                label: '登录ID',
                rules: [
                    {
                        'required': true,
                        'message': '登录ID必填'
                    }
                ],
                component: <Input />
            },
            {
                name: 'username',
                label: '用户名',
                rules: [
                    {
                        'required': true,
                        'message': '用户名必填'
                    }
                ],
                component: <Input />
            },
            {
                name: 'phoneNumber',
                label: '手机号',
                rules: [
                ],
                component: <Input />
            },
            {
                name: 'email',
                label: '邮箱',
                rules: [
                ],
                component: <Input />
            },
            {
                name: 'password',
                label: '密码',
                rules: [
                    {
                        required: true,
                        message: '密码必填'
                    }
                ],
                component: <Input type='password' />
            },
            {
                name: 'bindingRoles',
                label: '分配角色',
                rules: [

                ],
                component: <Select options={roleOptions} mode="tags" />

            },
        ],
        doCreate: (data) => {
            return addUser(data)
        }

    }

    return (
        <CRUD columns={columns}
            queryConfig={queryConfig}
            paginationConfig={paginationConfig}
            updateConfig={updateConfig}
            deleteConfig={deleteConfig}
            rowOperations={rowOperations}
            createConfig={createConfig}
        >

        </CRUD>
    )
}
