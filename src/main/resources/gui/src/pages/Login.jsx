import { useNavigate } from 'react-router-dom';
import { doLogin, getCapture } from '../service/login';
import { Button, Col, Form, Row, Input, Image } from 'antd';
import { useEffect, useState } from 'react';

export default function Login() {
  const navigate = useNavigate()
  const [captureId, setCaptureId] = useState('')
  const [captureUrl, setCaptureUrl] = useState('')

  useEffect(()=> {
    refreshCapture()
  }, [])
  const handleLogin = async (param) => {
    const loginSuccess = await doLogin({...param, captureId})
    if (loginSuccess) {
      navigate("/", { replace: true });
    } else {
      refreshCapture()
    }
  }
  const onFinish = (values) => {
    handleLogin(values);
  };
  const onFinishFailed = (errorInfo) => {
  };

  const refreshCapture = () => {
    getCapture()
    .then(res => {
      const {captureId, capture} = res
      setCaptureId(captureId)
      setCaptureUrl(`data:image/png;base64,${capture}`)
    })
  }

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
          'padding-top': '30vh',
          'min-height':'100vh'
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
              message: '请输入登录用户名!',
            },
          ]}
        >
          <Input placeholder='输入登录ID' />
        </Form.Item>

        <Form.Item
          label="密码"
          name="password"
          rules={[
            {
              required: true,
              message: '请输入密码!',
            },
          ]}
        >
          <Input.Password placeholder='请输入密码' />
        </Form.Item>

        <Form.Item 
          label="验证码"
          name="capture"
          rules={[
            {
              required: true,
              message: '请输入验证码!',
            },
          ]}
          >
          <Row>
            <Col span={14}>
              <Input placeholder='请输入验证码'/>
            </Col>
            <Col span={10}>
              <Image src={captureUrl} preview= {false} onClick={refreshCapture} title='点击刷新验证码'></Image>
            </Col>
          </Row>
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
