import React from 'react'
import { Avatar, Badge } from 'antd';
import defaultAvatar from './default_avatar.png'

export default function LoginUser(props) {
    const { user } = props
    return (
        <>
            <div style={{ position: 'absolute', right: '30px', top: '20px' }}>
                <Badge count={151}>
                    <Avatar src={defaultAvatar} style={{ backgroundColor: '' }} onClick={() => {
                        alert(1)
                    }} />
                </Badge>
            </div>
        </>
    )
}
