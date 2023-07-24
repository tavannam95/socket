import React, { useEffect } from 'react'
import { SocketService } from '../../service/SocketService'

export default function Sockets() {
    useEffect(()=>{
        SocketService.getInstance().initSocket();
        SocketService.getInstance().socketClient.connect({username: 'namtv'}, ()=>{
            console.log('Connect started');
            
        },
            (e: any) =>{
                console.log(e);
                
            }
        )
    },[])
  return (
    <>
        <label>Name</label><br />
        <input type="text" /><br />
        <button className='btn btn-primary mt-3'>Submit</button>
    </>
  )
}
