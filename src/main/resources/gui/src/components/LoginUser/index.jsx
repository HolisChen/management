import React from 'react'
import { Avatar } from 'antd';
import { UserOutlined } from '@ant-design/icons';



export default function LoginUser(props) {
    const { user } = props
    return (
        <>
            <div style={{ float: 'right', position: 'relative', top: '-64px', right: '20px' }}>
                <Avatar icon={<UserOutlined />} />
                <span>{user.username}</span>
            </div>

        </>
    )
}
