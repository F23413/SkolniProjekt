import React, {useState, useEffect} from "react";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'
import ApiService from "../../service/ApiService";

const FilmSearch = ({handleSearchResult}) =>{
    const [startDatum, setStartDatum] = useState(null);
    const [endDatum, setEndDatum] = useState(null);
    const [zanrFilmu, setZanrFilmu] = useState('');
    const [zanryFilmu, setZanryFilmu] = useState([]);
    const [error, setError] = useState('');

    useEffect(()=>{

        const fetchZanryFilmu = async () =>{
            try{
                const zanry = await ApiService.getVsechnyZanry();
    
                setZanryFilmu(zanry)
            }catch (err){
                console.log(err.message);
            }
        };
        fetchZanryFilmu();
    }, []);

    const showError = (message, timeOut = 5000) =>{
        setError(message);
        setTimeout(()=>{
            setError('');
        }, timeOut);
    };
    const handleInternalSearch = async () => {
        if (!startDatum || !endDatum || !zanrFilmu) {
          showError('Prosím vyberte všechna pole.');
          return false;
        }
        const formatDate = (date) => {
            if (!date) return null;
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // měsíce jsou 0–11
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
          };
        try {
          // Konvertuje start/endDatum do požadovaného formátu
          const formattedStartDatum = formatDate(startDatum);
          const formattedEndDatum = formatDate(endDatum);
          // Volá API na nalezení možných filmů
          const response = await ApiService.getVsechnyMozneFilmyDleDataAZanru(formattedStartDatum, formattedEndDatum, zanrFilmu);
    
          // Zkouška, je-li výsledek úspěšný
          if (response.kodStavu === 200) {
            if (response.seznamFilmu.length === 0) {
              showError('Film tohoto žánru a datového rozmezí není momentálně k mání.');
              return
            }
            handleSearchResult(response.seznamFilmu);
            setError('');
          }
        } catch (error) {
          showError("Nastala neznámá chyba: " + error.response.zprava);
          //showError("Nastala neznámá chyba." + formatDate(startDatum));
        }
      };

    return(
        <section>
            <div className="search-container">
                <div className="search-field">
                    <label>Datum půjčení</label>
                    <DatePicker
                        selected={startDatum}
                        onChange={(datum) => setStartDatum(datum)}
                        dateFormat="dd/MM/yyyy"
                        placeholderText="Vyberte datum půjčení"
                    />
                </div>
                <div className="search-field">
                    <label>Datum vrácení</label>
                    <DatePicker
                        selected={endDatum}
                        onChange={(datum) => setEndDatum(datum)}
                        dateFormat="dd/MM/yyyy"
                        placeholderText="Vyberte datum vrácení"
                    />
                </div>

                <div className="search-field">
                    <label>Zanr filmu</label>
                    <select value={zanrFilmu} onChange={(e) => setZanrFilmu(e.target.value)}>
                        <option disabled value="">
                            Vyberte žánr filmu
                        </option>
                        {zanryFilmu.map((zanrFilmu) =>(
                            <option key={zanrFilmu} value={zanrFilmu}>
                                {zanrFilmu}
                            </option>
                        ))}
                    </select>
                </div>
                <button className="home-search-button" onClick={handleInternalSearch}>
                        Vyhledat filmy
                </button>
            </div>
            {error && <p className="error-message">{error}</p>}
        </section>
    )
}
export default FilmSearch;