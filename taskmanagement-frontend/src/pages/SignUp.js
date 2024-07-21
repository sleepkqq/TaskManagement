import React, { useState } from 'react';
import { Form, Input, Button, Alert } from 'antd';
import { signUp } from '../api/auth';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const onFinish = async (values) => {
        setLoading(true);
        setError(null);
        try {
            await signUp(values);
            setSuccess(true);
            setTimeout(() => {
                navigate('/sign-in');
            }, 2000);
        } catch (err) {
            setError('Failed to create account');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h1>Sign Up</h1>
            {error && <Alert message={error} type="error" showIcon />}
            {success && <Alert message="Account created successfully! Redirecting to sign in..." type="success" showIcon />}
            <Form layout="vertical" onFinish={onFinish}>
                <Form.Item name="username" label="Username" rules={[{ required: true, message: 'Please input your username!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="email" label="Email" rules={[{ required: true, message: 'Please input your email!' }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="password" label="Password" rules={[{ required: true, message: 'Please input your password!' }]}>
                    <Input.Password />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Sign Up
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default SignUp;
