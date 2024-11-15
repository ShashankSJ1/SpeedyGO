import React, { useState } from 'react';

import { Link, useLocation } from 'react-router-dom';

import { FaBars } from 'react-icons/fa';

import '../../styles/Header.css';

import { useSelector } from 'react-redux';

import speedygoIcon from '../../images/speedygo_home.jpeg';

import speedygoIcon1 from '../../images/speedygo.jpeg';

import { HEADER_ROUTES, HEADER_TEXT } from '../../constants.js';

function Header() {

    const role = useSelector((state) => state.user.role);

    const username = useSelector((state) => state.user.name);

    const email = useSelector((state) => state.user.email);

    const location = useLocation();

    const [menuOpen, setMenuOpen] = useState(false);

    const [hover, setHover] = useState(false);

    const isHomeRoute = location.pathname === HEADER_ROUTES.USER_HOME || location.pathname === HEADER_ROUTES.TRANSPORTER_HOME;

    // Close the menu when a link is clicked

    const handleLinkClick = () => {

        setMenuOpen(false);

    };

    return (
        <nav className={`navbar ${isHomeRoute ? 'navbar-home' : 'navbar-other'}`}>
            <Link className={`navbar-brand ${isHomeRoute ? 'text-white' : 'text-black'}`} to={role === "CUSTOMER" ? HEADER_ROUTES.USER_HOME : HEADER_ROUTES.TRANSPORTER_HOME}>
                <img src={isHomeRoute?speedygoIcon:speedygoIcon1} alt="Speedy Go Icon" className={isHomeRoute?"navbar-iconhome":"navbar-icon"} />

                {HEADER_TEXT.SPEEDY_GO}
            </Link>

            {/* User Icon + Menu for Mobile */}
            <div className="user-icon-mobile">
                <div

                    className="user-icon-wrapper"

                    onMouseEnter={() => setHover(true)}

                    onMouseLeave={() => setHover(false)}
                >
                    <div className="user-icon">

                        {username ? username.charAt(0).toUpperCase() : 'U'}
                    </div>

                    {hover && username && email && (
                        <div className="user-info">
                            <p>{username}</p>
                            <p>{email}</p>
                        </div>

                    )}
                </div>
                <div className="menu" onClick={() => setMenuOpen(!menuOpen)}>
                    <FaBars size={30} />
                </div>
            </div>

            <ul className={`navbar-menu ${menuOpen ? 'menu-open' : ''}`}>

                {role === "CUSTOMER" && (
                    <>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.USER_HOME} onClick={handleLinkClick}>

                                {HEADER_TEXT.HOME}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.USER_LOGISTICS} onClick={handleLinkClick}>

                                {HEADER_TEXT.LOGISTICS}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.USER_ABOUT} onClick={handleLinkClick}>

                                {HEADER_TEXT.ABOUT_US}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.USER_CONTACT} onClick={handleLinkClick}>

                                {HEADER_TEXT.CONTACT}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.USER_DASHBOARD} onClick={handleLinkClick}>

                                {HEADER_TEXT.DASHBOARD}
                            </Link>
                        </li>
                        <li className="nav-item user-icon-wrapper desktop-only">
                            <div

                                className="user-icon-wrapper"

                                onMouseEnter={() => setHover(true)}

                                onMouseLeave={() => setHover(false)}
                            >
                                <div className="user-icon">

                                    {username ? username.charAt(0).toUpperCase() : 'C'}
                                </div>

                                {hover && username && email && (
                                    <div className="user-info">
                                        <p>{username}</p>
                                        <p>{email}</p>
                                    </div>

                                )}
                            </div>
                        </li>
                    </>

                )}

                {role === "TRANSPORTER" && (
                    <>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.TRANSPORTER_HOME} onClick={handleLinkClick}>

                                {HEADER_TEXT.HOME}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.TRANSPORTER_PROFILE_MANAGEMENT} onClick={handleLinkClick}>

                                {HEADER_TEXT.QUOTATIONS}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.TRANSPORTER_ABOUT} onClick={handleLinkClick}>

                                {HEADER_TEXT.ABOUT_US}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.TRANSPORTER_CONTACT} onClick={handleLinkClick}>

                                {HEADER_TEXT.CONTACT}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className={`nav-link ${isHomeRoute ? 'text-white' : 'text-black'}`} to={HEADER_ROUTES.TRANSPORTER_DASHBOARD} onClick={handleLinkClick}>

                                {HEADER_TEXT.DASHBOARD}
                            </Link>
                        </li>
                        <li className="nav-item user-icon-wrapper desktop-only">
                            <div

                                className="user-icon-wrapper"

                                onMouseEnter={() => setHover(true)}

                                onMouseLeave={() => setHover(false)}
                            >
                                <div className="user-icon">

                                    {username ? username.charAt(0).toUpperCase() : 'T'}
                                </div>

                                {hover && username && email && (
                                    <div className="user-info">
                                        <p>{username}</p>
                                        <p>{email}</p>
                                    </div>

                                )}
                            </div>
                        </li>
                    </>

                )}
            </ul>
        </nav>

    );

}

export default Header;

 