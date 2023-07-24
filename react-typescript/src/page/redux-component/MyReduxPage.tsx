import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { decrement, increment } from '../../reducers/counterSlice';

const MyReduxPage = () => {
    const count = useSelector((state: any) => state.counter.value)

    const dispatch = useDispatch()
    return (
        <div className='mt-3'>
            <span className='h1'>Redux page</span>
            <div className='mt-3'>
                <button
                    aria-label="Decrement value"
                    onClick={() => dispatch(decrement())}
                >
                    Giảm
                </button>
                <span> {count} </span>
                <button
                    aria-label="Increment value"
                    onClick={() => dispatch(increment())}
                >
                    Tăng
                </button>

            </div>
        </div>
    );
};

export default MyReduxPage;