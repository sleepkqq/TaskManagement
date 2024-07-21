import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import { getAuthToken, removeAuthToken } from '../utils/auth';

const { Header, Content, Footer } = Layout;

const AppLayout = ({ children }) => {
    const navigate = useNavigate();
    const token = getAuthToken();

    const handleSignOut = () => {
        removeAuthToken();
        navigate('/sign-in');
    };

    return (
        <Layout className="layout">
            <Header>
                <div className="logo" />
                <Menu theme="dark" mode="horizontal">
                    <Menu.Item key="1">
                        <Link to="/">Home</Link>
                    </Menu.Item>
                    {token ? (
                        <>
                            <Menu.Item key="4">
                                <Link to="/users">Users</Link>
                            </Menu.Item>
                            <Menu.Item key="5">
                                <Link to="/tasks">Tasks</Link>
                            </Menu.Item>
                            <Menu.Item key="6" onClick={handleSignOut}>
                                Sign Out
                            </Menu.Item>
                        </>
                    ) : (
                        <>
                            <Menu.Item key="2">
                                <Link to="/sign-in">Sign In</Link>
                            </Menu.Item>
                            <Menu.Item key="3">
                                <Link to="/sign-up">Sign Up</Link>
                            </Menu.Item>
                        </>
                    )}
                </Menu>
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <div className="site-layout-content">{children}</div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>Task Management ©2023</Footer>
        </Layout>
    );
};

export default AppLayout;
