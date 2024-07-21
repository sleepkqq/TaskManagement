import React, { useEffect, useState } from 'react';
import { getAllUsers } from '../api/users';
import { List, Typography, Spin, Alert, Tag, Tooltip } from 'antd';
import { Link } from 'react-router-dom';

const { Title } = Typography;

const UserList = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await getAllUsers();
                setUsers(response.data);
            } catch (err) {
                setError('Failed to fetch users');
            } finally {
                setLoading(false);
            }
        };

        fetchUsers();
    }, []);

    if (loading) return <Spin />;
    if (error) return <Alert message={error} type="error" showIcon />;

    return (
        <div>
            <Title level={2}>User List</Title>
            <List
                itemLayout="vertical"
                size="large"
                dataSource={users}
                renderItem={(user) => (
                    <List.Item
                        key={user.id}
                        extra={
                            <div>
                                <Tooltip title="Assigned Tasks">
                                    <div>
                                        <strong>Assigned Tasks:</strong> {user.assignedTasks.length}
                                    </div>
                                </Tooltip>
                                <Tooltip title="Reported Tasks">
                                    <div>
                                        <strong>Reported Tasks:</strong> {user.reportedTasks.length}
                                    </div>
                                </Tooltip>
                            </div>
                        }
                    >
                        <List.Item.Meta
                            title={<Link to={`/users/${user.username}`}>{user.username}</Link>}
                            description={
                                <div>
                                    <div><strong>Email:</strong> {user.email}</div>
                                    <div>
                                        <strong>Roles:</strong> 
                                        {user.roles.map(role => (
                                            <Tag key={role} color="blue">{role}</Tag>
                                        ))}
                                    </div>
                                </div>
                            }
                        />
                    </List.Item>
                )}
            />
        </div>
    );
};

export default UserList;
