import React, {useState} from "react";
import FilmSearch from "../common/FilmSearch";
import FilmResult from "../common/FilmResult";


const HomePage =()=>{
    const [filmSearchResults, setFilmSearchResults] = useState([]);

const handleSearchResult = (vysledky)=>{
    setFilmSearchResults(vysledky);
};

    return(
        <div className="home">
            {/* Záhlaví a banner*/}
            <section>
                <header className="header-banner">
                    <img src="./assets/obrazky/YdFsXZgsagfMECEWk7qRTQ.jpg" alt="uvodni obrazek" className="header-image"/>
                    <div className="overlay"></div>
                    <div className="animated-texts overlay-content">
                        <h1>
                            Vítejte na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span>
                        </h1><br/>
                        <h3>Kde půjčování je snadné jako Java.</h3>
                    </div>
                </header>
            </section>
            {/* Hledání filmů k půjčení */}
            <FilmSearch handleSearchResult={handleSearchResult}/>
            <FilmResult filmSearchResults={filmSearchResults}/>

            <h4><a className="view-filmy-home" href="/filmy">Všechny filmy</a></h4>
            <h2 className="home-services">Služby na stránkách serveru <span className="kvadrazic-barva">Kvadrazič</span></h2>
            {/* Služby */}
            <section className="service-section">
                <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 1"/>
                <div className="service-details">
                    <h3 className="service-title">Široká nabídka filmů</h3>
                    <p className="service-description">Novinky, klasiky, různé žánry (komedie, horory, sci-fi, ...)</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 2"/>
                <div className="service-details">
                    <h3 className="service-title">Snadné půjčení a vrácení</h3>
                    <p className="service-description">Jasné podmínky, jednoduchý proces půjčení i vrácení</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 3"/>
                <div className="service-details">
                    <h3 className="service-title">Půjčovné za rozumnou cenu</h3>
                    <p className="service-description">Možnost slev, předplatného nebo balíčků</p>
                </div>
            </div>
            <div className="service-card">
                <img src=".\assets\obrazky\kosmonaut.png" alt="obr 4"/>
                <div className="service-details">
                    <h3 className="service-title">Zákaznická podpora</h3>
                    <p className="service-description">Rychlá pomoc, kdyby něco nefungovalo</p>
                </div>
            </div>

            </section>
        </div>
    )
}
export default HomePage;