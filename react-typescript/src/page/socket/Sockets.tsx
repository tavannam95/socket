import React, { useEffect, useState } from 'react'
import { SocketService } from '../../service/SocketService'
import { ChatService } from '../../service/ChatService';

export default function Sockets() {

    const [content, setContent] = useState('');

    useEffect(() => {
        SocketService.getInstance().initSocket();
        SocketService.getInstance().socketClient.connect({ username: new Date().getTime() }, () => {

            SocketService.getInstance().socketClient.subscribe(
                '/chat/topic/messages',
                (msg: any) => {
                    console.log(JSON.parse(msg.body));
                    
                },
                (err: any) => {
                    console.log(err);
                }
            );
        },
            (e: any) => {
                console.log(e);

            }
        )
    }, [])

    const handleChange = (event: any) => {
        setContent(event.target.value)
    }

    const send = () => {
        ChatService.getInstance().send(content).then((res) => {
            console.log(res);

        })
            .catch(e => {
                console.log(e);

            });
    }

    return (
        <>
            <label>Name</label><br />
            <input type="text" name='content' onChange={handleChange} /><br />
            <button className='btn btn-primary mt-3' onClick={send}>Submit</button>
        </>
    )
}
