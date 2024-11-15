import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import RideDetailsModal from './RideDetailsModal';
import axios from "axios";
import Cookies from 'js-cookie';
import Swal from "sweetalert2";
import { CURRENTRIDETABLETRANSPORTER_HEADERS, CURRENTRIDETABLETRANSPORTER_MESSAGES, CURRENTRIDETABLETRANSPORTER_ENDPOINTS } from '../../constants';
import { FaCheck, FaInfoCircle, FaTimes } from 'react-icons/fa';
import { Pagination } from 'react-bootstrap';
import "../../styles/TransporterDashBoard.css";

function CurrentRideTableTransporter({ data, fetchTransporterData }) {
    const [showModal, setShowModal] = useState(false);
    const [selectedRide, setSelectedRide] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const token = Cookies.get('token');

    const handleShowModal = (ride) => {
        setSelectedRide(ride);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedRide(null);
    };

    const handleAccept = async (id) => {
        Swal.fire({
            title: CURRENTRIDETABLETRANSPORTER_MESSAGES.ACCEPT_REQUEST_TITLE,
            text: CURRENTRIDETABLETRANSPORTER_MESSAGES.ACCEPT_REQUEST_TEXT,
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: CURRENTRIDETABLETRANSPORTER_MESSAGES.ACCEPT_REQUEST_CONFIRM,
            cancelButtonText: CURRENTRIDETABLETRANSPORTER_MESSAGES.ACCEPT_REQUEST_CANCEL
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await axios.put(
                        `${CURRENTRIDETABLETRANSPORTER_ENDPOINTS.UPDATE_RIDE_STATUS}${id}`,
                        { status: "ACCEPTED" },
                        { headers: { Authorization: `Bearer ${token}` } }
                    );
                    Swal.fire('Success!', CURRENTRIDETABLETRANSPORTER_MESSAGES.ACCEPT_SUCCESS, 'success');
                    fetchTransporterData();
                } catch (error) {
                    console.error(CURRENTRIDETABLETRANSPORTER_MESSAGES.ERROR_UPDATING_STATUS, error);
                }
            }
        });
    };

    const handleReject = async (id) => {
        Swal.fire({
            title: CURRENTRIDETABLETRANSPORTER_MESSAGES.REJECT_REQUEST_TITLE,
            text: CURRENTRIDETABLETRANSPORTER_MESSAGES.REJECT_REQUEST_TEXT,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: CURRENTRIDETABLETRANSPORTER_MESSAGES.REJECT_REQUEST_CONFIRM,
            cancelButtonText: CURRENTRIDETABLETRANSPORTER_MESSAGES.REJECT_REQUEST_CANCEL
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await axios.put(
                        `${CURRENTRIDETABLETRANSPORTER_ENDPOINTS.UPDATE_RIDE_STATUS}${id}`,
                        { status: "REJECTED" },
                        { headers: { Authorization: `Bearer ${token}` } }
                    );
                    Swal.fire('Success!', CURRENTRIDETABLETRANSPORTER_MESSAGES.REJECT_SUCCESS, 'success');
                    fetchTransporterData();
                } catch (error) {
                    console.error(CURRENTRIDETABLETRANSPORTER_MESSAGES.ERROR_UPDATING_STATUS, error);
                }
            }
        });
    };

    const handleCompleteRide = async (id) => {
        Swal.fire({
            title: CURRENTRIDETABLETRANSPORTER_MESSAGES.COMPLETE_RIDE_TITLE,
            text: CURRENTRIDETABLETRANSPORTER_MESSAGES.COMPLETE_RIDE_TEXT,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: CURRENTRIDETABLETRANSPORTER_MESSAGES.COMPLETE_RIDE_CONFIRM
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await axios.put(
                        `${CURRENTRIDETABLETRANSPORTER_ENDPOINTS.UPDATE_RIDE_STATUS}${id}`,
                        { status: "PAYMENT" },
                        { headers: { Authorization: `Bearer ${token}` } }
                    );
                    Swal.fire('Success!', CURRENTRIDETABLETRANSPORTER_MESSAGES.COMPLETE_RIDE_SUCCESS, 'success');
                    fetchTransporterData();
                } catch (error) {
                    console.error(CURRENTRIDETABLETRANSPORTER_MESSAGES.ERROR_UPDATING_STATUS, error);
                }
            }
        });
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = data.slice(indexOfFirstItem, indexOfLastItem);

    const renderPagination = () => {
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(data.length / itemsPerPage); i++) {
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
        <div className="table-responsive transporterdashboardtable">
            <h2 className="text-center custom-margin-bottom">{CURRENTRIDETABLETRANSPORTER_HEADERS.CURRENT_RIDES}</h2>
            {data.length === 0 ? (
                <p className="text-center fw-bold my-2">{CURRENTRIDETABLETRANSPORTER_MESSAGES.NO_PENDING_REQUEST}</p>
            ) : (
                <>
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.REQUEST_ID}</th>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.SOURCE}</th>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.DESTINATION}</th>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.STATUS}</th>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.DETAILS}</th>
                                <th className="text-center">{CURRENTRIDETABLETRANSPORTER_HEADERS.OTHER}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentItems.map((ride) => (
                                <tr key={ride.requestId}>
                                    <td className="text-center">{ride.requestId}</td>
                                    <td className="text-center">{ride.source}</td>
                                    <td className="text-center">{ride.destination}</td>
                                    <td className="text-center">{ride.rideStatus}</td>
                                    <td className="text-center">
                                        <FaInfoCircle className="info-icon" onClick={() => handleShowModal(ride)} style={{ cursor: 'pointer', color: 'blue', fontSize: "20px" }} />
                                    </td>
                                    <td className="text-center">
                                        <div className="icon-container">
                                            {ride.rideStatus === 'ACCEPTED' ? (
                                                <FaCheck className="check-icon" onClick={() => handleCompleteRide(ride.requestId)} style={{ cursor: 'pointer', color: 'green' }} />
                                            ) : (
                                                <div className="accept-cancel text-center">
                                                    <FaCheck className="check-icon" onClick={() => handleAccept(ride.requestId)} style={{ cursor: 'pointer', color: 'green' }} />
                                                    <FaTimes className="cancel-icon" onClick={() => handleReject(ride.requestId)} style={{ cursor: 'pointer', color: 'red' }} />
                                                </div>
                                            )}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    
                </>
            )}
            {renderPagination()}
            <RideDetailsModal show={showModal} handleClose={handleCloseModal} ride={selectedRide} />
        </div>
    );
}

export default CurrentRideTableTransporter;
