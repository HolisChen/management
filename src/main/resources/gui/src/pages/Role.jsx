import { Form, Input, Button, Row, Col, Space, Typography, Popconfirm, Table, Select, message, Pagination, Tag, Modal } from "antd";
import { useEffect, useState } from "react";
import { addUser, deleteUser, disableUser, enableUser, getUserPage, updateUser } from "../service/user";
import EditableCell from "../components/EditableCell";
import { getAllRoles, updateRole } from "../service/role";
import FormItem from "antd/es/form/FormItem";

export default function Role() {
    const [roles, setRoles] = useState([])
    const [editingId, setEditingId] = useState('')
    const [openAdd, setOpenAdd] = useState(false)
    const [tableForm] = Form.useForm()
    const [searchForm] = Form.useForm()
    const [addForm] = Form.useForm()

    const queryRoles = () => {
      getAllRoles().then(data => setRoles(data))
    }
    useEffect(() => {
      queryRoles();
    }, [])


    const editRow = (record) => {
        setEditingId(record.id);
        tableForm.setFieldsValue({
            ...record,
        });
    }
    const columns = [
        {
            title: "角色编码",
            dataIndex: 'roleCode',
            editable: true,
            onCell: (record) => ({
                record,
                name: 'roleCode',
                editing: isEditing(record),
                component: <Input />
            }),
        },
        {
            title: "角色名称",
            dataIndex: 'roleName',
            editable: true,
            onCell: (record) => ({
                record,
                name: 'roleName',
                editing: isEditing(record),
                component: <Input />
            }),
        },
        {
            title: "角色描述",
            dataIndex: 'roleDescription',
            editable: true,
            onCell: (record) => ({
                record,
                name: 'roleDescription',
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
            .then(row => updateRole({ id: record.id, ...row }))
            .then(r => {
                message.success('修改成功')
                cancelEdit()
                queryRoles()
            })
    }

    

    const handleDelete = (userId) => {
        
    }
    const handleAdd = () => {
        addForm.validateFields()
            .then((value) => {
                return addUser(value)
            })
            .then(res => {
                setOpenAdd(false)
                addForm.resetFields()
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
                        
                    </Col>
                    <Col span={2}   >
                        <Space>
                            <Button type={"primary"} onClick={() => {
                                queryRoles()
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
                    dataSource={roles}
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
            </Form>

            <Modal open={openAdd} onCancel={() => {
                setOpenAdd(false)
                addForm.resetFields()
            }} onOk={handleAdd} title={'新增用户'}>
                <Form form={addForm} component={false} labelCol={{span:4}} wrapperCol={{span:20}} autoComplete={false}>
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
                </Form>
            </Modal>

        </>
    )
}
