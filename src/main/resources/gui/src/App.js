import {useRoutes, useNavigate} from 'react-router-dom'
import { useEffect } from 'react';
import routes from './routes';
function App() {
  const router = useRoutes(routes)
  const navigate = useNavigate()
  useEffect(() => {
    window.addEventListener('need_login', () => navigate("/login"))
    return () => {
    };
  }, [navigate]);
  return (
    <>
      {router}
    </>
  );
}

export default App;
