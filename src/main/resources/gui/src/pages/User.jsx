import {Table, Form, Input, Button, Row, Col, Space} from "antd";
import {useState} from "react";
import {getUserList} from "../service/user";
import {USER_STATUS} from "../constants/code_mapping";

export default function User() {
    const [users, setUsers] = useState([])
    const searchForm = Form.useForm()
    const columns = [
        {
            title: "登录ID",
            dataIndex: 'loginId',
        },
        {
            title: "用户名",
            dataIndex: 'username',
        },
        {
            title: "状态",
            dataIndex: 'status',
            render: (text) => {
               return  USER_STATUS[text]
            }
        },
        {
            title: "手机号",
            dataIndex: 'phoneNumber',
        },
        {
            title: "邮箱",
            dataIndex: 'email',
        },
        {
            title: "创建时间",
            dataIndex: 'createAt',
        }
    ]

    const getUsers = () => {
        getUserList()
            .then(res => setUsers(res))
    }

    return (
        <>
            <Form component={false}>
                <Row>
                    <Col span={5}>
                        <Form.Item label={'登录ID'} name={'loginId'}>
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={5}>
                        <Form.Item label={'状态'} name={'status'}>
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={2}>
                        <Button type={"primary"} onClick={() => {
                            getUsers()
                        }}>查询</Button>
                    </Col>
                </Row>
            </Form>
            <Table columns={columns} dataSource={users} rowKey={'id'}/>
        </>
    )
}
