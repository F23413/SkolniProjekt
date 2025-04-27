import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const UpravaFilmuPage = () => {
    const { filmId } = useParams();
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

    useEffect(() => {
        const fetchFilmDetails = async () => {
            try {
                const odpoved = await ApiService.getFilmDleId(filmId);
                setFilmDetails({
                    obrazekFilmu: odpoved.film.obrazekFilmu,
                    zanrFilmu: odpoved.film.zanrFilmu,
                    cenaFilmu: odpoved.film.cenaFilmu,
                    popisFilmu: odpoved.film.popisFilmu,
                });
            } catch (error) {
                setError(error.odpoved?.data?.zprava || error.zprava);
            }
        };
        fetchFilmDetails();
    }, [filmId]);

    const handleChange = (e) => {
        const { jmeno, value } = e.target;
        setFilmDetails(prevState => ({
            ...prevState,
            [jmeno]: value,
        }));
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


    const handleUpdate = async () => {
        try {
            const formData = new FormData();
            formData.append('zanrFilmu', filmDetails.zanrFilmu);
            formData.append('cenaFilmu', filmDetails.cenaFilmu);
            formData.append('popisFilmu', filmDetails.popisFilmu);

            if (file) {
                formData.append('photo', file);
            }

            const result = await ApiService.updateFilm(filmId, formData);
            if (result.statusCode === 200) {
                setSuccess('film updated successfully.');
                
                setTimeout(() => {
                    setSuccess('');
                    navigate('/admin/manage-films');
                }, 3000);
            }
            setTimeout(() => setSuccess(''), 5000);
        } catch (error) {
            setError(error.odpoved?.data?.zprava || error.zprava);
            setTimeout(() => setError(''), 5000);
        }
    };

    const handleDelete = async () => {
        if (window.confirm('Chcete odstranit tento film?')) {
            try {
                const result = await ApiService.odstranFilmDleId(filmId);
                if (result.statusCode === 200) {
                    setSuccess('Film úspěšně odstraněn.');
                    
                    setTimeout(() => {
                        setSuccess('');
                        navigate('/admin/manage-films');
                    }, 3000);
                }
            } catch (error) {
                setError(error.odpoved?.data?.zprava || error.zprava);
                setTimeout(() => setError(''), 5000);
            }
        }
    };

    return (
        <div className="edit-film-container">
            <h2>Upravit film</h2>
            {error && <p className="error-zprava">{error}</p>}
            {success && <p className="success-zprava">{success}</p>}
            <div className="edit-film-form">
                <div className="form-group">
                    {preview ? (
                        <img src={preview} alt="film Preview" className="film-photo-preview" />
                    ) : (
                        filmDetails.obrazekFilmu && (
                            <img src={filmDetails.obrazekFilmu} alt="film" className="film-photo" />
                        )
                    )}
                    <input
                        type="file"
                        name="foto"
                        onChange={handleFileChange}
                    />
                </div>
                <div className="form-group">
                    <label>film Type</label>
                    <input
                        type="text"
                        name="zanrFilmu"
                        value={filmDetails.zanrFilmu}
                        onChange={handleChange}
                    />
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
                <button className="update-button" onClick={handleUpdate}>Upravit film</button>
                <button className="delete-button" onClick={handleDelete}>Odstranit film</button>
            </div>
        </div>
    );
};

export default UpravaFilmuPage;