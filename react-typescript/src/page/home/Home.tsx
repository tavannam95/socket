import React from 'react';
import { useSelector } from 'react-redux'
import axios from 'axios';
import { Routes, Route, Link } from 'react-router-dom'



const Home = () => {
    const count = useSelector((state: any) => state.counter.value);
    const handleLogin = () => {
        let httpPrefix = 'http://localhost:3000';
        let domain = window.location.origin;
        // console.log(domain);
        window.location.href = httpPrefix + "/login?redirect_uri=" + domain || ""
        console.log(httpPrefix + "/login?redirect_uri=" + domain || "");

    }
    return (
        <div>
            <div>
                <span className='h1'>Home page</span>
            </div>
            <span className="h3">Redux value: {count}</span>
            <div>
                <button onClick={handleLogin}>Login</button>
            </div>
        </div>
    );
};

export default Home;