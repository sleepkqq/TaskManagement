import React, { useState } from 'react';
import { Form, Input, Button, Select, DatePicker } from 'antd';
import { createTask } from '../api/tasks';
import { useNavigate } from 'react-router-dom';

const { Option } = Select;

const TaskForm = () => {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const onFinish = async (values) => {
        setLoading(true);
        try {
            await createTask(values);
            navigate('/tasks');
        } catch (error) {
            console.error('Failed to create task:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Form layout="vertical" onFinish={onFinish}>
            <Form.Item name="title" label="Title" rules={[{ required: true, message: 'Please input the title!' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="description" label="Description" rules={[{ required: true, message: 'Please input the description!' }]}>
                <Input.TextArea />
            </Form.Item>
            <Form.Item name="status" label="Status" rules={[{ required: true, message: 'Please select the status!' }]}>
                <Select>
                    <Option value="TO_DO">TO_DO</Option>
                    <Option value="IN_PROGRESS">IN_PROGRESS</Option>
                    <Option value="DONE">DONE</Option>
                </Select>
            </Form.Item>
            <Form.Item name="priority" label="Priority" rules={[{ required: true, message: 'Please select the priority!' }]}>
                <Select>
                    <Option value="LOW">LOW</Option>
                    <Option value="MEDIUM">MEDIUM</Option>
                    <Option value="HIGH">HIGH</Option>
                </Select>
            </Form.Item>
            <Form.Item name="dueDate" label="Due Date" rules={[{ required: true, message: 'Please select the due date!' }]}>
                <DatePicker showTime />
            </Form.Item>
            <Form.Item name="assignee" label="Assignee" rules={[{ required: true, message: 'Please input the assignee!' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="reporter" label="Reporter" rules={[{ required: true, message: 'Please input the reporter!' }]}>
                <Input />
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading}>
                    Create Task
                </Button>
            </Form.Item>
        </Form>
    );
};

export default TaskForm;
