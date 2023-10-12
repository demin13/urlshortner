import './App.css';
import UrlShorterHomePage from "./components/urlForm";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import RedirectPage from "./components/redirectPage";
import React from "react";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<UrlShorterHomePage/>}/>
                <Route path="/:id" element={<RedirectPage />} />
            </Routes>
        </Router>
    );
}

export default App;
