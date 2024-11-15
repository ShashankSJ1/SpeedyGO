import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CustomCard from '../../Components/CustomComponents/CustomCard';

describe('CustomCard', () => {
  const defaultProps = {
    imgsrc: 'http://example.com/image.jpg',
    btntitle: 'Learn More',
    cardheading: 'Card Title',
    description: 'This is a description.',
  };

  test('renders with required props', () => {
    render(<CustomCard {...defaultProps} />);

    const headingElement = screen.getByText(/card title/i);
    const descriptionElement = screen.getByText(/this is a description/i);
    const buttonElement = screen.getByText(/learn more/i);
    const imageElement = screen.getByAltText('img');

    expect(headingElement).toBeInTheDocument();
    expect(descriptionElement).toBeInTheDocument();
    expect(buttonElement).toBeInTheDocument();
    expect(imageElement).toHaveAttribute('src', defaultProps.imgsrc);
  });

  test('renders dynamic properties correctly', () => {
    const optionalProps = {
      location: 'New York',
      date: '2024-01-01',
    };

    render(<CustomCard {...defaultProps} {...optionalProps} />);

    expect(screen.getByText(/location:/i)).toBeInTheDocument();
    expect(screen.getByText(/new york/i)).toBeInTheDocument();
    expect(screen.getByText(/date:/i)).toBeInTheDocument();
    expect(screen.getByText(/2024-01-01/i)).toBeInTheDocument();
  });

  test('button works correctly when clicked', () => {
    const handleClick = jest.fn(); // Mock function

    render(<CustomCard {...defaultProps} onClick={handleClick} />);

    const buttonElement = screen.getByText(/learn more/i);
    fireEvent.click(buttonElement);

    expect(handleClick).toHaveBeenCalledTimes(1); // Check if the click handler was called
  });

  test('does not render functions in dynamic properties', () => {
    const optionalProps = {
      location: 'New York',
      date: () => '2024-01-01', // This is a function and should not be rendered
    };

    render(<CustomCard {...defaultProps} {...optionalProps} />);

    expect(screen.getByText(/location:/i)).toBeInTheDocument();
    expect(screen.getByText(/new york/i)).toBeInTheDocument();
    expect(screen.queryByText(/date:/i)).not.toBeInTheDocument(); // Function should not render
  });

  test('renders correctly with no optional props', () => {
    render(<CustomCard {...defaultProps} />);

    const dynamicContainer = screen.queryByText(/dynamic props/i); // Expecting it not to be present
    expect(dynamicContainer).not.toBeInTheDocument();
  });
});
