import React, { useState, useEffect } from "react";
import CustomButton from "../../Components/CustomComponents/CustomButton";
import RideCustomerModal from "./RideCustomerModal";
import axios from "axios";
import Cookies from "js-cookie";
import Swal from "sweetalert2";
import { CURRENTRIDETABLECUSTOMER_HEADERS, CURRENTRIDETABLECUSTOMER_BUTTON_TITLES, CURRENTRIDETABLECUSTOMER_MESSAGES, CURRENTRIDETABLECUSTOMER_ENDPOINTS } from '../../constants';
import { FaCheck, FaInfoCircle, FaTimes } from 'react-icons/fa';  // Import necessary icons
import { Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../../styles/UserDashBoard.css';

function CurrentRideTableCustomer({ customerData, fetchCustomerDetails }) {
    const [selectedRide, setSelectedRide] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const token = Cookies.get('token');

    const handleDetailsClick = (ride) => {
        setSelectedRide(ride);
    };

    const handleCloseModal = () => {
        setSelectedRide(null);
    };

    const handleReject = async (id) => {
        Swal.fire({
            title: CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_REQUEST_TITLE,
            text: CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_REQUEST_TEXT,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_REQUEST_CONFIRM,
            cancelButtonText: CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_REQUEST_CANCEL
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await axios.put(
                        `${CURRENTRIDETABLECUSTOMER_ENDPOINTS.UPDATE_RIDE_STATUS}${id}`,
                        { status: "REJECTED" },
                        { headers: { Authorization: `Bearer ${token}` } }
                    );
                    Swal.fire('Success!', CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_SUCCESS, 'success');
                    fetchCustomerDetails(); // Refresh data after rejection
                } catch (error) {
                    console.error(CURRENTRIDETABLECUSTOMER_MESSAGES.ERROR_UPDATING_STATUS, error);
                    Swal.fire('Error!', CURRENTRIDETABLECUSTOMER_MESSAGES.REJECT_ERROR, 'error');
                }
            }
        });
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = customerData.slice(indexOfFirstItem, indexOfLastItem);

    const renderPagination = () => {
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(customerData.length / itemsPerPage); i++) {
            pageNumbers.push(i);
        }

        return (
            <Pagination className="custom-pagination justify-content-center">
                {pageNumbers.map(number => (
                    <Pagination.Item key={number} active={number === currentPage} onClick={() => handlePageChange(number)}>
                        {number}
                    </Pagination.Item>
                ))}
            </Pagination>
        );
    };

    return (
        <div>
            <h2 className="text-center custom-margin-bottom">{CURRENTRIDETABLECUSTOMER_MESSAGES.PENDING_REQUESTS_TITLE}</h2>
            <div className="table-responsive">
                {customerData.length === 0 ? (
                    <p className="text-center fw-bold my-2">{CURRENTRIDETABLECUSTOMER_MESSAGES.NO_PENDING_REQUESTS}</p>
                ) : (
                    <>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.SOURCE}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.DESTINATION}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.RIDE_DATE}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.RIDE_TIME}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.STATUS}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.DETAILS}</th>
                                    <th>{CURRENTRIDETABLECUSTOMER_HEADERS.OTHERS}</th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentItems.map((ride) => (
                                    <tr key={ride.requestId}>
                                        <td>{ride.source}</td>
                                        <td>{ride.destination}</td>
                                        <td>{ride.rideDate}</td>
                                        <td>{ride.rideTime}</td>
                                        <td>
                                            <span>{ride.rideStatus}</span>
                                        </td>
                                        <td>
                                            <FaInfoCircle className="info-icon" onClick={() => handleDetailsClick(ride)} style={{ cursor: 'pointer', fontSize: "20px" }} />
                                        </td>
                                        <td className="action-icons">
                                            <FaTimes className="cancel-icon" onClick={() => handleReject(ride.requestId)} />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        {renderPagination()}
                    </>
                )}

                {selectedRide && <RideCustomerModal ride={selectedRide} onClose={handleCloseModal} />}
            </div>
        </div>
    );
}

export default CurrentRideTableCustomer;
