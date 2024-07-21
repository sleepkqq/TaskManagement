import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getTaskById } from '../api/tasks';
import { Descriptions, Spin, Alert } from 'antd';

const TaskDetail = () => {
    const { id } = useParams();
    const [task, setTask] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTask = async () => {
            try {
                const response = await getTaskById(id);
                setTask(response.data);
            } catch (err) {
                setError('Failed to fetch task');
            } finally {
                setLoading(false);
            }
        };

        fetchTask();
    }, [id]);

    if (loading) return <Spin />;
    if (error) return <Alert message={error} type="error" showIcon />;

    return (
        <Descriptions title="Task Details" bordered>
            <Descriptions.Item label="ID">{task.id}</Descriptions.Item>
            <Descriptions.Item label="Title">{task.title}</Descriptions.Item>
            <Descriptions.Item label="Description">{task.description}</Descriptions.Item>
            <Descriptions.Item label="Status">{task.status}</Descriptions.Item>
            <Descriptions.Item label="Priority">{task.priority}</Descriptions.Item>
            <Descriptions.Item label="Created Date">{task.createdDate}</Descriptions.Item>
            <Descriptions.Item label="Updated Date">{task.updatedDate}</Descriptions.Item>
            <Descriptions.Item label="Due Date">{task.dueDate}</Descriptions.Item>
            <Descriptions.Item label="Assignee">{task.assignee}</Descriptions.Item>
            <Descriptions.Item label="Reporter">{task.reporter}</Descriptions.Item>
        </Descriptions>
    );
};

export default TaskDetail;
