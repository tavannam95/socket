import React, { useEffect, useState } from 'react';

const MyfuncPage = () => {
    const [count, setCount] = useState(0);

    // useEffect với mảng dependency rỗng sẽ chỉ chạy một lần sau khi component được render
    useEffect(() => {
        console.log('Component được render');

        // Hàm cleanup sẽ được gọi khi component bị unmount hoặc khi dependency thay đổi
        return () => {
            console.log('Component bị unmount');
        };
    }, []);

    // useEffect với dependency là một biến sẽ chỉ chạy khi biến đó thay đổi
    useEffect(() => {
        console.log('Giá trị count đã thay đổi: ' + count);
    }, [count]);

    const handleClick = () => {
        setCount(count + 1);
    };

    return (
        <div>
            <span className='h1'>Function page</span>
            <p>Giá trị count: {count}</p>
            <button onClick={handleClick}>Tăng giá trị</button>
        </div>
    );
};

export default MyfuncPage;