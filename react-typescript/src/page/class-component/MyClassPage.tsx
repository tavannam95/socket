import React, { Component } from 'react';

// Định nghĩa kiểu dữ liệu cho props
interface MyComponentProps {
    name: string;
}

// Định nghĩa kiểu dữ liệu cho state
interface MyComponentState {
    count: number;
}

class MyClassPage extends Component<MyComponentProps, MyComponentState>{
    constructor(props: MyComponentProps) {
        super(props);
        this.state = {
            count: 0,
        };
    }

    // incrementCount = () => {
    //     this.setState((prevState) => ({
    //         count: prevState.count + 1,
    //     }));
    // }

    componentDidMount() {
        console.log('Component mounted');
    }

    componentDidUpdate(prevProps: any, prevState: any) {
        console.log('Component did update');
        if (prevState.count !== this.state.count) {
            console.log('Count has changed');
        }
    }

    componentWillUnmount() {
        console.log('Component will unmount');
    }

    handleClick = () => {
        this.setState((prevState) => ({
            count: prevState.count + 1
        }));
    }

    render() {
        const { name } = this.props;
        const { count } = this.state;

        return (
            <div>
                <h1>Hello, {name}!</h1>
                <p>Count: {count}</p>
                <button onClick={this.handleClick}>Tăng</button>
            </div>
        );
    }
};

export default MyClassPage;