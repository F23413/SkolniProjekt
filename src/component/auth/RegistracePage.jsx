import React, { useState } from 'react';
import ApiService from '../../service/ApiService';
import { useNavigate } from 'react-router-dom';

function RegistracePage() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        jmeno: '',
        email: '',
        heslo: '',
        telCislo: ''
    });

    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const validateForm = () => {
        const { jmeno, email, heslo, telCislo } = formData;
        if (!jmeno || !email || !heslo || !telCislo) {
            return false;
        }
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) {
            setErrorMessage('Vyplňte prosím všechna pole.');
            setTimeout(() => setErrorMessage(''), 5000);
            return;
        }
        try {
            // Call the register method from ApiService
            const response = await ApiService.registrUzivatel(formData);

            // Check if the response is successful
            if (response.kodStavu === 200) {
                // Clear the form fields after successful registration
                setFormData({
                    jmeno: '',
                    email: '',
                    heslo: '',
                    telCislo: ''
                });
                setSuccessMessage('Registrace proběhla úspěšně');
                setTimeout(() => {
                    setSuccessMessage('');
                    navigate('/');
                }, 3000);
            }
        }
         catch (error) {
            setErrorMessage(error.response?.data?.message || error.message);
            setTimeout(() => setErrorMessage(''), 5000);
        }
    };

    return (
        <div className="auth-container">
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        {successMessage && <p className="success-message">{successMessage}</p>}
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Jméno:</label>
                    <input type="text" name="jmeno" value={formData.jmeno} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Telefonní číslo:</label>
                    <input type="text" name="telCislo" value={formData.telCislo} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Heslo:</label>
                    <input type="password" name="heslo" value={formData.heslo} onChange={handleInputChange} required />
                </div>
                <button type="submit">Zaregistrovat se</button>
            </form>
            <p className="register-link">
                Máte již účet? <a href="/login">Login</a>
            </p>
        </div>
    );
}

export default RegistracePage;