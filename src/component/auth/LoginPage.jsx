import React, { useState } from "react";
import { useNavigate,useLocation } from "react-router-dom";
import ApiService from "../../service/ApiService";

function LoginPage() {
    const [email, setEmail] = useState('');
    const [heslo, setHeslo] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

  const from = location.state?.from?.pathname || '/home';


    const handlePotvrzeni = async (e) => {
        e.preventDefault();

        if (!email || !heslo) {
            setError('Vyplňte prosím všechna pole');
            setTimeout(() => setError(''), 5000);
            return;
        }

        try {
            const odpoved = await ApiService.loginUzivatel({email, heslo});
            if (odpoved.kodStavu === 200) {
                localStorage.setItem('token', odpoved.token);
                localStorage.setItem('role', odpoved.role);
                navigate(from, { replace: true });
            }
        } catch (error) {
            setError(error.odpoved?.data?.zprava || error.zprava);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className="auth-container">
            <h2>Login</h2>
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handlePotvrzeni}>
                <div className="form-group">
                    <label>Email: </label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Heslo: </label>
                    <input
                        type="password"
                        value={heslo}
                        onChange={(e) => setHeslo(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>

            <p className="register-link">
                Nemáte účet? <a href="/registrace">Zaregistrujte se</a>
            </p>
        </div>
    );
}

export default LoginPage;