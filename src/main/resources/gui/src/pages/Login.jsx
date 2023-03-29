import { useState} from 'react'
import { useNavigate } from 'react-router-dom';
import { doLogin } from '../service/login';
export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate()
  const handleLogin = async () => {
    const loginSuccess = await doLogin({'loginId':username, password})
    if(loginSuccess) {
        navigate("/")
    }
  }

  return (
    <div>
      <h1>Login Page</h1>
      <form >
        <div>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="button" onClick={handleLogin}>Log in</button>
      </form>
    </div>
  );
}
