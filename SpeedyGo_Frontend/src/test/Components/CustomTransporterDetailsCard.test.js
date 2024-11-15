import React from 'react';
import { render, screen } from '@testing-library/react';
import CustomTransporterDetailsCard from '../../Components/CustomTransporterDetailsCard';// Adjust the path according to your project structure
import CustomButton from '../../Components/CustomComponents/CustomButton';// Correct path to CustomButton component

// Mock data for props
const mockProps = {
  imgsrc: 'test-image.jpg',
  vehicleType: 'Truck',
  vehicleName: 'Heavy Duty Truck',
  btntitle: 'Learn More',
  loadCapacity: '5000kg',
  driverName: 'John Doe'
};

// Mock CustomButton component
jest.mock('../../Components/CustomComponents/CustomButton', () => (props) => (
  <button>{props.buttonTitle}</button>
));

describe('CustomTransporterDetailsCard', () => {
  test('renders without crashing', () => {
    render(<CustomTransporterDetailsCard {...mockProps} />);
  });

  test('displays image, title, and vehicle type correctly', () => {
    render(<CustomTransporterDetailsCard {...mockProps} />);

    expect(screen.getByAltText('Vehicle')).toHaveAttribute('src', 'test-image.jpg');
    expect(screen.getByText('Heavy Duty Truck')).toBeInTheDocument();
    expect(screen.getByText('Truck')).toBeInTheDocument();
  });

  test('displays dynamic properties correctly', () => {
    render(<CustomTransporterDetailsCard {...mockProps} />);

    expect(screen.getByText('loadCapacity:')).toBeInTheDocument();
    expect(screen.getByText('5000kg')).toBeInTheDocument();
    expect(screen.getByText('driverName:')).toBeInTheDocument();
    expect(screen.getByText('John Doe')).toBeInTheDocument();
  });

  test('renders CustomButton with correct title', () => {
    render(<CustomTransporterDetailsCard {...mockProps} />);

    expect(screen.getByText('Learn More')).toBeInTheDocument();
  });
});
