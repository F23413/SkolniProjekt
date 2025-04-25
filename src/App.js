import logo from './logo.svg';
import './App.css';
import HomePage from './component/common/Navbar.jsx'
import Footer from './component/common/Footer.jsx';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Welcome to the <code>library</code> of your mind.
        </p>
        <a href='/App.js'
        >
          My name is Mr. Owl.
          <Footer/>
        </a>
      </header>
    </div>
  );
}

export default App;
