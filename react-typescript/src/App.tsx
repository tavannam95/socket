import React, { useEffect, useState } from 'react';
import './App.css';
import Home from './page/home/Home';
import { Routes, Route, Link, redirect } from 'react-router-dom'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import MyfuncPage from './page/func-component/MyfuncPage';
import MyReduxPage from './page/redux-component/MyReduxPage';
import MyClassPage from './page/class-component/MyClassPage';
import Login from './page/auth/login/Login';
import Sockets from './page/socket/Sockets';

function App() {
  return (
    <div className="App">
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <Link className="navbar-brand" to="">Navbar</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link active" aria-current="page" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/class">Class</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/func">Function</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/redux" aria-disabled="true">Redux</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/login" aria-disabled="true">Login</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/sockets" aria-disabled="true">Sockets</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <Routes>
        <Route path='/' element={<Home />}></Route>
        <Route path='/class' element={<MyClassPage name={'Class page'} />}></Route>
        <Route path='/func' element={<MyfuncPage />}></Route>
        <Route path='/redux' element={<MyReduxPage />}></Route>
        <Route path='/login' element={<Login />}></Route>
        <Route path='/sockets' element={<Sockets />}></Route>
      </Routes>
    </div>
  );
}

export default App;
