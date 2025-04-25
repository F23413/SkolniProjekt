import React, {useState} from "react";
import FilmSearch from "../common/FilmSearch";

const HomePage =()=>{
    const [filmSearchResults, setFilmSearchResults] = useState([]);
const handleSearchResult = (vysledky)=>{
    setFilmSearchResults(vysledky);
}

    return(
        <div className="home">
            {/* Záhlaví a banner*/}
            <section>
                <header className="header-banner">
                    <img src="./assets/obrazky/YdFsXZgsagfMECEWk7qRTQ.jpg" alt="uvodni obrazek" className="header-image"/>
                    <div className="overlay"></div>
                    <div className="animace-textu overlay-content">
                        <h1>
                            Vítejte na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span>
                        </h1><br></br>
                        <h3>Kde půjčování je snadné jako Java.</h3>
                    </div>
                </header>
            </section>
            {/* Hledání filmů k půjčení */}
            <FilmSearch handleSearchResult={handleSearchResult}/>

            <h4><a className="view-filmy-home" href="/filmy">Všechny filmy</a></h4>
            <h2 className="home-services">Služby na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span></h2>
            {/* Služby */}
            <section className="service-section">
                <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 1"/>
                <div className="service-details">
                    <h3 className="service-title">Jedna věc</h3>
                    <p className="service-description">Popis věci číslo jedna</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 2"/>
                <div className="service-details">
                    <h3 className="service-title">Druhá věc</h3>
                    <p className="service-description">Popis věci číslo dva</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 3"/>
                <div className="service-details">
                    <h3 className="service-title">Třetí věc</h3>
                    <p className="service-description">Popis věci číslo tři</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 4"/>
                <div className="service-details">
                    <h3 className="service-title">Čtvrtá věc</h3>
                    <p className="service-description">Popis věci číslo čtyři</p>
                </div>
            </div>

            </section>
        </div>
    )
}
export default HomePage;