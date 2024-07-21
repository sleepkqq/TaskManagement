import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUserByUsername } from '../api/users';
import { Descriptions, Spin, Alert } from 'antd';

const UserDetail = () => {
    const { username } = useParams();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserByUsername(username);
                setUser(response.data);
            } catch (err) {
                setError('Failed to fetch user');
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, [username]);

    if (loading) return <Spin />;
    if (error) return <Alert message={error} type="error" showIcon />;

    return (
        <Descriptions title="User Details" bordered>
            <Descriptions.Item label="ID">{user.id}</Descriptions.Item>
            <Descriptions.Item label="Username">{user.username}</Descriptions.Item>
            <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
            <Descriptions.Item label="Roles">{user.roles.join(', ')}</Descriptions.Item>
            <Descriptions.Item label="Assigned Tasks">{user.assignedTasks.join(', ')}</Descriptions.Item>
            <Descriptions.Item label="Reported Tasks">{user.reportedTasks.join(', ')}</Descriptions.Item>
        </Descriptions>
    );
};

export default UserDetail;
