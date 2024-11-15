import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import TransporterFilter from '../../Components/TransporterFilter';// Adjust the path according to your project structure
import CustomButton from '../../Components/CustomComponents/CustomButton';// Adjust the path according to your project structure
import CustomFormInput from '../../Components/CustomComponents/CustomFormInput'; // Adjust the path according to your project structure
import { TRANSPORTERFILTER_LABELS,TRANSPORTERDASHBOARD_BUTTON_TITLES,TRANSPORTERFILTER_OPTIONS } from '../../contansts';

// Mock data
const mockFilters = {
  vehicleType: '',
  baseRate: 'lowest',
  pricePerKm: 'highest',
};

const mockTransporterData = [
  { vehicleInfo: { vehicleType: 'Type A' } },
  { vehicleInfo: { vehicleType: 'Type B' } },
];

const mockHandleFilter = jest.fn();
const mockHandleInputChange = jest.fn();
const mockHandleremoveFilter = jest.fn();
const mockSetFilters = jest.fn();
const mockSetFilterVisible = jest.fn();

describe('TransporterFilter', () => {
  beforeEach(() => {
    render(
      <TransporterFilter
        filters={mockFilters}
        setFilters={mockSetFilters}
        isFilterVisible={true}
        setFilterVisible={mockSetFilterVisible}
        handleFilter={mockHandleFilter}
        transporterData={mockTransporterData}
        handleInputChange={mockHandleInputChange}
        handleremoveFilter={mockHandleremoveFilter}
      />
    );
  });

  test('renders without crashing', () => {
    expect(screen.getByText(TRANSPORTERFILTER_LABELS.FILTERS)).toBeInTheDocument();
  });

  test('displays vehicle type options correctly', () => {
    const selectElement = screen.getByLabelText(TRANSPORTERFILTER_LABELS.VEHICLE_TYPE);
    expect(selectElement).toBeInTheDocument();
    expect(selectElement.children.length).toBe(3); // "Select" option, "Type A" and "Type B"
  });

  test('handles input change for vehicle type', () => {
    const selectElement = screen.getByLabelText(TRANSPORTERFILTER_LABELS.VEHICLE_TYPE);
    fireEvent.change(selectElement, { target: { value: 'Type A' } });

    expect(mockHandleInputChange).toHaveBeenCalled();
  });

  test('displays base rate filter options correctly', () => {
    expect(screen.getByLabelText(TRANSPORTERFILTER_LABELS.LOWEST)).toBeInTheDocument();
    expect(screen.getByLabelText(TRANSPORTERFILTER_LABELS.HIGHEST)).toBeInTheDocument();
  });

  test('handles input change for base rate filter', () => {
    const lowestRadio = screen.getByLabelText(TRANSPORTERFILTER_LABELS.LOWEST);
    fireEvent.click(lowestRadio);

    expect(mockHandleInputChange).toHaveBeenCalled();
  });

  test('displays price per km filter options correctly', () => {
    expect(screen.getByLabelText(TRANSPORTERFILTER_LABELS.PRICE_PER_KM)).toBeInTheDocument();
    expect(screen.getByLabelText(TRANSPORTERFILTER_LABELS.LOWEST)).toBeInTheDocument();
    expect(screen.getByLabelText(TRANSPORTERFILTER_LABELS.HIGHEST)).toBeInTheDocument();
  });

  test('handles input change for price per km filter', () => {
    const highestRadio = screen.getByLabelText(TRANSPORTERFILTER_LABELS.HIGHEST);
    fireEvent.click(highestRadio);

    expect(mockHandleInputChange).toHaveBeenCalled();
  });

  test('calls handleFilter when Apply Filter button is clicked', () => {
    fireEvent.click(screen.getByText(TRANSPORTERFILTER_BUTTON_TITLES.APPLY_FILTER));
    expect(mockHandleFilter).toHaveBeenCalled();
  });

  test('calls handleremoveFilter when Remove Filter button is clicked', () => {
    fireEvent.click(screen.getByText(TRANSPORTERFILTER_BUTTON_TITLES.REMOVE_FILTER));
    expect(mockHandleremoveFilter).toHaveBeenCalled();
  });

  test('calls setFilterVisible when close icon is clicked', () => {
    fireEvent.click(screen.getByLabelText('close-icon'));
    expect(mockSetFilterVisible).toHaveBeenCalledWith(false);
  });
});