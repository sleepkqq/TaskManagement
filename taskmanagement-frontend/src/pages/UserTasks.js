import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUserAssignedTasks, getUserReportedTasks } from '../api/users';
import { List, Typography } from 'antd';

const { Title } = Typography;

const UserTasks = () => {
    const { username } = useParams();
    const [assignedTasks, setAssignedTasks] = useState([]);
    const [reportedTasks, setReportedTasks] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const assignedResponse = await getUserAssignedTasks(username);
                const reportedResponse = await getUserReportedTasks(username);

                setAssignedTasks(assignedResponse.data);
                setReportedTasks(reportedResponse.data);
            } catch (error) {
                console.error('Error fetching tasks', error);
            } finally {
                setLoading(false);
            }
        };
        fetchTasks();
    }, [username]);

    return (
        <div>
            <Title level={2}>Assigned Tasks</Title>
            <List
                loading={loading}
                itemLayout="horizontal"
                dataSource={assignedTasks}
                renderItem={(task) => (
                    <List.Item>
                        <List.Item.Meta
                            title={<a href={`/task/${task.id}`}>{task.title}</a>}
                            description={task.description}
                        />
                    </List.Item>
                )}
            />
            <Title level={2}>Reported Tasks</Title>
            <List
                loading={loading}
                itemLayout="horizontal"
                dataSource={reportedTasks}
                renderItem={(task) => (
                    <List.Item>
                        <List.Item.Meta
                            title={<a href={`/task/${task.id}`}>{task.title}</a>}
                            description={task.description}
                        />
                    </List.Item>
                )}
            />
        </div>
    );
};

export default UserTasks;
