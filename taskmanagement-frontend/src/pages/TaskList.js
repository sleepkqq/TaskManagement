import React, { useEffect, useState } from 'react';
import { getAllTasks, deleteTaskById } from '../api/tasks';
import { List, Typography, Spin, Alert, Button, Modal, notification } from 'antd';
import { Link } from 'react-router-dom';
import CreateTask from '../components/CreateTask';

const { Title } = Typography;

const TaskList = () => {
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isModalVisible, setIsModalVisible] = useState(false);

    const fetchTasks = async () => {
        try {
            const response = await getAllTasks();
            setTasks(response.data);
        } catch (err) {
            setError('Failed to fetch tasks');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTasks();
    }, []);

    const handleDeleteTask = (id) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this task?',
            okText: 'Yes',
            cancelText: 'No',
            onOk: async () => {
                try {
                    await deleteTaskById(id);
                    fetchTasks();
                    notification.success({
                        message: 'Success',
                        description: 'Task successfully deleted',
                    });
                } catch (err) {
                    setError('Failed to delete task');
                }
            },
        });
    };

    if (loading) return <Spin />;
    if (error) return <Alert message={error} type="error" showIcon />;

    return (
        <div>
            <Title level={2}>Task List</Title>
            <Button type="primary" onClick={() => setIsModalVisible(true)}>Create Task</Button>
            <List
                itemLayout="horizontal"
                dataSource={tasks}
                renderItem={(task) => (
                    <List.Item
                        actions={[
                            <Button type="link" onClick={() => handleDeleteTask(task.id)}>Delete</Button>
                        ]}
                    >
                        <List.Item.Meta
                            title={<Link to={`/tasks/${task.id}`}>{task.title}</Link>}
                        />
                    </List.Item>
                )}
            />
            <CreateTask
                isVisible={isModalVisible}
                onClose={() => setIsModalVisible(false)}
                onTaskCreated={fetchTasks}
            />
        </div>
    );
};

export default TaskList;
