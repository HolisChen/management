import { Form, Input, Button, Row, Col, Space, Typography, Popconfirm, Table, Select } from "antd";
import { useState } from "react";
import { getUserList } from "../service/user";
import { USER_STATUS, USER_STATUS_OPTIONS } from "../constants/code_mapping";
import EditableCell from "../components/EditableCell";

export default function User() {
    const [users, setUsers] = useState([])
    const [editingId, setEditingId] = useState('')
    const [tableForm] = Form.useForm()
    const [searchForm] = Form.useForm()

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
                        <Typography.Link>保存</Typography.Link>
                        <Popconfirm title="确定取消吗？" onConfirm={cancelEdit} okText="确定" cancelText="取消">
                            <Typography.Link>取消</Typography.Link>
                        </Popconfirm>
                    </Space>
                ) : (
                    <Space>
                        <Typography.Link disabled={editingId !== ''} onClick={() => editRow(record)}>编辑</Typography.Link>
                        <Typography.Link>{enabled ? '禁用' : '启用'}</Typography.Link>
                        <Popconfirm title="确定删除吗？" okText="确定" cancelText="取消">
                            <Typography.Link>删除</Typography.Link>
                        </Popconfirm>
                    </Space>
                )
            }
        }
    ]

    const getUsers = () => {
        console.log(searchForm.getFieldValue())
        getUserList()
            .then(res => setUsers(res))
    }

    const isEditing = (record) => editingId === record.id

    const cancelEdit = () => {
        setEditingId('')
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
                            <Select options={USER_STATUS_OPTIONS}  defaultValue={'启用'}/>
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
                />
            </Form>
        </>
    )
}
