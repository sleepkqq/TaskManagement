import React, { useEffect, useState } from 'react';
import { getAllTasks, createTask, deleteTaskById } from '../api/tasks';
import { getAllUsers } from '../api/users';
import { List, Typography, Spin, Alert, Button, Modal, Form, Input, Select, DatePicker, notification } from 'antd';
import { Link } from 'react-router-dom';

const { Title } = Typography;
const { Option } = Select;

// Определяем перечисления прямо в этом файле
const TaskStatus = {
    TO_DO: 'TO_DO',
    IN_PROGRESS: 'IN_PROGRESS',
    DONE: 'DONE',
};

const TaskPriority = {
    LOW: 'LOW',
    MEDIUM: 'MEDIUM',
    HIGH: 'HIGH',
};

const TaskList = () => {
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [form] = Form.useForm();
    const [users, setUsers] = useState([]);

    useEffect(() => {
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

        const fetchUsers = async () => {
            try {
                const response = await getAllUsers();
                setUsers(response.data);
            } catch (err) {
                setError('Failed to fetch users');
            }
        };

        fetchTasks();
        fetchUsers();
    }, []);

    const handleCreateTask = async (values) => {
        try {
            await createTask(values);
            // Fetch tasks again to update the list
            const response = await getAllTasks();
            setTasks(response.data);
            setIsModalVisible(false);
            form.resetFields();
            notification.success({
                message: 'Success',
                description: 'Task successfully created',
            });
        } catch (err) {
            setError('Failed to create task');
        }
    };

    const handleDeleteTask = (id) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this task?',
            okText: 'Yes',
            cancelText: 'No',
            onOk: async () => {
                try {
                    await deleteTaskById(id);
                    // Fetch tasks again to update the list
                    const response = await getAllTasks();
                    setTasks(response.data);
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
                            description={task.description}
                        />
                    </List.Item>
                )}
            />
            <Modal
                title="Create Task"
                visible={isModalVisible}
                onCancel={() => setIsModalVisible(false)}
                footer={null}
            >
                <Form
                    form={form}
                    onFinish={handleCreateTask}
                    layout="vertical"
                >
                    <Form.Item
                        name="title"
                        label="Title"
                        rules={[{ required: true, message: 'Please input the title!' }, { min: 5, max: 100, message: 'Title must be between 5 and 100 characters' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="description"
                        label="Description"
                        rules={[{ required: true, message: 'Please input the description!' }, { max: 500, message: 'Description length must not exceed 500 characters' }]}
                    >
                        <Input.TextArea />
                    </Form.Item>
                    <Form.Item
                        name="assignee"
                        label="Assignee"
                        rules={[{ required: true, message: 'Please select the assignee!' }, { min: 5, max: 50, message: 'Assignee username must be between 5 and 50 characters' }]}
                    >
                        <Select>
                            {users.map(user => (
                                <Option key={user.username} value={user.username}>{user.username}</Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="reporter"
                        label="Reporter"
                        rules={[{ required: true, message: 'Please select the reporter!' }, { min: 5, max: 50, message: 'Reporter username must be between 5 and 50 characters' }]}
                    >
                        <Select>
                            {users.map(user => (
                                <Option key={user.username} value={user.username}>{user.username}</Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="status"
                        label="Status"
                        rules={[{ required: true, message: 'Please select the status!' }]}
                    >
                        <Select>
                            {Object.values(TaskStatus).map(status => (
                                <Option key={status} value={status}>{status}</Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="priority"
                        label="Priority"
                        rules={[{ required: true, message: 'Please select the priority!' }]}
                    >
                        <Select>
                            {Object.values(TaskPriority).map(priority => (
                                <Option key={priority} value={priority}>{priority}</Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="dueDate"
                        label="Due Date"
                        rules={[{ required: false, message: 'Please select the due date!' }]}
                    >
                        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Create Task
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default TaskList;
