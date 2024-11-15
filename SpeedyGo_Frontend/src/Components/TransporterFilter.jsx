import React from 'react';
import "../styles/UserStyles/TransporterFilter.css";
import { FaTimes } from 'react-icons/fa';
import CustomFormInput from './CustomComponents/CustomFormInput';
import CustomButton from './CustomComponents/CustomButton';
import { TRANSPORTERFILTER_LABELS, TRANSPORTERFILTER_BUTTON_TITLES, TRANSPORTERFILTER_OPTIONS } from '../constants';

function TransporterFilter({ filters, setFilters, isFilterVisible, setFilterVisible, handleFilter, transporterData, handleInputChange, handleremoveFilter }) {
    // Create vehicle type options dynamically from transporterData
    const vehicleOptions = [
        TRANSPORTERFILTER_OPTIONS.SELECT_OPTION,
        ...Array.from(new Set(transporterData.map(item => item.vehicleInfo?.vehicleType)))
            .filter(Boolean)
            .map(vehicleType => ({
                value: vehicleType,
                label: vehicleType
            }))
    ];

    return (
        <div className="filter-section">
            <h3>{TRANSPORTERFILTER_LABELS.FILTERS}</h3>
            
            {isFilterVisible && (
                <FaTimes className="close-icon" onClick={() => setFilterVisible(false)} />
            )}

            {/* Vehicle Type Filter */}
            <div className="filter-item">
                <label htmlFor="vehicleType">{TRANSPORTERFILTER_LABELS.VEHICLE_TYPE}</label>
                <CustomFormInput
                    tag='select'
                    name="vehicleType"
                    id="vehicleType"
                    value={filters.vehicleType}
                    onChange={handleInputChange}
                    options={vehicleOptions}
                />
            </div>

            {/* Base Rate Filter */}
            <div className="filter-item">
                <label>{TRANSPORTERFILTER_LABELS.BASE_RATE}</label>
                <div>
                    <label>
                        <input
                            type="radio"
                            name="baseRate"
                            value="lowest"
                            checked={filters.baseRate === "lowest"}
                            onChange={handleInputChange}
                        />
                        {TRANSPORTERFILTER_LABELS.LOWEST}
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="baseRate"
                            value="highest"
                            checked={filters.baseRate === "highest"}
                            onChange={handleInputChange}
                        />
                        {TRANSPORTERFILTER_LABELS.HIGHEST}
                    </label>
                </div>
            </div>

            {/* Price per Km Filter */}
            <div className="filter-item">
                <label>{TRANSPORTERFILTER_LABELS.PRICE_PER_KM}</label>
                <div>
                    <label>
                        <input
                            type="radio"
                            name="pricePerKm"
                            value="lowest"
                            checked={filters.pricePerKm === "lowest"}
                            onChange={handleInputChange}
                        />
                        {TRANSPORTERFILTER_LABELS.LOWEST}
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="pricePerKm"
                            value="highest"
                            checked={filters.pricePerKm === "highest"}
                            onChange={handleInputChange}
                        />
                        {TRANSPORTERFILTER_LABELS.HIGHEST}
                    </label>
                </div>
            </div>

            <div className='filter-buttons'>
                <CustomButton buttonTitle={TRANSPORTERFILTER_BUTTON_TITLES.REMOVE_FILTER} onClick={handleremoveFilter} />
                <CustomButton buttonTitle={TRANSPORTERFILTER_BUTTON_TITLES.APPLY_FILTER} onClick={handleFilter} />
            </div>
        </div>
    );
}

export default TransporterFilter;
