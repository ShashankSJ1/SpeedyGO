import React, { useEffect } from 'react';
import '../../styles/Footer.css';
import { Link, useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { FOOTER_TEXT, FOOTER_LINKS, FOOTER_ICONS } from '../../constants';
import footericon from "../../images/speedygo.jpeg"
const Footer = () => {
  const role = useSelector((state) => state.user.role);
  const location = useLocation();

  // Scroll to top on route change
  useEffect(() => {
    window.scrollTo(0, 0);
  }, [location]);

  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-section ft-1">
          <h3 className="footer-logo">
            <i className="fa-solid fa-truck"><img src={footericon} className='footer-websiteicon'/></i> 
            <span>{FOOTER_TEXT.LOGO}</span>
          </h3>
          <p>{FOOTER_TEXT.DESCRIPTION}</p>
          <div className="footer-icons">
            {FOOTER_ICONS.FACEBOOK}
            {FOOTER_ICONS.TWITTER}
            {FOOTER_ICONS.INSTAGRAM}
            {FOOTER_ICONS.LINKEDIN}
          </div>
        </div>

        <div className="footer-section ft-2">
          <h5>{FOOTER_TEXT.QUICK_LINKS}</h5>
          <ul>
            <li>{ role === "CUSTOMER" ? <Link to={FOOTER_LINKS.LOGISTICS}>LOGISTICS</Link> : <Link to={FOOTER_LINKS.QUOTATIONS}>QUOTATIONS</Link>}</li>
            <li><Link to={FOOTER_LINKS.CONTACT}>Contact Us</Link></li>
            <li><Link to={FOOTER_LINKS.ABOUT}>About Us</Link></li>
            <li><Link to={FOOTER_LINKS.DASHBOARD}>Dashboard</Link></li>
          </ul>
        </div>

        <div className="footer-section ft-3">
          <h5>{FOOTER_TEXT.CONTACT_US}</h5>
          <p><i className="fa-solid fa-phone-volume"></i> {FOOTER_TEXT.PHONE}</p>
          <p><i className="fa-solid fa-envelope"></i> {FOOTER_TEXT.EMAIL}</p>
          <p><i className="fa-solid fa-paper-plane"></i> {FOOTER_TEXT.ADDRESS}</p>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
