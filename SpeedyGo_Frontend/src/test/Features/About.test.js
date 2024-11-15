import React from 'react';
import { render, screen } from '@testing-library/react';
import About from '../../pages/About.jsx';
import { aboutset, topTexts, commitmentText, belowTitles, bottomBoldText, bottomServicesText } from '../../constants.js';

describe('About Component', () => {
    test('renders without crashing', () => {
        render(<About />);
    });

    test('renders top texts correctly', () => {
        render(<About />);
        topTexts.forEach(text => {
            expect(screen.getByText(text)).toBeInTheDocument();
        });
    });

    test('renders commitment text correctly', () => {
        render(<About />);
        expect(screen.getByText(commitmentText)).toBeInTheDocument();
    });

    test('renders below titles correctly', () => {
        render(<About />);
        belowTitles.forEach(title => {
            expect(screen.getByText(title)).toBeInTheDocument();
        });
    });

    test('renders bottom bold text correctly', () => {
        render(<About />);
        expect(screen.getByText(bottomBoldText)).toBeInTheDocument();
    });

    test('renders bottom services text correctly', () => {
        render(<About />);
        expect(screen.getByText(bottomServicesText)).toBeInTheDocument();
    });

    test('renders aboutset content correctly', () => {
        render(<About />);
        aboutset.forEach(([icon, title, description]) => {
            expect(screen.getByText(title)).toBeInTheDocument();
            expect(screen.getByText(description)).toBeInTheDocument();
        });
    });

    test('applies AOS attributes correctly', () => {
        render(<About />);
        const elementsWithAos = document.querySelectorAll('[data-aos]');
        elementsWithAos.forEach(element => {
            expect(element).toHaveAttribute('data-aos', 'fade-up');
        });
    });
    
});
