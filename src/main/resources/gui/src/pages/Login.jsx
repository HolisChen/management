import { useNavigate } from 'react-router-dom';
import { doLogin } from '../service/login';
import { Button, Form, Input } from 'antd';

export default function Login() {
  const navigate = useNavigate()
  const handleLogin = async (param) => {
    const loginSuccess = await doLogin(param)
    if (loginSuccess) {
      navigate("/", { replace: true });
    }
  }
  const onFinish = (values) => {
    console.log('Success:', values);
    handleLogin(values);
  };
  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <>
      <Form
        name="basic"
        labelCol={{
          span: 10,
        }}
        wrapperCol={{
          span: 4,
        }}
        style={{
          // maxWidth: 600,
          marginTop: '30vh',
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="用户名"
          name="loginId"
          rules={[
            {
              required: true,
              message: 'Please input your username!',
            },
          ]}
        >
          <Input placeholder='输入登录ID'/>
        </Form.Item>

        <Form.Item
          label="密码"
          name="password"
          rules={[
            {
              required: true,
              message: 'Please input your password!',
            },
          ]}
        >
          <Input.Password placeholder='请输入密码'/>
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 10,
            span: 14,
          }}
        >
          <Button type="primary" htmlType="submit">
            登录
          </Button>
        </Form.Item>
      </Form>
    </>
  );
}
