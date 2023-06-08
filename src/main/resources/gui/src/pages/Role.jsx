
import React, { Children, useState } from 'react'
import CRUD from '../components/CRUD'
import { Input, Modal, Tree, message } from 'antd'
import { addRole, deleteRole, getAllRoles, getDepByRole, getResourceByRole, saveDepByRole, saveResourceByRole, updateRole } from '../service/role'
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
                    message: '角色编码必填'
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
                    message: '角色名称必填'
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
            title: "创建人",
            dataIndex: 'createByName',
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
        totalName: 'total'
    }

    const rowOperations = [
        {
            buttonName: '分配权限',
            onClick: (row) => {
                const { id, roleName } = row
                setBindModalOpen(true)
                queryBindResource(id)
                setEditRoleId(id)
            }
        },
        {
            buttonName: '关联部门',
            onClick: (row) => {
                const { id, roleName } = row
                setDepModalOpen(true)
                queryBindDep(id)
                setEditRoleId(id)
            }
        }
    ]

    const [bindResource, setBindResource] = useState([])
    const [bindDep, setBindDep] = useState([])
    const [checkedKeys, setCheckedKeys] = useState([])
    const [halfCheckedKeys, setHalfCheckedKeys] = useState([])
    const [bindModalOpen, setBindModalOpen] = useState(false)
    const [depModalOpen, setDepModalOpen] = useState(false)
    const [editRoleId, setEditRoleId] = useState('')
    const queryBindResource = (roleId) => {
        getResourceByRole(roleId)
            .then(res => {
                setBindResource(res)
                setCheckedKeys(getCheckedKeys(res))
            })
    }

    const queryBindDep = (roleId) => {
        getDepByRole(roleId)
            .then(res => {
                setBindDep(res)
                setCheckedKeys(getCheckedKeys(res))
            })
    }

    const getCheckedKeys = (tree = []) => {
        const keys = []
        if (tree.length) {
            tree.forEach(item => {
                let { checked = false, children = [], id } = item
                children = children === null ? [] : children
                if (children.length) {
                    const childSelected = getCheckedKeys(children)
                    keys.push(...childSelected)
                }
                if (checked === true && !children.length) {
                    keys.push(id)
                }

            })
        }
        return keys;
    }

    const closeBindModal = () => {
        setBindModalOpen(false)
        setEditRoleId('')
    }

    const closeDepModal = () => {
        setEditRoleId('')
        setDepModalOpen(false)
    }

    const onCheck = (keys, e) => {
        setHalfCheckedKeys(e.halfCheckedKeys)
        setCheckedKeys(keys)
    }

    const onSaveBind = () => {
        saveResourceByRole(editRoleId, [...checkedKeys, ...halfCheckedKeys])
            .then(res => {
                message.success('保存成功')
                closeBindModal()
            })
    }

    const onSaveBindDep = () => {
        saveDepByRole(editRoleId, [...checkedKeys, ...halfCheckedKeys])
            .then(res => {
                message.success('保存成功')
                closeDepModal()
            })
    }

    return (
        <div>
            <CRUD columns={columns}
                queryConfig={queryConfig}
                createConfig={createConfig}
                updateConfig={updateConfig}
                deleteConfig={deleteConfig}
                rowOperations={rowOperations}
            ></CRUD>

            <Modal title='分配权限' open={bindModalOpen} okText={'保存'} cancelText={'取消'} onCancel={closeBindModal} onOk={onSaveBind}>
                <Tree
                    checkable
                    treeData={bindResource}
                    fieldNames={{
                        title: 'resourceName',
                        key: 'id'
                    }}
                    checkedKeys={checkedKeys}
                    onCheck={onCheck} 
                    defaultExpandAll = {true}
                />
            </Modal>

            <Modal title='关联部门' open={depModalOpen} okText={'保存'} cancelText={'取消'} onCancel={closeDepModal} onOk={onSaveBindDep}>
                <Tree
                    checkable
                    treeData={bindDep}
                    fieldNames={{
                        title: 'departmentName',
                        key: 'id'
                    }}
                    checkedKeys={checkedKeys}
                    onCheck={onCheck}
                    defaultExpandParent={true}
                    defaultExpandAll = {true}
                />
            </Modal>


        </div>
    )
}
