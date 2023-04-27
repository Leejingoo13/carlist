import React, { useState } from "react";
import { SERVER_URL } from "../constants"; // 로그인 요청에 필요한 SERVER_URL 도 가져와야함 
import Button from "@mui/material/Button"; //로그인 함수를 호출하기위한 Button 컴포넌트
import TextField from "@mui/material/TextField"; // 자격 증명을 위한 TextField 컴포넌트
import Stack from "@mui/material/Stack"; //레이아웃을 위한 Stack컴포넌트
import Carlist from "./Carlist";
import Snackbar from "@mui/material/Snackbar";

function Login() {
    // 인증에 필요한 상태값은 세개
    // 자격증명에 두개 (사용자 이름과 암호) 가 필요하고 상태를 나나태는 부울값 하나가 필요함
    // 인증 상태의 초기값은 false
    const [user, setUser] = useState({
        username: '',
        password: ''
    });
    
    const [isAuthenticated, setAuth] = useState(false);
    const [open , setOpen] = useState(false);

    
    // 입력 값을 상태에 저장하기위해 TextField 변경 처리기 함수를 구현함
    const handleChange = (event) => {
        setUser({...user, [event.target.name] : event.target.value});
    }

    // 로그인을 위해서는 본문에 사용자 객체를 포함하고 POST 방식으로 /login 엔드포인트로 호출해야함
    // 인증이 성공하면 응답 isAuthenticated 상태를 true 로 설정함
    // 세션 저장소는 로컬 저장소와 비슷하지만 페이지 세션이 끝나면 삭제됨
    // isAuthenticated 상태 값이 변경되면 사용자 인터페이스가 다시 렌더링 됨
    const login = () => {
        fetch(SERVER_URL + 'login', {
            method: 'POST',
            headers: { 'Content-Type':'application/json' },
            body: JSON.stringify(user) 
        })
        .then(res => {
            const jwtToken = res.headers.get('Authorization');
            if (jwtToken !== null) {
                sessionStorage.setItem("jwt", jwtToken);
                setAuth(true);
            }
            else {
                setOpen(true);
            }
        })
        .catch(err => console.error(err))
    }

    // 조건부 렌더링을 구현할 경우 isAuthenticated 상태가 false 이면 Login 컴포넌트를 렌더링하고
    // true 이면 Carlist 컴포넌트를 렌더링 할 수 있음
    if (isAuthenticated) {
        return <Carlist />;
    }
    else{
        return(
            <div>
            <Stack spacing={2} alignItems='center' mt={2}>
            <TextField 
                name="username"
                label="Username" 
                onChange={handleChange} />
            <TextField 
                type="password"
                name="password"
                label="Password"
                onChange={handleChange}/>
            <Button 
                variant="outlined" 
                color="primary" 
                onClick={login}>
                Login
            </Button>
            </Stack>
            <Snackbar
                open={open}
                autoHideDuration={3000}
                onClose={() => setOpen(false)}
                message="Login failed: Check your username and password"
                />
            </div>
        );
    }
}

export default Login;