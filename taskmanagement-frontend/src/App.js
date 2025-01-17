import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AppLayout from './components/Layout';
import Home from './pages/Home';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import TaskDetail from './pages/TaskDetail';
import UserTasks from './pages/UserTasks';
import TaskList from './pages/TaskList';
import UserList from './pages/UserList';
import UserDetail from './pages/UserDetail';

const App = () => {
    return (
        <Router>
            <AppLayout>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/sign-in" element={<SignIn />} />
                    <Route path="/sign-up" element={<SignUp />} />
                    <Route path="/tasks/:id" element={<TaskDetail />} />
                    <Route path="/users/:username/tasks" element={<UserTasks />} />
                    <Route path="/tasks" element={<TaskList />} />
                    <Route path="/users" element={<UserList />} />
                    <Route path="/users/:username" element={<UserDetail />} />
                </Routes>
            </AppLayout>
        </Router>
    );
};

export default App;
