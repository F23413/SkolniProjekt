import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';


const PridatFilmPage = () => {
    const navigate = useNavigate();
    const [filmDetails, setFilmDetails] = useState({
        obrazekFilmu: '',
        zanrFilmu: '',
        cenaFilmu: '',
        popisFilmu: '',
    });
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [zanryFilmu, setZanryFilmu] = useState([]);
    const [newZanrFilmu, setNewZanrFilmu] = useState(false);


    useEffect(() => {
        const fetchZanryFilmu = async () => {
            try {
                const types = await ApiService.getVsechnyZanry();
                setZanryFilmu(types);
            } catch (error) {
                console.error('Error fetching film types:', error.message);
            }
        };
        fetchZanryFilmu();
    }, []);



    const handleChange = (e) => {
        const { jmeno, value } = e.target;
        setFilmDetails(prevState => ({
            ...prevState,
            [jmeno]: value,
        }));
    };


    const handleZanrFilmuChange = (e) => {
        if (e.target.value === 'new') {
            setNewZanrFilmu(true);
            setFilmDetails(prevState => ({ ...prevState, zanrFilmu: '' }));
        } else {
            setNewZanrFilmu(false);
            setFilmDetails(prevState => ({ ...prevState, zanrFilmu: e.target.value }));
        }
    };


    const handleFileChange = (e) => {
        const vybranySoubor = e.target.files[0];
        if (vybranySoubor) {
            setFile(vybranySoubor);
            setPreview(URL.createObjectURL(vybranySoubor));
        } else {
            setFile(null);
            setPreview(null);
        }
    };


    const addfilm = async () => {
        if (!filmDetails.zanrFilmu || !filmDetails.cenaFilmu || !filmDetails.popisFilmu) {
            setError('Musí být uvedeny všechny podrobnosti o filmu.');
            setTimeout(() => setError(''), 5000);
            return;
        }

        if (!window.confirm('Chcete přidat tento film?')) {
            return
        }

        try {
            const formData = new FormData();
            formData.append('zanrFilmu', filmDetails.zanrFilmu);
            formData.append('cenaFilmu', filmDetails.cenaFilmu);
            formData.append('popisFilmu', filmDetails.popisFilmu);

            if (file) {
                formData.append('foto', file);
            }

            const result = await ApiService.pridejNovyFilm(formData);
            if (result.statusCode === 200) {
                setSuccess('film Added successfully.');
                
                setTimeout(() => {
                    setSuccess('');
                    navigate('/admin/manage-films');
                }, 3000);
            }
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className="edit-film-container">
            <h2>Přidej nový film</h2>
            {error && <p className="error-message">{error}</p>}
            {success && <p className="success-message">{success}</p>}
            <div className="edit-film-form">
                <div className="form-group">
                    {preview && (
                        <img src={preview} alt="film Preview" className="film-photo-preview" />
                    )}
                    <input
                        type="file"
                        name="obrazekFilmu"
                        onChange={handleFileChange}
                    />
                </div>

                <div className="form-group">
                    <label>film Type</label>
                    <select value={filmDetails.zanrFilmu} onChange={handleZanrFilmuChange}>
                        <option value="">Vyberte žánr filmu</option>
                        {zanryFilmu.map(type => (
                            <option key={type} value={type}>{type}</option>
                        ))}
                        <option value="new">Jiné (specifikujte)</option>
                    </select>
                    {newZanrFilmu && (
                        <input
                            type="text"
                            name="ZanrFilmu"
                            placeholder="Enter new film type"
                            value={filmDetails.zanrFilmu}
                            onChange={handleChange}
                        />
                    )}
                </div>
                <div className="form-group">
                    <label>film Price</label>
                    <input
                        type="text"
                        name="cenaFilmu"
                        value={filmDetails.cenaFilmu}
                        onChange={handleChange}
                    />
                </div>
                <div className="form-group">
                    <label>film Description</label>
                    <textarea
                        name="popisFilmu"
                        value={filmDetails.popisFilmu}
                        onChange={handleChange}
                    ></textarea>
                </div>
                <button className="update-button" onClick={addfilm}>Přidat film</button>
            </div>
        </div>
    );
};

export default PridatFilmPage;