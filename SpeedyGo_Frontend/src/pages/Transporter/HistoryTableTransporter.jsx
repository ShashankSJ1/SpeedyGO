import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Cookies from 'js-cookie';
import RideDetailsModal from './RideDetailsModal';
import { HISTORYTRANSPORTER_HEADERS, HISTORYTRANSPORTER_MESSAGES, HISTORYTRANSPORTER_ENDPOINTS } from '../../constants';
import Swal from 'sweetalert2';
import { FaInfoCircle } from 'react-icons/fa';
import { Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function HistoryTableTransporter() {
    const [historyData, setHistoryData] = useState([]);
    const email = useSelector(state => state.user.email);
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

    useEffect(() => {
        const fetchHistoryData = async () => {
            try {
                const response = await axios.get(`${HISTORYTRANSPORTER_ENDPOINTS.FETCH_HISTORY}${email}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setHistoryData(response.data);
            } catch (error) {
                Swal.fire('Error', HISTORYTRANSPORTER_MESSAGES.SOMETHING_WENT_WRONG, 'error');
            }
        };

        if (email) {
            fetchHistoryData();
        }
    }, [email, token]);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = historyData.slice(indexOfFirstItem, indexOfLastItem);

    const renderPagination = () => {
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(historyData.length / itemsPerPage); i++) {
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
        <div className="table-responsive">
            <h2 className="text-center custom-margin-bottom">{HISTORYTRANSPORTER_HEADERS.HISTORY}</h2>
            {historyData.length === 0 ? (
                <p className="text-center fw-bold my-2">{HISTORYTRANSPORTER_MESSAGES.NO_DATA}</p>
            ) : (
                <>
                    <table className='table table-striped'>
                        <thead>
                            <tr>
                                <th className="text-center">{HISTORYTRANSPORTER_HEADERS.REQUEST_ID}</th>
                                <th className="text-center">{HISTORYTRANSPORTER_HEADERS.SOURCE}</th>
                                <th className="text-center">{HISTORYTRANSPORTER_HEADERS.DESTINATION}</th>
                                <th className="text-center">{HISTORYTRANSPORTER_HEADERS.STATUS}</th>
                                <th className="text-center">{HISTORYTRANSPORTER_HEADERS.DETAILS}</th>
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
                                        <FaInfoCircle 
                                            className="info-icon" 
                                            onClick={() => handleShowModal(ride)} 
                                            style={{ cursor: 'pointer', color: 'blue', fontSize: "20px" }} 
                                        />
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

export default HistoryTableTransporter;
