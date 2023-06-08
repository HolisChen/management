import { Avatar, Badge } from 'antd';
import defaultAvatar from './default_avatar.png'

export default function LoginUser(props) {
    const { user } = props
    return (
        <>
            <div style={{ position: 'absolute', right: '30px', top: '20px' }}>
                <Badge count={151}>
                    <Avatar src={defaultAvatar} style={{ backgroundColor: '' }} onClick={() => {
                    }} />
                </Badge>
                <span style={{ position: 'relative', bottom:'-9px' }}>
                    {user.username}
                </span>
            </div>
        </>
    )
}
