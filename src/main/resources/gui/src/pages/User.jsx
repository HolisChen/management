import { Form, Input, Button, Row, Col, Space, Typography, Popconfirm, Table, Select, message, Pagination } from "antd";
import { useEffect, useState } from "react";
import { disableUser, enableUser, getUserPage, updateUser } from "../service/user";
import { USER_STATUS, USER_STATUS_OPTIONS } from "../constants/code_mapping";
import EditableCell from "../components/EditableCell";

export default function User() {
    const [users, setUsers] = useState([])
    const [total, setTotal] = useState(0)
    const [pageNo, setPageNo] = useState(1)
    const [pageSize, setPageSize] = useState(10)
    const [editingId, setEditingId] = useState('')
    const [tableForm] = Form.useForm()
    const [searchForm] = Form.useForm()

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
    useEffect(()=>{
        getUsers();
    },[pageNo,pageSize])

    const editRow = (record) => {
        setEditingId(record.id);
        tableForm.setFieldsValue({
            ...record,
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
                        <Popconfirm title="确定删除吗？" okText="确定" cancelText="取消">
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
                        <Form.Item label={'状态'} name={'status'} initialValue={1}>
                            <Select options={USER_STATUS_OPTIONS} allowClear/>
                        </Form.Item>
                    </Col>
                    <Col span={2}>
                        <Button type={"primary"} onClick={() => {
                            getUsers()
                        }}>查询</Button>
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
                onShowSizeChange={(current, size)=> {
                    console.log('@', current, size)
                    setPageSize(size)
                }}
                defaultCurrent={1} 
                defaultPageSize={10}/>
            </Form>
        </>
    )
}
