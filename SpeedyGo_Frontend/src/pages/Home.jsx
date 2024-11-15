import React, { useEffect, useState } from 'react';
import AOS from 'aos';
import 'aos/dist/aos.css'; // Import the AOS CSS
import '../styles/Home.css';
import feature1 from "../images/Feature1.png";
import feature2 from "../images/Feature2.svg";
import feature3 from "../images/Feature3.svg";
import service1 from "../images/Service1.svg";
import service2 from "../images/Service2.svg";
import service3 from "../images/Service3.svg";
import explorenow from "../images/Exploreimg.svg";
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { HOMEPAGE_SERVICES, HOMEPAGE_CONTENT_ARRAY, HOMEPAGE_UI_TEXT } from '../constants';

const HomePage = () => {
  const [isDarkMode, setIsDarkMode] = useState(false);
  const navigate = useNavigate();
  const role = useSelector((state) => state.user.role);

  useEffect(() => {
    AOS.init({ duration: 1000 });
  }, []);

  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  const handleExplore = () => {
    if (role === "CUSTOMER") {
      navigate("/user/logistics");
    } else {
      navigate("/transporter/profile-management");
    }
  };

  return (
    <div className={`homepage-container ${isDarkMode ? 'dark' : ''}`}>
      <div className='toggle-container'>
        <button className={`theme-toggle-button ${isDarkMode ? 'dark' : ''}`} onClick={toggleTheme}>
          {isDarkMode ? HOMEPAGE_UI_TEXT.SWITCH_TO_LIGHT_MODE : HOMEPAGE_UI_TEXT.SWITCH_TO_DARK_MODE}
        </button>
      </div>

      <div className='homepage-exploresection'>
        <div className='text-section'>
          <h1>{HOMEPAGE_UI_TEXT.WELCOME_TITLE}</h1>
          <p>{HOMEPAGE_UI_TEXT.WELCOME_DESCRIPTION}</p>
          <p>{HOMEPAGE_UI_TEXT.DISCOVER_CONTENT}</p>
          <div className='explorebutton-container'>
            <button className='explore-button' onClick={handleExplore}>{HOMEPAGE_UI_TEXT.EXPLORE_NOW_BUTTON}</button>
          </div>
        </div>
        <div className='image-section'>
          <img src={explorenow} alt='Explore' className='exploreimg' />
        </div>
      </div>
      <h2 className='text-center' data-aos='fade-up'>{HOMEPAGE_UI_TEXT.OUR_SERVICES_TITLE}</h2>
      <div className='homepage-servicesection'>
        {HOMEPAGE_SERVICES.map((item, index) => (
          <div 
            key={index} 
            className='service-card'
            data-aos='fade-up'
          >
            <h2>{item.title}</h2>
            <div className='image-section'>
              <img src={item.image === "feature1" ? feature1 : item.image === "feature2" ? feature2 : feature3} alt={item.title} className='exploreimg' />
            </div>
            <p>{item.text}</p>
          </div>
        ))}
      </div>
      <h2 className='text-center' data-aos='fade-up'>{HOMEPAGE_UI_TEXT.FEATURES_TITLE}</h2>
      <div className='even-odd-cards'>
        {HOMEPAGE_CONTENT_ARRAY.map((item, index) => (
          <div 
            key={index} 
            className={`card ${index % 2 === 0 ? 'odd' : 'even'}`}
            data-aos='fade-up'
          >
            <div className='image-section'>
              <img src={item.image === "service1" ? service1 : item.image === "service2" ? service2 : service3} alt={item.title} className='exploreimg' />
            </div>
            <div className='text-section'>
              <h2>{item.title}</h2>
              <p>{item.text}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HomePage;
