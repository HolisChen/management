import { Form, Input, Button, Row, Col, Space, Typography, Popconfirm, Table, Select, message, Pagination, Tag, Modal } from "antd";
import { useEffect, useState } from "react";
import { addUser, deleteUser, disableUser, enableUser, getUserPage, updateUser } from "../service/user";
import { USER_STATUS, USER_STATUS_OPTIONS } from "../constants/code_mapping";
import EditableCell from "../components/EditableCell";
import { getAllRoles } from "../service/role";
import FormItem from "antd/es/form/FormItem";

export default function User() {
    const [users, setUsers] = useState([])
    const [total, setTotal] = useState(0)
    const [pageNo, setPageNo] = useState(1)
    const [pageSize, setPageSize] = useState(10)
    const [editingId, setEditingId] = useState('')
    const [openAdd, setOpenAdd] = useState(false)
    const [roleOptions, setRoleOptions] = useState([])
    const [tableForm] = Form.useForm()
    const [searchForm] = Form.useForm()
    const [addForm] = Form.useForm()

    const getUsers = () => {
        const payload = searchForm.getFieldValue()
        getUserPage({
            pageSize,
            pageNo,
            ...payload
        }).then(res => {
            setUsers(res.contents)
            setTotal(res.total)
        })
    }
    useEffect(() => {
        getUsers();
    }, [pageNo, pageSize])

    useEffect(() => {
        getAllRoles().then(res => {
            const options = res.map(role => ({
                value: role.id,
                label: role.roleName
            }))
            setRoleOptions(options);
        })
    }, [])

    const editRow = (record) => {
        setEditingId(record.id);
        tableForm.setFieldsValue({
            ...record,
            bindingRoles: record.roles.map(item => item.id)
        });
    }
    const columns = [
        {
            title: "登录ID",
            dataIndex: 'loginId',
        },
        {
            title: "用户名",
            dataIndex: 'username',
            editable: true,
            onCell: (record) => ({
                record,
                name: 'username',
                editing: isEditing(record),
                component: <Input />
            }),
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
            onCell: (record) => ({
                record,
                name: 'phoneNumber',
                editing: isEditing(record),
                component: <Input />
            }),
        },
        {
            title: "邮箱",
            dataIndex: 'email',
            editable: true,
            onCell: (record) => ({
                record,
                name: 'email',
                editing: isEditing(record),
                component: <Input />
            }),
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
            onCell: (record) => ({
                record,
                name: 'bindingRoles',
                editing: isEditing(record),
                component: <Select mode="tags" options={roleOptions} />
            }),
        },
        {
            title: "创建时间",
            dataIndex: 'createAt',
        },
        {
            title: '操作',
            dataIndex: 'operations',
            render: (text, record, index) => {
                const editable = isEditing(record)
                const enabled = record.status === 1
                return editable ? (
                    <Space>
                        <Typography.Link onClick={() => saveEdit(record)}>保存</Typography.Link>
                        <Popconfirm title="确定取消吗？" onConfirm={cancelEdit} okText="确定" cancelText="取消">
                            <Typography.Link>取消</Typography.Link>
                        </Popconfirm>
                    </Space>
                ) : (
                    <Space>
                        <Typography.Link disabled={editingId !== ''} onClick={() => editRow(record)}>编辑</Typography.Link>
                        <Typography.Link disabled={editingId !== ''} onClick={() => switchStatus(record)}>{enabled ? '禁用' : '启用'}</Typography.Link>
                        <Popconfirm title="确定删除吗？" okText="确定" cancelText="取消" onConfirm={() => handleDelete(record.id)}>
                            <Typography.Link disabled={editingId !== ''} type="danger">删除</Typography.Link>
                        </Popconfirm>
                    </Space>
                )
            }
        }
    ]



    const isEditing = (record) => editingId === record.id

    const cancelEdit = () => {
        setEditingId('')
    }

    const saveEdit = (record) => {
        tableForm.validateFields()
            .then(row => updateUser({ ...record, ...row }))
            .then(r => {
                message.success('修改成功')
                cancelEdit()
                getUsers();
            })
    }

    const switchStatus = (record) => {
        const { id } = record
        if (record.status === 0) {
            //启用
            enableUser(id)
                .then(res => {
                    message.success('启用成功')
                    const newUserList = users.map(user => {
                        if (user.id === id) {
                            user.status = 1
                        }
                        return user;
                    })
                    setUsers(newUserList)
                })
        } else {
            disableUser(id).then(res => {
                message.success('禁用成功')
                const newUserList = users.map(user => {
                    if (user.id === id) {
                        user.status = 0
                    }
                    return user;
                })
                setUsers(newUserList)
            })
        }
    }

    const handleDelete = (userId) => {
        deleteUser(userId).then(res => {
           return getUsers()
        }).catch(err => {
            
        })
    }
    const handleAdd = () => {
        addForm.validateFields()
            .then((value) => {
                return addUser(value)
            })
            .then(res => {
                setOpenAdd(false)
                addForm.resetFields()
                return getUsers()
            })
            .catch(err => {

            })
    }

    return (
        <>
            <Form form={searchForm} component={false} >
                <Row>
                    <Col span={5}>
                        <Form.Item label={'登录ID'} name={'loginId'}>
                            <Input />
                        </Form.Item>
                    </Col>
                    <Col span={3}>
                        <Form.Item label={'状态'} name={'status'}>
                            <Select options={USER_STATUS_OPTIONS} allowClear />
                        </Form.Item>
                    </Col>
                    <Col span={2}   >
                        <Space>
                            <Button type={"primary"} onClick={() => {
                                getUsers()
                            }}>查询</Button>

                            <Button onClick={() => {
                                setOpenAdd(true)
                            }}>新增</Button>
                        </Space>
                    </Col>
                </Row>
            </Form>
            <Form form={tableForm} component={false} >
                <Table columns={columns}
                    dataSource={users}
                    rowKey={'id'}
                    bordered
                    components={{
                        body: {
                            cell: EditableCell
                        }
                    }}
                    pagination={false}
                >
                </Table>
                <Pagination total={total}
                    onChange={(pageNo, pageSize) => {
                        setPageNo(pageNo)
                        setPageSize(pageSize)
                    }}
                    onShowSizeChange={(current, size) => {
                        console.log('@', current, size)
                        setPageSize(size)
                    }}
                    defaultCurrent={1}
                    defaultPageSize={10} />
            </Form>

            <Modal open={openAdd} onCancel={() => {
                setOpenAdd(false)
                addForm.resetFields()
            }} onOk={handleAdd} title={'新增用户'}>
                <Form form={addForm} component={false} labelCol={{span:4}} wrapperCol={{span:16}} autoComplete={false}>
                    <FormItem label={'登录ID'} name={'loginId'} rules={[
                        {
                            required:true,
                            message:'登录ID必填'
                        }
                    ]}>
                        <Input/>
                    </FormItem>
                    <FormItem label={'用户名'} name={'username'} rules={[
                        {
                            required:true,
                            message:'用户名必填'
                        }
                    ]}>
                        <Input />
                    </FormItem>
                    <FormItem label={'手机号'} name={'phoneNumber'}>
                        <Input />
                    </FormItem>
                    <FormItem label={'邮箱'} name={'email'}>
                        <Input/>
                    </FormItem>
                    <FormItem label={'密码'} name={'password'} rules={[
                        {
                            required:true,
                            message:'密码必填'
                        }
                    ]}>
                        <Input type="password"/>
                    </FormItem>
                    <FormItem label={'分配角色'} name={'bindingRoles'}>
                        <Select options={roleOptions} mode="tags"/>
                    </FormItem>
                </Form>
            </Modal>

        </>
    )
}
