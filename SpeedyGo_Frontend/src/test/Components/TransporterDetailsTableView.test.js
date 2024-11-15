import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import TransporterDetailsTableView from '../../Components/TransporterDetailsTableView'; // Adjust the path according to your project structure
import CustomButton from '../../Components/CustomComponents/CustomButton'; // Adjust the path according to your project structure
import { TRANSPORTERDETAILSTABLE_HEADERS, TRANSPORTERDETAILSTABLE_BUTTON_TITLES, TRANSPORTERDETAILSTABLE_MESSAGES} from '../../contansts'; // Adjust the path according to your project structure

// Mock data
const mockTransporterData = [
  {
    vehicleInfo: {
      vehicleName: 'Truck 1',
      vehicleType: 'Type A',
      basePrice: 1000,
      pricePerKilometer: 10,
      totalPrice: 5000,
      vehicleNumber: 'XYZ123'
    },
    transporterInfo: {
      email: 'transporter1@example.com'
    }
  },
  {
    vehicleInfo: null
  }
];

// Mock CustomButton component
jest.mock('../../Components/CustomComponents/CustomButton', () => (props) => (
  <button onClick={props.onClick}>{props.buttonTitle}</button>
));

describe('TransporterDetailsTableView', () => {
  test('renders without crashing', () => {
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={jest.fn()} submitRequest={jest.fn()} />);
  });

  test('displays the table headers correctly', () => {
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={jest.fn()} submitRequest={jest.fn()} />);

    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.VEHICLE_NAME)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.VEHICLE_TYPE)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.BASE_PRICE)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.PRICE_PER_KM)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.TOTAL_PRICE)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.ACTION)).toBeInTheDocument();
    expect(screen.getByText(TRANSPORTERDETAILSTABLE_HEADERS.REQUEST)).toBeInTheDocument();
  });

  test('displays transporter data correctly', () => {
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={jest.fn()} submitRequest={jest.fn()} />);

    expect(screen.getByText('Truck 1')).toBeInTheDocument();
    expect(screen.getByText('Type A')).toBeInTheDocument();
    expect(screen.getByText('₹1000')).toBeInTheDocument();
    expect(screen.getByText('₹10/km')).toBeInTheDocument();
    expect(screen.getByText('₹5000')).toBeInTheDocument();
  });

  test('displays vehicle info not available message when data is missing', () => {
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={jest.fn()} submitRequest={jest.fn()} />);

    expect(screen.getByText(TRANSPORTERDETAILSTABLE_MESSAGES.VEHICLE_INFO_NOT_AVAILABLE)).toBeInTheDocument();
  });

  test('calls openModal with correct item when details button is clicked', () => {
    const openModalMock = jest.fn();
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={openModalMock} submitRequest={jest.fn()} />);

    fireEvent.click(screen.getAllByText(TRANSPORTERDETAILSTABLE_BUTTON_TITLES.DETAILS)[0]);

    expect(openModalMock).toHaveBeenCalledWith(mockTransporterData[0]);
  });

  test('calls submitRequest with correct parameters when request button is clicked', () => {
    const submitRequestMock = jest.fn();
    render(<TransporterDetailsTableView transporterData={mockTransporterData} openModal={jest.fn()} submitRequest={submitRequestMock} />);

    fireEvent.click(screen.getAllByText(TRANSPORTERDETAILSTABLE_BUTTON_TITLES.REQUEST)[0]);

    expect(submitRequestMock).toHaveBeenCalledWith(5000, 'transporter1@example.com', 'XYZ123');
  });
});
