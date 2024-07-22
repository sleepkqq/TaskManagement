import React, { useState } from 'react';
import { Form, Input, Button, Alert } from 'antd';
import { signIn } from '../api/auth';
import { setAuthToken } from '../utils/auth';
import { useNavigate } from 'react-router-dom';

const SignIn = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const onFinish = async (values) => {
        setLoading(true);
        setError(null);
        try {
            const response = await signIn(values);
            setAuthToken(response.data.accessToken, response.data.accessExpiresIn);
            navigate('/');
        } catch (err) {
            setError('Invalid username or password');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h1>Sign In</h1>
            {error && <Alert message={error} type="error" showIcon />}
            <Form layout="vertical" onFinish={onFinish}>
                <Form.Item name="username" label="Username" rules={[{ required: true, message: 'Please input your username!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="password" label="Password" rules={[{ required: true, message: 'Please input your password!' }]}>
                    <Input.Password />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Sign In
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default SignIn;
