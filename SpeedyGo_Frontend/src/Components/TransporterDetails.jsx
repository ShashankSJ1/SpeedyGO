import React, { useState, useEffect, useCallback } from 'react';
import { useLocation } from 'react-router-dom';
import { useSelector } from 'react-redux';
import axios from 'axios';
import Cookies from 'js-cookie';
import Swal from 'sweetalert2';
import { FaTimes, FaBars } from 'react-icons/fa';
import { TRANSPOTDETAILS_INITIAL_FILTERS, TRANSPOTDETAILS_ITEMS_PER_PAGE, TRANSPOTDETAILS_ENDPOINTS, TRANSPOTDETAILS_MESSAGES, TRANSPOTDETAILS_UI_TEXT, TRANSPORTERDASHBOARD_MESSAGES } from '../constants';
import useDebounce from '../Components/CustomComponents/useDebounce';
import CustomFormInput from './CustomComponents/CustomFormInput';
import CustomButton from './CustomComponents/CustomButton';
import TransporterFilter from './TransporterFilter';
import TransporterDetailsTableView from './TransporterDetailsTableView';
import CustomTransporterDetailsCard from './CustomTransporterDetailsCard';

const TransporterDetails = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const location = useLocation();
    const [filters, setFilters] = useState(TRANSPOTDETAILS_INITIAL_FILTERS);
    const [isFilterVisible, setFilterVisible] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = TRANSPOTDETAILS_ITEMS_PER_PAGE;
    const [isTableView, setIsTableView] = useState(false);
    const [selectedTransporter, setSelectedTransporter] = useState(null);

    const originalTransporterData = location.state?.transporter || [];
    const riderData = location.state?.riderData;
    const [transporterData, setTransporterData] = useState(originalTransporterData);
    const { email, name, phoneNumber } = useSelector(state => state.user);
    const token = Cookies.get('token');

    useEffect(() => {
        setTransporterData(originalTransporterData);
    }, [originalTransporterData]);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFilters((prevFilters) => ({
            ...prevFilters,
            [name]: value,
        }));
    };

    const handleremoveFilter = () => {
        setFilters(TRANSPOTDETAILS_INITIAL_FILTERS);
        setTransporterData(originalTransporterData);
        setFilterVisible(false); // Close the filter panel
    };

    const handleSearch = async (event) => {
        const searchTerm = event.target.value;
        setSearchTerm(searchTerm);

        if (searchTerm === '') {
            setTransporterData(originalTransporterData);
            return;
        }

        try {
            const response = await axios.get(TRANSPOTDETAILS_ENDPOINTS.SEARCH, {
                params: {
                    keyword: searchTerm,
                    distance: riderData.distance
                },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setTransporterData(response.data);
        } catch (error) {
            console.error(TRANSPOTDETAILS_MESSAGES.FETCH_ERROR, error);
        }
    };

    const handleFilter = async () => {
        let sortField1, sortDirection1;
        let sortField2, sortDirection2;

        // Determine the primary and secondary sort fields and directions
        if (filters.pricePerKm && !filters.baseRate) {
            // Only pricePerKilometer is selected
            sortField1 = 'pricePerKilometer';
            sortDirection1 = filters.pricePerKm === 'lowest' ? 'asc' : 'desc';
        } else if (filters.baseRate && !filters.pricePerKm) {
            // Only baseRate is selected
            sortField1 = 'basePrice';
            sortDirection1 = filters.baseRate === 'lowest' ? 'asc' : 'desc';
        } else if (filters.baseRate && filters.pricePerKm) {
            // Both are selected
            sortField1 = 'basePrice';
            sortDirection1 = filters.baseRate === 'lowest' ? 'asc' : 'desc';
            sortField2 = 'pricePerKilometer';
            sortDirection2 = filters.pricePerKm === 'lowest' ? 'asc' : 'desc';
        }

        const params = {
            vehicleType: filters.vehicleType,
            sortField1: sortField1,
            sortDirection1: sortDirection1,
            sortField2: sortField2,
            sortDirection2: sortDirection2,
            sortField3: 'vehicleType',
            sortDirection3: 'asc',
            distance: riderData.distance
        };

        try {
            const response = await axios.get(TRANSPOTDETAILS_ENDPOINTS.FILTER, {
                params: params,
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setTransporterData(response.data);
            setFilterVisible(false); // Close the filter panel
        } catch (error) {
            console.error(TRANSPOTDETAILS_MESSAGES.FETCH_ERROR, error);
            if (error.response) {
                console.error(TRANSPOTDETAILS_MESSAGES.RESPONSE_ERROR, error.response.data);
                console.error(TRANSPOTDETAILS_MESSAGES.RESPONSE_STATUS, error.response.status);
                console.error(TRANSPOTDETAILS_MESSAGES.RESPONSE_HEADERS, error.response.headers);
            }
        }
    };

    const submitRequest = async (totalPrice, transporterEmail, vehicleNumber) => {
        const data = {
            customerEmail: email,
            transporterEmail: transporterEmail,
            vehicleNumber: vehicleNumber,
            source: riderData.source,
            destination: riderData.destination,
            distance: riderData.distance,
            rideDate: riderData.rideDate,
            rideTime: riderData.rideTime,
        };
    
        Swal.fire({
            title:  TRANSPOTDETAILS_MESSAGES.SWAL_TITLE,
            text:  TRANSPOTDETAILS_MESSAGES.SWAL_TEXT,
            icon: TRANSPOTDETAILS_MESSAGES.SWAL_WARNING,
            showCancelButton: true,
            confirmButtonText:  TRANSPOTDETAILS_MESSAGES.SWAL_CONFIRM,
            cancelButtonText:  TRANSPOTDETAILS_MESSAGES.SWAL_CANCEL,
            reverseButtons: true
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    const response = await axios.post(TRANSPOTDETAILS_ENDPOINTS.REQUEST_RIDE, data, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    });
                    if (response.status === 201) {
                        Swal.fire("Success", TRANSPOTDETAILS_MESSAGES.SUCCESS, "success");
                    } else {
                        Swal.fire("Error", TRANSPOTDETAILS_MESSAGES.ERROR, "error");
                    }
                } catch (error) {
                    console.log(error);
                    Swal.fire("Error", TRANSPOTDETAILS_MESSAGES.ERROR, "error");
                }
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire("Cancelled", TRANSPOTDETAILS_MESSAGES.SWAL_CATCH_CANCEL, "error");
            }
        });
    };
    
    const debouncedHandleSearch = useCallback(useDebounce(handleSearch, 500), [originalTransporterData]);

    // Paginate the transporter data based on the current page
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = transporterData.slice(indexOfFirstItem, indexOfLastItem);
    const totalPages = Math.ceil(transporterData.length / itemsPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const openModal = (transporter) => {
        console.log("Modal called")
        setSelectedTransporter(transporter);
    };

    return (
        <div className="transporter-container">
            <div className="transporter-details">
                <div className={`filter-container ${isFilterVisible ? 'show-filter' : 'hide-filter'}`}>
                    <TransporterFilter
                        filters={filters}
                        setFilters={setFilters}
                        isFilterVisible={isFilterVisible}
                        setFilterVisible={setFilterVisible}
                        handleFilter={handleFilter}
                        transporterData={transporterData}
                        handleInputChange={handleInputChange}
                        handleremoveFilter={handleremoveFilter}
                    />
                    <FaTimes className="close-icon" onClick={() => setFilterVisible(false)} />
                </div>
                <div className="filter-toggle">
                    {!isFilterVisible && (
                        <FaBars className="menu-icon" onClick={() => setFilterVisible(true)} />
                    )}
                </div>

                <div className="transporter-contents">
                    <div className="searchbar-container">
                        <div className='transporter-search'>
                            <div className='transporter-search-container'>
                                <CustomFormInput tag="input" type="text" placeholder={TRANSPOTDETAILS_UI_TEXT.SEARCH_PLACEHOLDER} onChange={debouncedHandleSearch} />
                            </div>
                            <div className='Switch-table-view'>
                                <CustomButton buttonTitle={isTableView ? TRANSPOTDETAILS_UI_TEXT.SWITCH_TO_CARD_VIEW : TRANSPOTDETAILS_UI_TEXT.SWITCH_TO_TABLE_VIEW} onClick={() => setIsTableView(!isTableView)} />
                            </div>
                        </div>
                    </div>
                    {isTableView ? (
                        <TransporterDetailsTableView transporterData={currentItems} openModal={openModal} submitRequest={submitRequest} />
                    ) : (
                        <div className='customcard-maincontainer'>
                            {currentItems.map((item, index) => (
                                item.vehicleInfo ? (
                                    <CustomTransporterDetailsCard
                                        key={index}
                                        imgsrc="https://thumbs.dreamstime.com/b/logistics-import-export-background-transport-industry-container-cargo-freight-ship-sunset-sky-137520342.jpg"
                                        vehicleName={item.vehicleInfo.vehicleName}
                                        vehicleType={item.vehicleInfo.vehicleType}
                                        baseRate={`₹${item.vehicleInfo.basePrice}`}
                                        pricePerKm={`₹${item.vehicleInfo.pricePerKilometer}/km`}
                                        totalPrice={`₹${parseFloat(item.vehicleInfo.totalPrice).toFixed(2)}`}
                                        btntitle={TRANSPOTDETAILS_UI_TEXT.REQUEST_QUOTATIONS}
                                        onClick={() => submitRequest(item.vehicleInfo.totalPrice, item.transporterInfo.email, item.vehicleInfo.vehicleNumber)}
                                        openModal={() => openModal(item)}
                                    />
                                ) : (
                                    <div key={index}>{TRANSPOTDETAILS_UI_TEXT.VEHICLE_INFO_NOT_AVAILABLE}</div>
                                )
                            ))}
                        </div>
                    )}
                    <div className="pagination-container">
                        {[...Array(totalPages)].map((_, index) => (
                            <button
                                key={index}
                                className={`pagination-item ${index + 1 === currentPage ? 'active' : ''}`}
                                onClick={() => handlePageChange(index + 1)}
                            >
                                {index + 1}
                            </button>
                        ))}
                    </div>
                </div>
            </div>

            <div className="modal fade" id="transporterModal" tabIndex="-1" aria-labelledby="transporterModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="transporterModalLabel">{TRANSPOTDETAILS_UI_TEXT.MODAL_TITLE}</h5>
                            <button type="button" className="close" data-bs-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            {selectedTransporter && (
                                <div>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.TRANSPORTER_NAME}</strong> {selectedTransporter.transporterInfo.username}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.EMAIL}</strong> {selectedTransporter.transporterInfo.email}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.PHONE_NUMBER}</strong> {selectedTransporter.transporterInfo.phoneNumber}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.VEHICLE_NUMBER}</strong> {selectedTransporter.vehicleInfo.vehicleNumber}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.VEHICLE_NAME}</strong> {selectedTransporter.vehicleInfo.vehicleName}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.VEHICLE_TYPE}</strong> {selectedTransporter.vehicleInfo.vehicleType}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.BASE_PRICE}</strong> ₹{selectedTransporter.vehicleInfo.basePrice}</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.PRICE_PER_KM}</strong> ₹{selectedTransporter.vehicleInfo.pricePerKilometer}/km</p>
                                    <p><strong>{TRANSPOTDETAILS_UI_TEXT.TOTAL_PRICE}</strong> ₹{selectedTransporter.vehicleInfo.totalPrice}</p>
                                </div>
                            )}
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{TRANSPOTDETAILS_UI_TEXT.MODAL_CLOSE}</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TransporterDetails;
