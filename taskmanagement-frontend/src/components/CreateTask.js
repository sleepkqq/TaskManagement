import React, { useState, useEffect } from 'react';
import { Form, Input, Select, DatePicker, Button, Modal, notification } from 'antd';
import { createTask } from '../api/tasks';
import { getAllUsers } from '../api/users';

const { Option } = Select;

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

const CreateTask = ({ isVisible, onClose, onTaskCreated }) => {
    const [form] = Form.useForm();
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await getAllUsers();
                setUsers(response.data);
            } catch (err) {
                notification.error({ message: 'Failed to fetch users' });
            }
        };
        fetchUsers();
    }, []);

    const handleCreateTask = async (values) => {
        try {
            const formattedValues = {
                ...values,
                dueDate: values.dueDate ? values.dueDate.format('YYYY-MM-DDTHH:mm:ss') : null,
            };

            await createTask(formattedValues);
            notification.success({
                message: 'Success',
                description: 'Task successfully created',
            });
            onTaskCreated();
            onClose();
            form.resetFields();
        } catch (err) {
            notification.error({ message: 'Failed to create task' });
        }
    };

    return (
        <Modal
            title="Create Task"
            visible={isVisible}
            onCancel={onClose}
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
                    rules={[{ required: true, message: 'Please select the due date!' }]}
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
    );
};

export default CreateTask;
