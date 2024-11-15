import React, { useEffect } from "react";
import Aos from "aos";
import "aos/dist/aos.css";
import "../styles/About.css";
import { aboutset, topTexts, commitmentText, belowTitles, bottomBoldText, bottomServicesText } from '../constants';

function About() {
  useEffect(() => {
    Aos.init({ duration: 2000 });
  }, []);

  return (
    <div className="aboutcontainer">
      <div className="text-div">
        {topTexts.map((text, index) => (
          <h1 key={index} className="top-text">{text}</h1>
        ))}
      </div>
      <div className="gradient-content">
        <h1 data-aos="fade-up" className="gradient-text">{commitmentText}</h1>
        <div data-aos="fade-up" className="below-texts">
          <ul className="values">
            {belowTitles.map((title, index) => (
              <li  data-aos="fade-up" key={index} className="below-title">{title}</li>
            ))}
          </ul>
        </div>
      </div>
      <div className="bottom-container">
        <div className="bottom-text">
          <h2 data-aos="fade-up" className="bold-text">{bottomBoldText}</h2>
          <p data-aos="fade-up" className="bold-bottom">{bottomServicesText}</p>
        </div>
      </div>

      <div className="our-contents">
        {aboutset.map((about, index) => (
          <div key={index} className="about-content" data-aos="fade-up">
            <div className="about-icon">{about[0]}</div>
            <h3 className="about-title">{about[1]}</h3>
            <p className="about-description">{about[2]}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default About;
